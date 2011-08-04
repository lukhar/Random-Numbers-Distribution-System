#ifndef UTIL
#define UTIL

#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <strings.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <time.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <errno.h>

int calculate_sequence_size(int bit_number);

void print_sequence(int8_t* sequnece, int sequence_size);

ssize_t readn(int fd, void *vptr, size_t n);

int8_t* generate_random_sequence(int8_t* random_sequence, unsigned int nbytes);

int8_t* generate_random_sequence_from_source(const char* source_path, int8_t* random_sequence, unsigned int nbytes);

ssize_t writen(int fd, const void *vptr, size_t n);

int timeval_subtract(struct timeval* result, struct timeval* x, struct timeval* y);
#endif /* end of include guard: UTIL */
