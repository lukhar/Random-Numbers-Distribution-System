#include "util.h"


int calculate_sequence_size(int bit_number)
{
    return (bit_number % 8 == 0) ? bit_number / 8 : bit_number / 8 + 1;
}

void print_sequence(int8_t* sequnece, int sequence_size) 
{
    for (int i = 0; i < sequence_size; i++) {
        printf("%d", sequnece[i]);
    }
}

ssize_t readn(int fd, void *vptr, size_t n)
{
    size_t nleft;
    ssize_t nread;
    char *ptr;

    ptr = vptr;
    nleft = n;
    while (nleft > 0) {
        if ((nread = read(fd, ptr, nleft)) < 0) {
            if (errno == EINTR) {
                nread = 0;
            } 
            else {
                return -1;
            }
        } 
        else if (nread == 0) {
            break;
        }

        nleft -= nread;
        ptr += nread;
    }

    return (n - nleft);
}

int8_t* generate_random_sequence(int8_t* random_sequence, unsigned int nbytes)
{
    ssize_t bytes_read;
    int8_t* where = random_sequence;

    bzero(random_sequence, nbytes);
    int random_dev_fd = open("/dev/urandom", O_RDONLY);
    
    if (random_dev_fd < 0) {
        perror("server generate_random_sequence() --> open()");
        exit(EXIT_FAILURE);
    }

    while (nbytes != 0) {
        if ((bytes_read = read(random_dev_fd, where, nbytes)) == -1) {
            perror("server generate_random_sequence() --> read()");
            exit(EXIT_FAILURE);
        }
        where  += bytes_read;
        nbytes -= bytes_read;
    }
    close(random_dev_fd);

    return random_sequence;
}

int8_t* generate_random_sequence_from_source(const char* source_path, int8_t* random_sequence, unsigned int nbytes)
{
    ssize_t bytes_read;
    int8_t* where = random_sequence;

    bzero(random_sequence, nbytes);
    int random_dev_fd = open(source_path, O_RDONLY);
    
    if (random_dev_fd < 0) {
        perror("server generate_random_sequence() --> open()");
        exit(EXIT_FAILURE);
    }
    
    while (nbytes != 0) {
        if ((bytes_read = read(random_dev_fd, where, nbytes)) == -1) {
            perror("server generate_random_sequence() --> read()");
            exit(EXIT_FAILURE);
        }
        where  += bytes_read;
        nbytes -= bytes_read;
    }
    close(random_dev_fd);

    return random_sequence;
}

ssize_t writen(int fd, const void *vptr, size_t n)
{
   size_t nleft;
   ssize_t nwritten;
   const char *ptr;

   ptr = vptr;
   nleft = n;
   while (nleft > 0) {
       if ((nwritten = write(fd, ptr, nleft)) <= 0) {
           if (errno = EINTR) {
               nwritten = 0;
           }
           else {
               return -1;
           }
       }

       nleft -= nwritten;
       ptr += nwritten;
   }
   return n;
}

int timeval_subtract(struct timeval* result, struct timeval* x, struct timeval* y)
{
  /* Perform the carry for the later subtraction by updating y. */
  if (x->tv_usec < y->tv_usec) {
    int nsec = (y->tv_usec - x->tv_usec) / 1000000 + 1;
    y->tv_usec -= 1000000 * nsec;
    y->tv_sec += nsec;
  }
  if (x->tv_usec - y->tv_usec > 1000000) {
    int nsec = (y->tv_usec - x->tv_usec) / 1000000;
    y->tv_usec += 1000000 * nsec;
    y->tv_sec -= nsec;
  }

  /* Compute the time remaining to wait.
     tv_usec is certainly positive. */
  result->tv_sec = x->tv_sec - y->tv_sec;
  result->tv_usec = x->tv_usec - y->tv_usec;

  /* Return 1 if result is negative. */
  return x->tv_sec < y->tv_sec;
}
