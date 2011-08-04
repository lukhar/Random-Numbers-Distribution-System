#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>

int calculateSequenceLength(int bitNumber)
{
    return (bitNumber % 8 == 0) ? bitNumber / 8 : bitNumber / 8 + 1;
}

int main (int argc, char const* argv[])
{
    int bitNumber = atoi(argv[1]);
    int sequenceLength = calculateSequenceLength(bitNumber);
    printf("sequence length : %d\n", sequenceLength);
    u_char* randomSequence = (u_char*)malloc(sizeof(u_char) * sequenceLength);
    bzero(randomSequence, sequenceLength);
    int randomData = open("/dev/random", O_RDONLY);
    int myRandomInteger;

    read(randomData, randomSequence, sequenceLength);
    close(randomData);

    int i;
    for (i = 0; i < sequenceLength; i++) {
        printf("%d ", randomSequence[i]);
    }
    printf("\n");

    return 0;
}
