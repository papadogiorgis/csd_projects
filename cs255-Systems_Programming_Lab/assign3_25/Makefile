# PAPADAKIS GEORGIOS 4975
# ASSIGNMENT 3
# MAKEFILE

CFLAGS = -Wall -pedantic -ansi

.PHONY: list hash clean

list: symtablelist.o src/testsymtab.c src/symtable.h
	gcc $(CFLAGS) symtablelist.o src/testsymtab.c -o testsymtab

hash: symtablehash.o src/testsymtab.c src/symtable.h
	gcc $(CFLAGS) symtablehash.o src/testsymtab.c -o testsymtab

symtablelist.o: src/symtablelist.c src/symtable.h
	gcc $(CFLAGS) -c src/symtablelist.c

symtablehash.o: src/symtablehash.c src/symtable.h
	gcc $(CFLAGS) -c src/symtablehash.c

clean:
	rm -f *.o testsymtab
