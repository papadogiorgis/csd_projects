/*PAPADAKIS GEORGIOS 4975
 *ASSIGNMENT 2
 *MYSTRING.H           */

#ifndef _STRING_H_
#define _STRING_H_

#include <stddef.h>

/* Return the length of string pcStr.
   It is a checked runtime error for `pcStr` to be NULL. */
size_t ms_length(const char *pcStr);

/*Copies pcStr to dest string.
 *Checks if any of the strings is NULL.
 *Returns the dest string, which is modified.*/
char *ms_copy(char *dest, const char *pcStr);

/*Copies only the first n chars of pcStr to dest string.
 *Checks if any of the strings is NULL.
 *Returns the dest string, which is modified.*/
char *ms_ncopy(char *dest, const char *pcStr, size_t n);

/*Connects the two strings. pcStr is connected to the end of dest.
 *Checks if any of the strings is NULL.
 *Returns the dest string which is modified.*/
char *ms_concat(char *dest, const char *pcStr);

/*Connects the two strings. Only the first n chars of pcStr is connected to
  the end of dest.
 *Checks if any of the strings is NULL.
 *Returns the dest string which is modified.*/
char *ms_nconcat(char *dest, const char *pcStr, size_t n);

/*Compares the two strings.
 *If they are the same then it returns 0.
 *If pcStr1 is "less than" pcStr2 it returns -1, else if pcStr1 is "bigger
  than" pcStr2 it returns +1.
 *It compares as many characters as pcStr1 is consisted of.
 *Checks if any of the strings is NULL.*/
int ms_compare(const char *pcStr1, const char *pcStr2);

/*Compares the two strings for the first n chars.
 *If they are the same then it returns 0.
 *If pcStr1 is "less than" pcStr2 it returns -1, else if pcStr1 is "bigger
  than" pcStr2 it returns +1.
 *It compares as many characters as pcStr1 is consisted of.
 *Checks if any of the strings is NULL.*/
int ms_ncompare(const char *pcStr1, const char *pcStr2, size_t n);

/*Searches for the first occurrence of the string key (without the NULL
  terminator) in the string pcStr.
 *Checks if any of the strings is NULL.
 *Returns a pointer to the first match in pcStr or NULL if not found.*/
char *ms_search(const char *pcStr, const char *key);

#endif
