/*PAPADAKIS GEORGIOS 4975
 *ASSIGNMENT 2
 *MYSTRING_ARS.C       */


#include <assert.h>
#include <stddef.h>

/* Return the length of string pcStr.
   It is a checked runtime error for `pcStr` to be NULL. */
size_t ms_length(const char pcStr[]){
	size_t i=0U;
	assert(pcStr != NULL);
	while(pcStr[i] != '\0'){
		i++;
	}
	return i;
}

/*Copies pcStr to dest string.
 *Checks if any of the strings is NULL.
 *Returns the dest string, which is modified.*/
char *ms_copy(char dest[], const char pcStr[]){
	int i=0,k=0;
        assert(dest != NULL);
        assert(pcStr != NULL);
        while((dest[i] = pcStr[k])!='\0'){
                i++;
                k++;
        }
        return dest;
}

/*Copies only the first n chars of pcStr to dest string.
 *Checks if any of the strings is NULL.
 *Returns the dest string, which is modified.*/
char *ms_ncopy(char dest[], const char pcStr[], size_t n){
	int i=0,k=0;
        assert(dest != NULL);
        assert(pcStr != NULL);
        while(n != 0){
                if((dest[i] = pcStr[k]) != '\0'){
                        k++;
                }
                i++;
                n--;
        }
        return dest;
}

/*Connects the two strings. pcStr is connected to the end of dest.
 *Checks if any of the strings is NULL.
 *Returns the dest string which is modified.*/
char *ms_concat(char dest[], const char pcStr[]){
	int i=0,k=0;
        assert(dest != NULL);
        assert(pcStr != NULL);
        while(dest[i] != '\0'){
                i++;
        }
        while((dest[i] = pcStr[k])!='\0'){
                i++;
                k++;
        }
        return dest;
}

/*Connects the two strings. Only the first n chars of pcStr is connected to
  the end of dest.
 *Checks if any of the strings is NULL.
 *Returns the dest string which is modified.*/
char *ms_nconcat(char dest[], const char pcStr[], size_t n){
	int i=0,k=0;
        assert(dest != NULL);
        assert(pcStr != NULL);
        if(n!=0){
                while(dest[i] != '\0'){
                        i++;
                }
                while((dest[i] = pcStr[k])!='\0'){
                        i++;
                        k++;
                        if((--n) == 0){
                                dest[i] = '\0';
                                break;
                        }
                }
        }
        return dest;
}

/*Compares the two strings.
 *If they are the same then it returns 0.
 *If pcStr1 is "less than" pcStr2 it returns -1, else if pcStr1 is "bigger
  than" pcStr2 it returns +1.
 *It compares as many characters as pcStr1 is consisted of.
 *Checks if any of the strings is NULL.*/
int ms_compare(const char pcStr1[], const char pcStr2[]){
	int i=0,k=0;
        unsigned char c1, c2;
        assert(pcStr1 != NULL);
        assert(pcStr2 != NULL);
        while(69 == 69){
                c1 = pcStr1[i];
		i++;
                c2 = pcStr2[k];
		k++;
                if(c1<c2){
                        return -1;
                }
                if(c1>c2){
                        return 1;
                }
                if(c1 == '\0'){
                        break;
                }
        }
        return 0;
}

/*Compares the two strings for the first n chars.
 *If they are the same then it returns 0.
 *If pcStr1 is "less than" pcStr2 it returns -1, else if pcStr1 is "bigger
  than" pcStr2 it returns +1.
 *It compares as many characters as pcStr1 is consisted of.
 *Checks if any of the strings is NULL.*/
int ms_ncompare(const char pcStr1[], const char pcStr2[], size_t n){
	int i=0,k=0;
        unsigned char c1, c2;
        assert(pcStr1 != NULL);
        assert(pcStr2 != NULL);
        while(n != 0){
                c1 = pcStr1[i];
		i++;
                c2 = pcStr2[k];
		k++;
                if(c1<c2){
                        return -1;
                }
                if(c1>c2){
                        return 1;
                }
                if(c1 == '\0'){
                        break;
                }
                n--;
        }
        return 0;
}

/*Searches for the first occurrence of the string key (without the NULL
  terminator) in the string pcStr.
 *Checks if any of the strings is NULL.
 *Returns a pointer to the first match in pcStr or NULL if not found.*/
char *ms_search(const char pcStr[], const char key[]){
        size_t m1,m2;
        int i = 0;
        assert(pcStr != NULL);
        assert(key != NULL);
        m1 = ms_length(pcStr);
        m2 = ms_length(key);
        if(m2 == 0){
                return (char *)pcStr;
        }
        if(m1 < m2){
                return NULL;
        }
        while(i <= m1 - m2){
                if(ms_ncompare(&pcStr[i], key, m2) == 0){
                        return (char *)&pcStr[i];
                }
                i++;
        }
        return NULL;
}
