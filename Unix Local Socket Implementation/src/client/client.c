#include "../utils/util.h"
#include <sys/un.h>

const int default_protocol = 0;

int main(int argc, char **argv)
{
    struct sockaddr_un client_address;
    int socket_fd;
    char* socket_path = NULL;
    unsigned int sequence_length;
    unsigned int sequence_size;
    int transfer_amount = 1;
    int8_t* random_bit_sequence;

    if (argc < 3) {
        printf("wrong number of paramenters : client <socket_path> <sequence_length>\n");
        exit(EXIT_FAILURE);
    }

    if (argc == 4) {
        transfer_amount = atoi(argv[3]);
    }
//    printf("transfer_amount = %d\n", transfer_amount);

    socket_path = argv[1];
    sequence_length = atoll(argv[2]);
    socket_fd = socket(AF_LOCAL, SOCK_STREAM, default_protocol);

    if (socket_fd < 0)
    {
        perror("client socket()");
        exit(EXIT_FAILURE);
    }

    bzero(&client_address, sizeof(client_address));
    client_address.sun_family = AF_LOCAL;
    strncpy(client_address.sun_path, socket_path, sizeof(client_address.sun_path) - 1);

    if (connect(socket_fd, (struct sockaddr*) &client_address, sizeof(client_address)) < 0)
    {
        perror("client connect()");
        exit(EXIT_FAILURE);
    }

    sequence_size = calculate_sequence_size(sequence_length);

    if (write(socket_fd, &sequence_size, sizeof sequence_size) < 0) {
        perror("client write()");
        exit(EXIT_FAILURE);
    }

    if (write(socket_fd, &transfer_amount, sizeof transfer_amount) < 0) {
        perror("client write()");
        exit(EXIT_FAILURE);
    }

    random_bit_sequence = (int8_t*) calloc(sequence_size, sizeof(int8_t));
//    time_t start = time(0);
    for (int i = 0; i < transfer_amount; ++i) {  
        if (readn(socket_fd, random_bit_sequence, sizeof(int8_t) * sequence_size) < 0) {
            perror("client read()");
            exit(EXIT_FAILURE);
        }
        //for (int i = 0; i < sequence_size; i++) {
        //    printf("%d", random_bit_sequence[i]);
        //}
        //printf("\n");
    }    
    time_t stop = time(0);

//    printf("elaspsed time %f\n", difftime(stop, start));

    free(random_bit_sequence);

    return EXIT_SUCCESS;
}
