/*PAPADAKIS GEORGIOS 4975
 *ASSIGNMENT 4
 *SUDOKU.C             */

#include "sudoku.h"

/* Read a sudoku puzzle from stdin and return an object Grid_T initialized to
the values read.*/
Grid_T sudoku_read(void){
    int values[9][9];
    int i, j;
    Grid_T temp_grid;

    for(i=0; i<9; i++){
        for(j=0; j<9; j++){
            if(scanf("%d", &values[i][j]) != 1){
                fprintf(stderr, "INVALID INPUT!\n");
                return grid_init(temp_grid, values);
            }
        }
    }

    return grid_init(temp_grid, values);
}

/* Print g to stream s in the same format as expected by sudoku_read(). */
void sudoku_print(FILE *s, Grid_T g){
    int i, j;
    Choice_T temp_choice, value;

    for(i=0; i<9; i++){
        for(j=0; j<9; j++){
            temp_choice.i = i;
            temp_choice.j = j;
            temp_choice.n = 0;
            value = grid_read_value(g, temp_choice);
            fprintf(s, "%d", value.n);
            if(j != 8){
                fprintf(s, " ");
            }else{
                fprintf(s, "\n");
            }
        }
    }
}

static int sudoku_checker_for_generator(Grid_T g){
    int i, j, big_i, big_j;
    int flag[9];
    Choice_T temp_choice;

    /* check rows */
    for(i=0; i<9; i++){
        for(j=0; j<9; j++){
            flag[j] = 0;
        }
        for(j=0; j<9; j++){
            temp_choice.i = i;
            temp_choice.j = j;
            temp_choice.n = 0;
            temp_choice = grid_read_value(g, temp_choice);
            if(temp_choice.n == 0){
                continue;
            }
            if((temp_choice.n < 0)||(temp_choice.n > 9)){
                return 0;
            }
            if(flag[temp_choice.n - 1] == 1){
                return 0;
            }
            flag[temp_choice.n - 1] = 1;
        }
    }

    /* check columns */
    for(j=0; j<9; j++){
        for(i=0; i<9; i++){
            flag[i] = 0;
        }
        for(i=0; i<9; i++){
            temp_choice.i = i;
            temp_choice.j = j;
            temp_choice.n = 0;
            temp_choice = grid_read_value(g, temp_choice);
            if(temp_choice.n == 0){
                continue;
            }
            if((temp_choice.n < 0)||(temp_choice.n > 9)){
                return 0;
            }
            if(flag[temp_choice.n - 1] == 1){
                return 0;
            }
            flag[temp_choice.n - 1] = 1;
        }
    }

    /* check 3x3 subgrids */
    for(big_i=0; big_i<3; big_i++){
        for(big_j=0; big_j<3; big_j++){
            for(i=0; i<9; i++){
                flag[i] = 0;
            }
            for(i=0; i<3; i++){
                for(j=0; j<3; j++){
                    temp_choice.i = i + (3*big_i);
                    temp_choice.j = j + (3*big_j);
                    temp_choice.n = 0;
                    temp_choice = grid_read_value(g, temp_choice);
                    if(temp_choice.n == 0){
                        continue;
                    }
                    if((temp_choice.n < 0)||(temp_choice.n > 9)){
                        return 0;
                    }
                    if(flag[temp_choice.n - 1] == 1){
                        return 0;
                    }
                    flag[temp_choice.n - 1] = 1;
                }
            }
        }
    }

    return 1;
}

/* Return true iff g is complete and filled in correctly. */
int sudoku_is_correct(Grid_T g){
    int i, j, big_i, big_j;
    int flag[9];
    Choice_T temp_choice;

    /* check rows */
    for(i=0; i<9; i++){
        for(j=0; j<9; j++){
            flag[j] = 0;
        }
        for(j=0; j<9; j++){
            temp_choice.i = i;
            temp_choice.j = j;
            temp_choice.n = 0;
            temp_choice = grid_read_value(g, temp_choice);
            if((temp_choice.n < 1)||(temp_choice.n > 9)){
                return 0;
            }
            if(flag[temp_choice.n - 1] == 1){
                return 0;
            }
            flag[temp_choice.n - 1] = 1;
        }
    }

    /* check columns */
    for(j=0; j<9; j++){
        for(i=0; i<9; i++){
            flag[i] = 0;
        }
        for(i=0; i<9; i++){
            temp_choice.i = i;
            temp_choice.j = j;
            temp_choice.n = 0;
            temp_choice = grid_read_value(g, temp_choice);
            if((temp_choice.n < 1)||(temp_choice.n > 9)){
                return 0;
            }
            if(flag[temp_choice.n - 1] == 1){
                return 0;
            }
            flag[temp_choice.n - 1] = 1;
        }
    }

    /* check 3x3 subgrids */
    for(big_i=0; big_i<3; big_i++){
        for(big_j=0; big_j<3; big_j++){
            for(i=0; i<9; i++){
                flag[i] = 0;
            }
            for(i=0; i<3; i++){
                for(j=0; j<3; j++){
                    temp_choice.i = i + (3*big_i);
                    temp_choice.j = j + (3*big_j);
                    temp_choice.n = 0;
                    temp_choice = grid_read_value(g, temp_choice);
                    if((temp_choice.n < 1)||(temp_choice.n > 9)){
                        return 0;
                    }
                    if(flag[temp_choice.n - 1] == 1){
                        return 0;
                    }
                    flag[temp_choice.n - 1] = 1;
                }
            }
        }
    }

    return 1;
}

/* Sovle g and return the solution; if g has multiple solutions, return one of them. 
c can help you iterate over all cells in g */
int it_has_contradiction(Grid_T g){
    int i, j;
    for(i=0; i<9; i++){
        for(j=0; j<9; j++){
            if((g.cell[i][j].choices[0]==0)&&(g.cell[i][j].count == 0)){
                return 1;
            }
        }
    }
    return 0;
}

Grid_T sudoku_solve(Grid_T g, Choice_T c){
    Choice_T temp_choice;
    Grid_T temp_grid;

    /* i initialize it as unique until i prove the opposite */
    g.unique = 1;

    /* if there is a unique choice */
    temp_choice = grid_exist_unique(g);
    if((temp_choice.i != 0)||(temp_choice.j != 0)||(temp_choice.n != 0)){
        /* use this choice and backtrack */
        temp_grid = grid_update(g, temp_choice);
        return sudoku_solve(temp_grid, temp_choice);
    }

    /* find possible choices */
    g = grid_clear_unique(g);
    temp_choice = grid_iterate(g, c);
    /* while there are still choices */
    while((temp_choice.i != 0)||(temp_choice.j != 0)||(temp_choice.n != 0)){
        /* use the next choice and backtrack */
        temp_grid = grid_update(g,temp_choice);
        if(it_has_contradiction(temp_grid) == 0){
            temp_grid = sudoku_solve(temp_grid, temp_choice);
            /* if its correct return */
            if(sudoku_is_correct(temp_grid)){
                return temp_grid;
            }
        }
        /* if not then find next choice */
        temp_choice = grid_iterate(g, temp_choice);
    }
    
    return g;
}

/* Generate and return a sudoku puzzle */
Grid_T sudoku_generate(int nelts, int unique){
    int values[9][9] = {{0}};
    Grid_T g, test, sol;
    Choice_T temp_choice;
    int i, j, k, saved;
    int all=81, counter=0;

    temp_choice.i = 0;
    temp_choice.j = 0;
    temp_choice.n = 0;

    srand(getpid());
    while(counter < 60){
        i = rand() % 9;
        j = rand() % 9;
        k = (rand() % 9) + 1;
        if(values[i][j] != 0){
            continue;
        }
        values[i][j] = k;
        g = grid_init(g, values);
        /*sudoku_print(stdout, g);
        printf("\n====================\n");*/
        if(sudoku_checker_for_generator(g) == 1){
            counter++;
        }else{
            values[i][j] = 0;
        }
    }
    sol = sudoku_solve(g, temp_choice);
    if(nelts >= 81){
        return sol;
    }
    g = sol;
    while(all > nelts){
        i = rand() % 9;
        j = rand() % 9;
        if(g.cell[i][j].choices[0] == 0){
            continue;
        }
        saved = g.cell[i][j].choices[0];
        g.cell[i][j].choices[0] = 0;
        g.cell[i][j].count = 9;
        for(k=0; k<9; k++){
            g.cell[i][j].choices[k+1] = 1;
        }
        test = g;
        test = sudoku_solve(test, temp_choice);
        if(sudoku_is_correct(test)){
            if((unique == 1)&&(grid_unique(test) == 0)){
                g.cell[i][j].choices[0] = saved;
                g.cell[i][j].count = 0;
                for(k=0; k<9; k++){
                    g.cell[i][j].choices[k+1] = 0;
                }
            }else{
                all--;
            }
        }else{
            g.cell[i][j].choices[0] = saved;
            g.cell[i][j].count = 0;
            for(k=0; k<9; k++){
                g.cell[i][j].choices[k+1] = 0;
            }
        }
    }
    return g;
}

/* Print all row, col, sub-puzzle errors/conflicts found in puzzle g */
#if DEBUG
void sudoku_print_errors(Grid_T g){
    int i, j, big_i, big_j;
    int flag[9];
    Choice_T temp_choice;

    /* check rows */
    for(i=0; i<9; i++){
        for(j=0; j<9; j++){
            flag[j] = 0;
        }
        for(j=0; j<9; j++){
            temp_choice.i = i;
            temp_choice.j = j;
            temp_choice.n = 0;
            temp_choice = grid_read_value(g, temp_choice);
            if((temp_choice.n < 1)||(temp_choice.n > 9)){
                continue;
            }
            if(flag[temp_choice.n - 1] == 1){
                fprintf(stderr, "DUPLICATE NUMBER %d IN ROW %d\n", temp_choice.n, i);
                break;
            }
            flag[temp_choice.n - 1] = 1;
        }
    }

    /* check columns */
    for(j=0; j<9; j++){
        for(i=0; i<9; i++){
            flag[i] = 0;
        }
        for(i=0; i<9; i++){
            temp_choice.i = i;
            temp_choice.j = j;
            temp_choice.n = 0;
            temp_choice = grid_read_value(g, temp_choice);
            if((temp_choice.n < 1)||(temp_choice.n > 9)){
                continue;
            }
            if(flag[temp_choice.n - 1] == 1){
                fprintf(stderr, "DUPLICATE NUMBER %d IN COLUMN %d\n", temp_choice.n, j);
                break;
            }
            flag[temp_choice.n - 1] = 1;
        }
    }

    /* check 3x3 subgrids */
    for(big_i=0; big_i<3; big_i++){
        for(big_j=0; big_j<3; big_j++){
            for(i=0; i<9; i++){
                flag[i] = 0;
            }
            for(i=0; i<3; i++){
                for(j=0; j<3; j++){
                    temp_choice.i = i + (3*big_i);
                    temp_choice.j = j + (3*big_j);
                    temp_choice.n = 0;
                    temp_choice = grid_read_value(g, temp_choice);
                    if((temp_choice.n < 1)||(temp_choice.n > 9)){
                        continue;
                    }
                    if(flag[temp_choice.n - 1] == 1){
                        fprintf(stderr, "DUPLICATE NUMBER %d IN 3x3 BLOCK STARTING AT [%d][%d]\n",
                            temp_choice.n, (big_i*3), (big_j*3));
                        i=3;
                        j=3;
                        break;
                    }
                    flag[temp_choice.n - 1] = 1;
                }
            }
        }
    }
}
#endif

int main(int argc, char * argv[]){
    Grid_T temp_grid, sol;
    Choice_T c;
    int nelts, uniq;

    if((argc == 2)&&(strcmp(argv[1], "-c") == 0)){
        temp_grid = sudoku_read();
        sudoku_print(stderr, temp_grid);
        if(sudoku_is_correct(temp_grid)){
            fprintf(stderr, "Puzzle solution is correct.\n");
        }else{
            fprintf(stderr, "Puzzle solution is incorrect.\n");
        }
        #ifdef DEBUG
        sudoku_print_errors(temp_grid);
        #endif
    }else if(((argc == 3)||(argc == 4)) && (strcmp(argv[1], "-g") == 0)){
        nelts = atoi(argv[2]);
        uniq = 0;

        if(argc == 4){
            if(strcmp(argv[3], "-u") == 0){
                uniq = 1;
            }
        }

        temp_grid = sudoku_generate(nelts, uniq);
        sudoku_print(stdout, temp_grid);
    }else if(argc == 1){
        temp_grid = sudoku_read();
        sudoku_print(stderr, temp_grid);
        
        c.i = 0;
        c.j = 0;
        c.n = 0;

        sol = sudoku_solve(temp_grid, c);

        if(sudoku_is_correct(sol)){
            if(grid_unique(sol)){
                fprintf(stderr, "Puzzle has a (unique choice) solution:\n");
            }else{
                fprintf(stderr, "Puzzle has a (non-unique choice) solution:\n");
            }
        }else{
            fprintf(stderr, "Puzzle has no solution:\n");
            #ifdef DEBUG
            sudoku_print_errors(sol);
            #endif
        }

        sudoku_print(stdout, sol);
    }

    return 0;
}

