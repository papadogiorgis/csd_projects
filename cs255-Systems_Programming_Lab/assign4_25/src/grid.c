/*PAPADAKIS GEORGIOS 4975
 *ASSIGNMENT 4
 *GRID.C               */

#include "grid.h"

/* init g with values from array v */
static Grid_T init(Grid_T g, int v[9][9], int i, int j){
    int temp;

    if(i == 9){
        return g;
    }
    if(j == 9){
        return init(g, v, i+1, 0);
    }

    for(temp = 1; temp < 10; temp++){
        g.cell[i][j].choices[temp] = 0;
    }

    if((v[i][j] > 0)&&(v[i][j] < 10)){
        g.cell[i][j].choices[0] = v[i][j];
        g.cell[i][j].count = 0;
    }else{
        g.cell[i][j].choices[0] = 0;
        g.cell[i][j].count = 9;
        for(temp = 1; temp < 10; temp++){
            g.cell[i][j].choices[temp] = 1;
        }
    }

    return init(g, v, i, j+1);
}

Grid_T grid_init(Grid_T g, int v[9][9]){
    g.unique = 0;
    return init(g, v, 0, 0);
}

/* update value of c.i,c.j to c.n and eliminate c from choices in grid */
static Grid_T update(Grid_T g, Choice_T c, int i, int j){
    int temp;

    if(i == 9){
        return g;
    }
    if(j == 9){
        return update(g, c, i+1, 0);
    }

    if((i == c.i)&&(j == c.j)){
        g.cell[i][j].choices[0] = c.n;
        g.cell[i][j].count = 0;
        for(temp = 1; temp < 10; temp++){
            g.cell[i][j].choices[temp] = 0;
        }
    }else if((i == c.i)||(j == c.j)||(((i/3) == (c.i/3))&&((j/3) == (c.j/3)))){
        if((g.cell[i][j].choices[c.n] == 1)&&(g.cell[i][j].choices[0] == 0)){
            g.cell[i][j].choices[c.n] = 0;
            g.cell[i][j].count--;
        }
    }

    return update(g, c, i, j+1);
}

Grid_T grid_update(Grid_T g, Choice_T c){
    return update(g, c, 0, 0);
}

/* iterate over all choices in all cells starting from (t.i,t.j,t.n+1). 
If no choice is left, return (0,0,0) */
static Choice_T iterate(Grid_T g, int i, int j, int n){
    Choice_T to_be_returned;

    if(i == 9){
        to_be_returned.i = 0;
        to_be_returned.j = 0;
        to_be_returned.n = 0;
        return to_be_returned;
    }
    
    if(j == 9){
        return iterate(g, i + 1, 0, 1);
    }

    if(n > 9){
        return iterate(g, i, j + 1, 1);
    }

    if(g.cell[i][j].choices[n] == 1){
        to_be_returned.i = i;
        to_be_returned.j = j;
        to_be_returned.n = n;
        return to_be_returned;
    }else{
        return iterate(g, i, j, n + 1);
    }
}

Choice_T grid_iterate(Grid_T g, Choice_T t){
    return iterate(g, t.i, t.j, t.n + 1);
}

/* return unique flag for g */
int grid_unique(Grid_T g){
    return g.unique;
}

/* return a cell with a unique choice, if one exists, otherwise return (0,0,0) */
Choice_T grid_exist_unique(Grid_T g){
    int i, j, k;
    Choice_T to_be_returned;

    for(i=0; i<9; i++){
        for(j=0; j<9; j++){
            if(g.cell[i][j].count == 1){
                for(k=1; k<10; k++){
                    if(g.cell[i][j].choices[k] == 1){
                        to_be_returned.i = i;
                        to_be_returned.j = j;
                        to_be_returned.n = k;
                        return to_be_returned;
                    }
                }
            }
        }
    }

    to_be_returned.i = 0;
    to_be_returned.j = 0;
    to_be_returned.n = 0;
    return to_be_returned;
}

/* clear unique flag */
Grid_T grid_clear_unique(Grid_T g){
    g.unique = 0;
    return g;
}

/* return value of i,j */
Choice_T grid_read_value(Grid_T g, Choice_T c){
    Choice_T to_be_returned;

    to_be_returned.i = c.i;
    to_be_returned.j = c.j;
    to_be_returned.n = g.cell[c.i][c.j].choices[0];

    return to_be_returned;
}

#ifdef DEBUG
void grid_cell_print(FILE *stream, Grid_T g, Choice_T c){
    fprintf(stream, "cell[%d][%d]: value = %d, count = %d\n", c.i, c.j,
    g.cell[c.i][c.j].choices[0], g.cell[c.i][c.j].count);
}
#endif

