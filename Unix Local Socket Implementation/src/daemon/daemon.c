#include "../utils/util.h"
#include <sys/un.h>

const int default_protocol = 0;

int main(int argc, char *argv[]) 
{
    /* Our process ID and Session ID */
//    pid_t pid, sid;

	struct sockaddr_un server_address, client_address;
 	int listenfd, acceptfd;
 	int client_address_size;
    int connetions_quee_size;
    char* socket_path;

    if (argc != 3) {
        printf("wrong number of paramenters : server <socket_path> <connetions_quee_size>\n");
        exit(EXIT_FAILURE);
    }

    socket_path = argv[1];
    connetions_quee_size = atoi(argv[2]);
 	listenfd = socket(AF_UNIX, SOCK_STREAM, default_protocol);

 	if (listenfd < 0)
    {
        perror("server socket()");
        exit(EXIT_FAILURE);
    }

    bzero(&server_address, sizeof(listenfd));
    server_address.sun_family = AF_UNIX; 
    strncpy(server_address.sun_path, socket_path, sizeof(server_address.sun_path) - 1);
    bind(listenfd, (struct sockaddr*) &server_address, sizeof(server_address));
    listen(listenfd, connetions_quee_size);
    
    unsigned int sequence_size  = 0;
    int transfer_amount = 1;
    /* The Big Loop */
    for(;;)
    {
        client_address_size = sizeof(client_address);
        acceptfd = accept(listenfd, (struct sockaddr*) &client_address, &client_address_size);
        if (fork() == 0)
        {
            close(listenfd);
            if (read(acceptfd, &sequence_size, sizeof sequence_size) < 0) {
                perror("server read()");
                exit(EXIT_FAILURE);
            }

            if (read(acceptfd, &transfer_amount, sizeof transfer_amount) < 0) {
                perror("server read()");
                exit(EXIT_FAILURE);
            }

            printf("sequence size : %d\n", sequence_size);
            printf("transfer amount : %d\n", transfer_amount);
            
            int8_t* random_sequence = (int8_t*) malloc(sizeof(int8_t) * sequence_size);
            for (int i = 0; i < transfer_amount; ++i) {
                generate_random_sequence_from_source("/home/lukash/temp/RandomNumbersSource", random_sequence, sequence_size);
                if (writen(acceptfd, random_sequence, sizeof(int8_t) * sequence_size) < 0) {
                    perror("server write()");
                }
            }
            free(random_sequence);
            close(acceptfd);

            return 0;
        }
    }

    exit(EXIT_SUCCESS);
}
