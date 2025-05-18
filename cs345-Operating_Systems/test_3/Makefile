CC = gcc
CFLAGS = -Wall -g -std=c99 -D_GNU_SOURCE

EXECUTABLES = test_0 test_50 test_100

SOURCES = test_0.c test_50.c test_100.c

HEADER = lib_for_tests.h

all: $(EXECUTABLES)

test_0: test_0.c  $(HEADER)
	$(CC) $(CFLAGS) -o test_0 test_0.c

test_50: test_50.c $(HEADER)
	$(CC) $(CFLAGS) -o test_50 test_50.c

test_100: test_100.c $(HEADER)
	$(CC) $(CFLAGS) -o test_100 test_100.c

clean:
	rm -f $(EXECUTABLES) *.o
