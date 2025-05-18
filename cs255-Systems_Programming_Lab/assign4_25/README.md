# Assignment 4: Sudoku checker, solver, and generator

## NAME: Papadakis Georgios 4975

## THINGS I HANDLED DIFFERENTLY THAN WHAT THE ASSIGNMENT SPECIFIES:
None I can think of.

## HELP FROM OTHERS:
Office hours.

## EXTRA CREDIT:
My code does not use any pointers or global variables.

## UNNECESSARY COST AND PERFORMANCE NOTES (3rd bullet of extra credit):
a)sudoku_solve(): This function repeatedly calls sudoku_is_correct() at every step, which is costly, and every grid_update results in a full copy of the grid. Improvement: Avoid calling sudoku_is_correct on every path.
