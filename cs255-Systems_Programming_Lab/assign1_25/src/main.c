/*PAPADAKIS GEORGIOS 4975 / ASK1 */
#include <stdio.h>
#include <stdlib.h>

void gemisma_grammata(char grammata[256][3]){
        int i,k=0;
        char mikra[]="avgdezh8iklmnqoprsstyfxyw";
/*i create a string with all the small greek letters*/
        char kefalaia[]="AVGDEZH8IKLMNQOPR-STYFXYW";/*and one for the capital letters*/

        /*every character that isnt greek stays as it is and gets into grammata table*/
        for(i=0;i<256;i++){
                grammata[i][0]=i;
                grammata[i][1]='\0';
                grammata[i][2]='\0';
        }

        /*i save every small greek letter*/
        for(i=225;i<250;i++){
                grammata[i][0]=mikra[k];
                k++;
        }
        k=0;

        /*and every capital greek letter*/
        for(i=193;i<218;i++){
                grammata[i][0]=kefalaia[k];
                k++;
        }

        /*WORD STRESS = 39
         *UMLAUT = 34
         *small letters with word stress*/
        grammata[220][0] = 'a';
        grammata[220][1] = 39;
        grammata[221][0] = 'e';
        grammata[221][1] = 39;
        grammata[222][0]  = 'h';
        grammata[222][1] = 39;
        grammata[223][0] = 'i';
        grammata[223][1] = 39;
        grammata[252][0] = 'o';
        grammata[252][1] = 39;
        grammata[253][0] = 'y';
        grammata[253][1] = 39;
        grammata[254][0] = 'w';
        grammata[254][1] = 39;
        /*capital letters with word stress*/
        grammata[182][0] = 39;
        grammata[182][1] = 'A';
        grammata[184][0] = 39;
        grammata[184][1] = 'E';
        grammata[185][0] = 39;
        grammata[185][1] = 'H';
        grammata[186][0] = 39;
        grammata[186][1] = 'I';
        grammata[188][0] = 39;
        grammata[188][1] = 'O';
        grammata[190][0] = 39;
        grammata[190][1] = 'Y';
        grammata[191][0] = 39;
        grammata[191][1] = 'W';
        /*letters with umlaut*/
        grammata[218][0] = 34;
        grammata[218][1] = 'I';
        grammata[219][0] = 34;
        grammata[219][1] = 'Y';
        grammata[250][0] = 'i';
        grammata[250][1] = 34;
        grammata[251][0] = 'y';
        grammata[251][1] = 34;
        grammata[192][0] = 'i';
        grammata[192][1] = 34;
        grammata[192][2] = 39;
        grammata[224][0] = 'y';
        grammata[224][1] = 34;
        grammata[224][2] = 39;
        /*KS/ks and PS/ps*/
        grammata[238][0] = 'k';
        grammata[238][1] = 's';
        grammata[248][0] = 'p';
        grammata[248][1] = 's';

        grammata[206][0] = 'K';
        grammata[206][1] = 'S';
        grammata[216][0] = 'P';
        grammata[216][1] = 'S';
}

void ektiposi(int i, char grammata[256][3]){
        if(grammata[i][0]!='\0'){/*i print the first column of the line, if it exists*/
                putchar(grammata[i][0]);
        }
        if(grammata[i][1]!='\0'){/*print the second column, if there is one*/
                putchar(grammata[i][1]);
        }
        if(grammata[i][2]!='\0'){/*print the second column, if there is one*/
                putchar(grammata[i][2]);
        }
}

int check_flags(int c, int *fM, int *fm, int *fN, int *fn, char grammata[256][3]){
        if((*fN)==1){
        /*flag for N is raised*/
                if((c==212)||(c==244)){/*if next letter is t/T */
                        putchar('D');
			*fN=0;
                	return 1;
                }else{
                        putchar('N');
                }
                *fN=0;
        }else if((*fn)==1){
        /*flag for n is raised*/
                if((c==212)||(c==244)){/*if next letter is t/T */
                        putchar('d');
			*fn=0;
                	return 1;
                }else{
                        putchar('n');
                }
                *fn=0;
        }else if((*fM)==1){
        /*flag for M is raised*/
                if((c==208)||(c==240)){/*if next letter is p/P */
                        putchar('B');
			*fM=0;
                	return 1;
                }else{
                        putchar('M');
                }
                *fM=0;
        }else if((*fm)==1){
        /*flag for m is raised*/
                if((c==208)||(c==240)){/*if next letter is p/P */
                        putchar('b');
			*fm=0;
                	return 1;
                }else{
                        putchar('m');
                }
                *fm=0;
        }
        return 0;
}

int raise_flags(int c, int *fM, int *fm, int *fN, int *fn){
        if((c==204)&&(*fM==0)){
        /*capital greek M*/
                *fM=1;
                return 1;
        }else if((c==236)&&(*fm==0)){
        /*small greek m*/
                *fm=1;
                return 1;
        }else if((c==205)&&(*fN==0)){
        /*capital greek N*/
                *fN=1;
                return 1;
        }else if((c==237)&&(*fn==0)){
        /*small greek n*/
                *fn=1;
                return 1;
        }
        return 0;
}

void check_flags_after_eof(int c, int *fM, int *fm, int *fN, int *fn){
        if((*fN)==1){
                putchar('N');
        }else if((*fn)==1){
                putchar('n');
        }else if((*fM)==1){
                putchar('M');
        }else if((*fm)==1){
                putchar('m');
        }
}

int main(){
        int c, fM=0, fm=0, fN=0, fn=0;/*int c represents each letter from the input*/
        /*i have a flag for each case of mp and nt*/
        int f_check, f_raise;
        char grammata[256][3];/*i create a table of 256 lines and 3 columns.
The first column is for each letter, and the 2 others are for word stress and for umlaut*/
        gemisma_grammata(grammata);/*this function fills the grammata table*/
        while((c=getchar())!=EOF){
                f_check = check_flags(c,&fM,&fm,&fN,&fn,grammata);
                f_raise = raise_flags(c,&fM,&fm,&fN,&fn);
                if((f_check==0)&&(f_raise==0)){/*if i didnt raise a flag and i didnt lower a flag
                then i print the char c*/
                        ektiposi(c,grammata);
                }
        }
        check_flags_after_eof(c,&fM,&fm,&fN,&fn);/*if i get an eof while a flag is raised and
        i have not printed its letter yet, i call this function to print the letter that has
        a raised flag*/
        return 0;
}
/*PAPADAKIS GEORGIOS 4975 / ASK1 */
