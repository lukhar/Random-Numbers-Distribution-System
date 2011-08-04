CC		=	gcc
CFLAGS	= 	-g -std=c99

BINDIR	=	bin
SRCDIR	=	src


all: client server
	
client: $(SRCDIR)/client/client.c $(SRCDIR)/utils/util.o
	$(CC) $(CFLAGS) $(SRCDIR)/client/client.c $(SRCDIR)/utils/util.o -o $(BINDIR)/client

server: $(SRCDIR)/server/server.c $(SRCDIR)/utils/util.o
	$(CC) $(CFLAGS) $(SRCDIR)/server/server.c $(SRCDIR)/utils/util.o -o $(BINDIR)/server

util.o: $(SRCDIR)/utils/util.c
	$(CC) $(CFLAGS) $(SRCDIR)/utils/util.c -o $(SRCDIR)/utils/util.o

clean: 
	rm -f $(BINDIR)/client \
		$(BINDIR)/server \
		$(SRCDIR)/utils/util.o

