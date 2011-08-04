CC		=	gcc
CFLAGS	= 	-g -std=c99

BINDIR	=	bin
SRCDIR	=	src


all: client daemon
	
client: $(SRCDIR)/client/client.c $(SRCDIR)/utils/util.o
	$(CC) $(CFLAGS) $(SRCDIR)/client/client.c $(SRCDIR)/utils/util.o -o $(BINDIR)/client

daemon: $(SRCDIR)/daemon/daemon.c $(SRCDIR)/utils/util.o
	$(CC) $(CFLAGS) $(SRCDIR)/daemon/daemon.c $(SRCDIR)/utils/util.o -o $(BINDIR)/daemon

util.o: $(SRCDIR)/utils/util.c
	$(CC) $(CFLAGS) $(SRCDIR)/utils/util.c -o $(SRCDIR)/utils/util.o

clean: 
	rm -f $(BINDIR)/client \
		$(BINDIR)/daemon \
		$(SRCDIR)/utils/util.o

run_daemon:
	./$(BINDIR)/daemon test 20

run_client:
	./$(BINDIR)/client test 6000
