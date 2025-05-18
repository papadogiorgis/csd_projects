###################################################
# PAPADAKIS GEORGIOS 4975
# ASSIGNMENT 4
# MAKEFILE
####################################################

CFLAGS = -Wall -pedantic -ansi

sudoku: src/grid.o src/sudoku.c src/sudoku.h
	gcc $(CFLAGS) -DDEBUG src/sudoku.c src/grid.o -o sudoku

grid.o: src/grid.c src/grid.h
	gcc $(CFLAGS) -c src/grid.c -o src/grid.o

clean:
	rm -f sudoku src/*.o
