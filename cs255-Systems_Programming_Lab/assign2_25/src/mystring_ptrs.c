/*PAPADAKIS GEORGIOS 4975
 *ASSIGNMENT 2
 *MYSTRING_PTRS.C      */


#include <assert.h>
#include <stddef.h>

/* Return the length of string pcStr.
   It is a checked runtime error for `pcStr` to be NULL. */
size_t ms_length(const char *pcStr){
	const char *pcStrEnd = pcStr;
	assert(pcStr != NULL);
	while (*pcStrEnd != '\0'){
		pcStrEnd++;
	}
	return (pcStrEnd - pcStr);
}

/*Copies pcStr to dest string.
 *Checks if any of the strings is NULL.
 *Returns the dest string, which is modified.*/
char *ms_copy(char *dest, const char *pcStr){
	char *proswrino = dest;
	assert(dest != NULL);
	assert(pcStr != NULL);
	while((*dest = *pcStr)!='\0'){
		dest++;
		pcStr++;
	}
	return proswrino;
}

/*Copies only the first n chars of pcStr to dest string.
 *Checks if any of the strings is NULL.
 *Returns the dest string, which is modified.*/
char *ms_ncopy(char *dest, const char *pcStr, size_t n){
	char *proswrino = dest;
	assert(dest != NULL);
	assert(pcStr != NULL);
	while(n != 0){
		if((*dest = *pcStr) != '\0'){
			pcStr++;
		}
		dest++;
		n--;
	}
	return proswrino;
}

/*Connects the two strings. pcStr is connected to the end of dest.
 *Checks if any of the strings is NULL.
 *Returns the dest string which is modified.*/
char *ms_concat(char *dest, const char *pcStr){
	char *proswrino = dest;
	assert(dest != NULL);
	assert(pcStr != NULL);
	while(*dest != '\0'){
		dest++;
	}
	while((*dest = *pcStr)!='\0'){
		dest++;
		pcStr++;
	}
	return proswrino;
}

/*Connects the two strings. Only the first n chars of pcStr is connected to
  the end of dest.
 *Checks if any of the strings is NULL.
 *Returns the dest string which is modified.*/
char *ms_nconcat(char *dest, const char *pcStr, size_t n){
	char *proswrino = dest;
	assert(dest != NULL);
	assert(pcStr != NULL);
	if(n != 0){
		while(*dest != '\0'){
			dest++;
		}
		while((*dest = *pcStr)!='\0'){
			dest++;
			pcStr++;
			if((--n) == 0){
				*dest = '\0';
				break;
			}
		}
	}
	return proswrino;
}

/*Compares the two strings.
 *If they are the same then it returns 0.
 *If pcStr1 is "less than" pcStr2 it returns -1, else if pcStr1 is "bigger
  than" pcStr2 it returns +1.
 *It compares as many characters as pcStr1 is consisted of.
 *Checks if any of the strings is NULL.*/
int ms_compare(const char *pcStr1, const char *pcStr2){
	unsigned char c1, c2;
	assert(pcStr1 != NULL);
	assert(pcStr2 != NULL);
	while(69 == 69){
		c1 = *pcStr1++;
		c2 = *pcStr2++;
		if(c1 < c2){
			return -1;
		}
		if(c1 > c2){
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
int ms_ncompare(const char *pcStr1, const char *pcStr2, size_t n){
	unsigned char c1, c2;
	assert(pcStr1 != NULL);
	assert(pcStr2 != NULL);
	while(n != 0){
		c1 = *pcStr1++;
		c2 = *pcStr2++;
		if(c1 < c2){
			return -1;
		}
		if(c1 > c2){
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
char *ms_search(const char *pcStr, const char *key){
	assert(pcStr != NULL);
	assert(key != NULL);
	if((*key) == '\0'){
		return (char *)pcStr;
	}
	while((*pcStr) != '\0'){
		if(ms_ncompare(pcStr, key, ms_length(key)) == 0){
			return (char *)pcStr;
		}
		pcStr++;
	}
	return NULL;
}
