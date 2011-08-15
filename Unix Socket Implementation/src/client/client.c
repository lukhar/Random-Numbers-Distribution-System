#include "../utils/util.h"

const int default_protocol = 0;

int main(int argc, char **argv)
{
    struct sockaddr_in client_address;
    int socket_fd;
    char* ip_adress = NULL;
    int port;
    unsigned int sequence_length;
    unsigned int sequence_size;
    int transfer_amount = 1;
    int8_t* random_bit_sequence;

    if (argc < 4) {
        printf("wrong number of paramenters : client <ip_adrress> <port> <sequence_length>\n");
        exit(EXIT_FAILURE);
    }

    if (argc == 5) {
        transfer_amount = atoi(argv[4]);
    }
    // printf("transfer_amount = %d\n", transfer_amount);

    ip_adress = argv[1];
    port = atoi(argv[2]);
    sequence_length = atol(argv[3]);
    socket_fd = socket(AF_INET, SOCK_STREAM, default_protocol);

    if (socket_fd < 0)
    {
        perror("client socket()");
        exit(EXIT_FAILURE);
    }

    bzero(&client_address, sizeof(client_address));
    client_address.sin_family = AF_INET;
    client_address.sin_port = htons(port);

    if (inet_pton(AF_INET, ip_adress, &client_address.sin_addr)<= 0)
    {
        perror("client inet_pton()");
        exit(EXIT_FAILURE);
    }

    struct timeval starttime, endtime, connection_open_time, data_transfer_time, connection_close_time;
    gettimeofday(&starttime, 0x0);
    if (connect(socket_fd, (struct sockaddr*) &client_address, sizeof(client_address)) < 0)
    {
        perror("client connect()");
        exit(EXIT_FAILURE);
    }
    gettimeofday(&endtime, 0x0);
	timeval_subtract(&connection_open_time, &endtime, &starttime);


    sequence_size = calculate_sequence_size(sequence_length);

    gettimeofday(&starttime, 0x0);
    if (write(socket_fd, &sequence_size, sizeof sequence_size) < 0) {
        perror("client write()");
        exit(EXIT_FAILURE);
    }
    
    if (write(socket_fd, &transfer_amount, sizeof transfer_amount) < 0) {
        perror("client write()");
        exit(EXIT_FAILURE);
    }

    random_bit_sequence = (int8_t*) calloc(sequence_size, sizeof(int8_t));
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
    gettimeofday(&endtime, 0x0);
	timeval_subtract(&data_transfer_time, &endtime, &starttime);

    gettimeofday(&starttime, 0x0);
    if (close(socket_fd) < 0) {
        perror("client close()");
    }
    gettimeofday(&endtime, 0x0);
	timeval_subtract(&connection_close_time, &endtime, &starttime);

    printf("%d %f %f %f\n", 
            sequence_size * transfer_amount,
            connection_open_time.tv_sec + connection_open_time.tv_usec / 1000000.0, 
            data_transfer_time.tv_sec + data_transfer_time.tv_usec / 1000000.0, 
            connection_close_time.tv_sec + connection_close_time.tv_usec / 1000000.0);

    free(random_bit_sequence);

    return EXIT_SUCCESS;
}
