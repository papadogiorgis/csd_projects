/*PAPADAKIS GEORGIOS 4975
 *ASSIGNMENT 2
 *MAIN.C               */

#include "mystring.h"
#include <stdio.h>

int main(void){
	char str1[1000];
	char temp[1000] = " ";
	char str2[1000];
	char *p;
	int n;
	printf("Type a string (max 1000 characters): ");
	scanf("%s",str1);
	printf("ms_length(%s) = %ld\n",str1,ms_length(str1));
	printf("Type a second string (max 1000 characters): ");
	scanf("%s",str2);
	printf("ms_length(%s) = %ld\n\n",str2,ms_length(str2));

	ms_copy(temp,str1);
	printf("ms_copy(%s, %s) = %s\n",temp, str2, ms_copy(str1,str2));
	ms_copy(str1,temp);
	printf("Give int for ms_ncopy: ");
	scanf("%d",&n);
	printf("ms_ncopy(%s, %s, %d) = %s\n\n",temp, str2, n, \
ms_ncopy(str1,str2,n));

	ms_copy(str1,temp);
	printf("ms_concat(%s, %s) = %s\n",temp, str2, ms_concat(str1,str2));
	ms_copy(str1,temp);
	printf("Give int for ms_nconcat: ");
	scanf("%d",&n);
	printf("ms_nconcat(%s, %s, %d) = %s\n\n",temp, str2, n,\
ms_nconcat(str1,str2,n));

	ms_copy(str1,temp);
	printf("ms_compare(%s, %s) = %d\n",temp, str2, ms_compare(str1,str2));
	ms_copy(str1,temp);
	printf("Give int for ms_ncompare: ");
	scanf("%d",&n);
	printf("ms_ncompare(%s, %s, %d) = %d\n\n",temp, str2, n,\
ms_ncompare(str1,str2,n));

	ms_copy(str1,temp);
	p = ms_search(str1,str2);
	if(p == NULL){
		printf("ms_search(%s,%s) = NULL\n",temp, str2);
	}else{
		printf("ms_search(%s,%s) = %s\n",temp, str2, p);
	}

	return 0;
}
