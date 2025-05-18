#########################
# PAPADAKIS GEORGIOS 4975
# ASSIGNMENT 2
# MAKEFILE
#########################

CC = gcc
CFLAGS = -Wall -pedantic -ansi

ARS_OBJ = mystring_ars.o
PTRS_OBJ = mystring_ptrs.o

ARS_EXE = main_ars
PTRS_EXE = main_ptrs

all: $(ARS_OBJ) $(PTRS_OBJ) $(ARS_EXE) $(PTRS_EXE)

mystring_ars.o: mystring_ars.c mystring.h
	$(CC) $(CFLAGS) -c mystring_ars.c

mystring_ptrs.o: mystring_ptrs.c mystring.h
	$(CC) $(CFLAGS) -c mystring_ptrs.c

$(ARS_EXE): $(ARS_OBJ) main.c
	$(CC) $(CFLAGS) main.c $(ARS_OBJ) -o $(ARS_EXE)

$(PTRS_EXE): $(PTRS_OBJ) main.c
	$(CC) $(CFLAGS) main.c $(PTRS_OBJ) -o $(PTRS_EXE)

clean:
	rm -f $(ARS_OBJ) $(PTRS_OBJ) $(ARS_EXE) $(PTRS_EXE)
