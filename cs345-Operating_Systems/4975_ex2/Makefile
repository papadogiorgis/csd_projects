#PAPADAKIS GEORGIOS
#CSD4975
#ASSIGNMENT 2

TARGET = ex2

CC = gcc

CFLAGS = -g -pthread -Wall

SRCS = ex2.c

OBJS = $(SRCS:.c=.o)

all: $(TARGET)

$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJS)

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f $(OBJS) $(TARGET)

.PHONY: all clean