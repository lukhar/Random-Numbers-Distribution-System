#include "../utils/util.h"

const int default_protocol = 0;

int main(int argc, char **argv)
{
	struct sockaddr_in server_address, client_address;
 	int listenfd, acceptfd;
 	int client_address_size;
    int port;
    int connetions_quee_size;

    if (argc != 3) {
        printf("wrong number of paramenters : server <port> <connetions_quee_size>\n");
        exit(EXIT_FAILURE);
    }

    port = atoi(argv[1]);
    connetions_quee_size = atoi(argv[2]);
 	listenfd = socket(AF_INET, SOCK_STREAM, default_protocol);

 	if (listenfd < 0)
    {
        perror("server socket()");
        exit(EXIT_FAILURE);
    }

    bzero(&server_address, sizeof(listenfd));
    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(port);
    server_address.sin_addr.s_addr = htonl(INADDR_ANY);
    bind(listenfd, (struct sockaddr*) &server_address, sizeof(server_address));
    listen(listenfd, connetions_quee_size);
    
    int sequence_size = 0;
    int transfer_amount = 1;
    for(;;)
    {
        client_address_size = sizeof(client_address);
        acceptfd = accept(listenfd, (struct sockaddr*) &client_address, &client_address_size);
        if (fork() == 0)
        {
            close(listenfd);
            if (read(acceptfd, &sequence_size, sizeof sequence_size) < 0) {
                perror("server write()");
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
}
