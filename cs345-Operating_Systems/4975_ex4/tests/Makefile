CC = gcc
CFLAGS = -Wall -g -D_GNU_SOURCE

EXECUTABLES = test

SOURCES = test.c

HEADER = lib_for_tests.h

all: $(EXECUTABLES)

test: test.c  $(HEADER)
	$(CC) $(CFLAGS) -o test test.c

clean:
	rm -f $(EXECUTABLES) *.o