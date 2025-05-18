CC = gcc
CFLAGS = -Wall -g -D_GNU_SOURCE

EXECUTABLES = test test2 test3

SOURCES = test.c test2.c test3.c

HEADER = lib_for_tests.h

all: $(EXECUTABLES)

test: test.c  $(HEADER)
	$(CC) $(CFLAGS) -o test test.c

test2: test2.c  $(HEADER)
	$(CC) $(CFLAGS) -o test2 test2.c

test3: test3.c  $(HEADER)
	$(CC) $(CFLAGS) -o test3 test3.c

clean:
	rm -f $(EXECUTABLES) *.o