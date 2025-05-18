/*****************************************************
 * @file   main.c                                    *
 * @author Paterakis Giorgos <geopat@csd.uoc.gr>     *
 *                                                   *
 * @brief Main Function for Data Structures (CS240b) *
 * Project: Winter 2023						         *
 *****************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>

#include "Movie.h"

#define BUFFER_SIZE 1024  /**< Maximum length of a line in input file */

/* Uncomment the following line to enable debugging prints 
 * or comment to disable it */
/*#define DEBUG*/ /*!!!!!!!!!!!!!!*/

#ifdef DEBUG
#define DPRINT(...) fprintf(stderr, __VA_ARGS__);
#else  /* DEBUG */
#define DPRINT(...)
#endif /* DEBUG */

int          hashtable_size; 	/** The size of the users hashtable (>0) */
int max_users;         /** The maximum number of registrations (users) */
int max_id;            /** The maximum user ID */
int global_counter;

// This is a very conservative progress on the hashtable. Our purpose
// is to force many rehashes to check the stability of the code.
int primes_g[170] = 		{  5,   7,  11,  13,  17,  19,  23,  29,  31,  37,
                               41,  43,  47,  53,  59,  61,  67,  71,  73,  79,
                               83,  89,  97, 101, 103, 107, 109, 113, 127, 131,
                              137, 139, 149, 151, 157, 163, 167, 173, 179, 181,
                              191, 193, 197, 199, 211, 223, 227, 229, 233, 239,
                              241, 251, 257, 263, 269, 271, 277, 281, 283, 293,
                              307, 311, 313, 317, 331, 337, 347, 349, 353, 359,
                              367, 373, 379, 383, 389, 397, 401, 409, 419, 421,
                              431, 433, 439, 443, 449, 457, 461, 463, 467, 479,
                              487, 491, 499, 503, 509, 521, 523, 541, 547, 557,
                              563, 569, 571, 577, 587, 593, 599, 601, 607, 613,
                              617, 619, 631, 641, 643, 647, 653, 659, 661, 673,
                              677, 683, 691, 701, 709, 719, 727, 733, 739, 743,
                              751, 757, 761, 769, 773, 787, 797, 809, 811, 821,
                              823, 827, 829, 839, 853, 857, 859, 863, 877, 881,
                              883, 887, 907, 911, 919, 929, 937, 941, 947, 953,
                          	  967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021 };

user_t **user_hashtable_p;	/* The users hashtable. This is an array of chains (pinakas katakermatismoy xrhstwn)*/
new_movie_t *new_releases;     /* New releases simply-linked binary tree*/
movieCategory_t *categoryArray[6];  /* The categories array (pinakas kathgoriwn)*/

int register_user(int newuserID){
	int position = newuserID % hashtable_size;
	user_t *temp;
	int i=0;
	if(user_hashtable_p[position]==NULL){
		user_hashtable_p[position]=malloc(sizeof(user_t));
		user_hashtable_p[position]->history=NULL;
		user_hashtable_p[position]->next=NULL;
		user_hashtable_p[position]->userID=newuserID;
	}else{
		temp=user_hashtable_p[position];
		while(temp->next!=NULL){
			temp=temp->next;
		}
		temp->next=malloc(sizeof(user_t));
		temp=temp->next;
		temp->next=NULL;
		temp->history=NULL;
		temp->userID=newuserID;
	}

	temp=user_hashtable_p[position];
	printf("R <%d>\nChain <%d> of Users:",newuserID,position);
	while(temp->next!=NULL){
		printf("\n    <%d>",temp->userID);
		temp=temp->next;
	}
	printf("\n    <%d>",temp->userID);
	printf("\nDONE\n");
	return 1;
}

int unregister_user(int newuserID){
	int position = newuserID % hashtable_size;
	user_t *temp;
	user_t *prev;
	prev=NULL;
	temp=user_hashtable_p[position];
	int flag=0;
	while(flag==0){
		if(temp->userID==newuserID){
			flag=1;
			temp->history=NULL;
			if(prev==NULL){
				user_hashtable_p[position]=user_hashtable_p[position]->next;
			}else{
				prev->next=temp->next;
			}
		}
		prev=temp;
		if(temp->next!=NULL){
			temp=temp->next;
		}else{
			if(flag==0){
				flag=-1;
			}
		}
	}

	printf("U <%d>",newuserID);
	if(flag==-1){
		printf("\nUSER NOT FOUND!!\nDONE\n");
		return 0;
	}else{
		temp=user_hashtable_p[position];
		printf("\nChain <%d> of Users:",position);
		if(temp!=NULL){
			while(temp->next!=NULL){
				printf("\n    <%d>",temp->userID);
				temp=temp->next;
			}
			printf("\n    <%d>",temp->userID);
		}
		printf("\nDONE\n");
		return 1;
	}
}

void print_new_releases(new_movie_t *temp){
	if(temp==NULL){
		return;
	}
	print_new_releases(temp->lc);
	if(global_counter!=0){
		printf(", ");
	}
	global_counter++;
	printf("<%d>",temp->movieID);
	print_new_releases(temp->rc);
}

int add_new_movie(int newmovieID, int newcategory, int newyear){
	new_movie_t *temp=new_releases;
	int flag=0;
	if(temp==NULL){
		temp=malloc(sizeof(struct new_movie));
		new_releases=temp;
	}else{
		while(flag==0){
			if(temp->movieID>newmovieID){
				if(temp->lc!=NULL){
					temp=temp->lc;
				}else{
					flag=1;
				}
			}else{
				if(temp->rc!=NULL){
					temp=temp->rc;
				}else{
					flag=2;
				}
			}
		}
		if(flag==1){
			temp->lc=malloc(sizeof(struct new_movie));
			temp=temp->lc;
		}else if(flag==2){
			temp->rc=malloc(sizeof(struct new_movie));
			temp=temp->rc;
		}
	}
	temp->category=newcategory;
	temp->year=newyear;
	temp->movieID=newmovieID;
	temp->watchedCounter=0;
	temp->sumScore=0;
	temp->lc=NULL;
	temp->rc=NULL;

	printf("A <%d> <%d> <%d>\nNew releases Tree:\n    <new_releases>: ",newmovieID,newcategory,newyear);
	global_counter=0;
	print_new_releases(new_releases);
	printf("\nDONE\n");
	return 1;
}

void distribute(new_movie_t *temp){
	int flag;
	struct movie *cat_temp;
	if(temp==NULL){
		return;
	}
	distribute(temp->lc);
	cat_temp=categoryArray[temp->category]->movie;
	if(categoryArray[temp->category]->movie==categoryArray[temp->category]->sentinel){
		categoryArray[temp->category]->movie=malloc(sizeof(struct movie));
		cat_temp=categoryArray[temp->category]->movie;
	}else{
		flag=0;
		while(flag==0){
			if(cat_temp->movieID>temp->movieID){
				if(cat_temp->lc!=categoryArray[temp->category]->sentinel){
					cat_temp=cat_temp->lc;
				}else{
					flag=1;
				}
			}else{
				if(cat_temp->rc!=categoryArray[temp->category]->sentinel){
					cat_temp=cat_temp->rc;
				}else{
					flag=2;
				}
			}
		}
		if(flag==1){
			cat_temp->lc=malloc(sizeof(struct movie));
			cat_temp=cat_temp->lc;
		}else if(flag==2){
			cat_temp->rc=malloc(sizeof(struct movie));
			cat_temp=cat_temp->rc;
		}
	}
	cat_temp->lc=categoryArray[temp->category]->sentinel;
	cat_temp->rc=categoryArray[temp->category]->sentinel;
	cat_temp->movieID=temp->movieID;
	cat_temp->sumScore=temp->sumScore;
	cat_temp->watchedCounter=temp->watchedCounter;
	cat_temp->year=temp->year;
	distribute(temp->rc);
}

void print_distributed(struct movie *temp){
	if(temp->movieID==-1){
		return;
	}
	print_distributed(temp->lc);
	if(global_counter!=0){
		printf(", ");
	}
	global_counter++;
	printf("<%d>",temp->movieID);
	print_distributed(temp->rc);
}

int distribute_movies(void){
	new_movie_t *temp=new_releases;
	distribute(temp);
	printf("D\nMovie Category Array:");
	printf("\n    Horror: ");
	global_counter=0;
	print_distributed(categoryArray[0]->movie);
	printf("\n    Science-Fiction: ");
	global_counter=0;
	print_distributed(categoryArray[1]->movie);
	printf("\n    Drama: ");
	global_counter=0;
	print_distributed(categoryArray[2]->movie);
	printf("\n    Romance: ");
	global_counter=0;
	print_distributed(categoryArray[3]->movie);
	printf("\n    Documentary: ");
	global_counter=0;
	print_distributed(categoryArray[4]->movie);
	printf("\n    Comedy: ");
	global_counter=0;
	print_distributed(categoryArray[5]->movie);
	printf("\nDONE\n");
	return 1;
}

void print_searched(struct movie *temp, int newmovieID, int newcategory){
	if(temp->movieID==-1){
		return;
	}
	print_searched(temp->lc,newmovieID,newcategory);
	if(temp->movieID==newmovieID){
		global_counter++;
		printf("I <%d> <%d> <%d>\nDONE\n",newmovieID,newcategory,temp->year);
	}
	print_searched(temp->rc,newmovieID,newcategory);
}

int search_movie(int newmovieID, int newcategory){
	global_counter=0;
	print_searched(categoryArray[newcategory]->movie,newmovieID,newcategory);
	if(global_counter==0){
		printf("I <%d> <%d>\nMOVIE NOT FOUND!!!\nDONE\n",newmovieID,newcategory);
		return 0;
	}else{
		return 1;
	}
}

void search_to_watch(struct movie *temp, int newmovieID, int newcategory, int newscore){
	if(temp->movieID==-1){
		return;
	}
	search_to_watch(temp->lc,newmovieID,newcategory,newscore);
	if(temp->movieID==newmovieID){
		global_counter++;
		temp->sumScore+=newscore;
		temp->watchedCounter++;
	}
	search_to_watch(temp->rc,newmovieID,newcategory,newscore);
}

void print_watched(struct user_movie *user_history){
	if(user_history==NULL){
		return;
	}
	print_watched(user_history->lc);
	printf("\n    <%d, %d>",user_history->movieID,user_history->score);
	print_watched(user_history->rc);
}

int watch_movie(int newuserID, int newcategory,int newmovieID, int newscore){
	int position = newuserID % hashtable_size;
	struct user_movie *temp_movie;
	int flag=0;
	struct user_movie *history_movie;
	user_t *temp_user;
	temp_user=user_hashtable_p[position];
	while(flag==0){
		if(temp_user->userID==newuserID){
			flag=1;
		}
		if((temp_user->next!=NULL)&&(flag==0)){
			temp_user=temp_user->next;
		}else if((temp_user->next==NULL)&&(flag==0)){
			flag=-1;
		}
	}
	if(flag==-1){
		printf("W <%d><%d><%d><%d>\nUSER NOT FOUND!!!\nDONE\n",newuserID,newcategory,newmovieID,newscore);
		return 0;
	}
	global_counter=0;
	search_to_watch(categoryArray[newcategory]->movie,newmovieID,newcategory,newscore);
	if(global_counter==0){
		printf("W <%d><%d><%d><%d>\nMOVIE NOT FOUND!!!\nDONE\n",newuserID,newcategory,newmovieID,newscore);
		return 0;
	}
	printf("W <%d><%d><%d><%d>\nHistory Tree of user <%d>:",newuserID,newcategory,newmovieID,newscore,newuserID);
	if(global_counter!=0){
		temp_movie=malloc(sizeof(struct user_movie));
		temp_movie->category=newcategory;
		temp_movie->movieID=newmovieID;
		temp_movie->score=newscore;
		temp_movie->lc=NULL;
		temp_movie->rc=NULL;
		if(temp_user->history==NULL){
			temp_movie->parent=NULL;
			temp_user->history=temp_movie;
		}else{
			flag=0;
			history_movie=temp_user->history;
			while(flag==0){
				if(history_movie->movieID>newmovieID){
					if(history_movie->lc!=NULL){
						history_movie=history_movie->lc;
					}else{
						flag=1;
					}
				}else{
					if(history_movie->rc!=NULL){
						history_movie=history_movie->rc;
					}else{
						flag=2;
					}
				}
			}
			if(flag==1){
				history_movie->lc=temp_movie;
				temp_movie->parent=history_movie;
			}else if(flag==2){
				history_movie->rc=temp_movie;
				temp_movie->parent=history_movie;
			}
		}
	}
	print_watched(temp_user->history);
	printf("\nDONE\n");
	return 1;
}

void count_movies_with_higher_score(struct movie *temp_movie, int newscore){
	if(temp_movie->movieID==-1){
		return;
	}
	count_movies_with_higher_score(temp_movie->lc, newscore);
	if(temp_movie->watchedCounter!=0){
		if((temp_movie->sumScore/temp_movie->watchedCounter)>newscore){
			global_counter++;
		}
	}
	count_movies_with_higher_score(temp_movie->rc, newscore);
}

void pointers_to_voithitikos(struct movie *temp_movie, int newscore, struct movie **voithitikos){
	if(temp_movie->movieID==-1){
		return;
	}
	pointers_to_voithitikos(temp_movie->lc, newscore, voithitikos);
	if(temp_movie->watchedCounter!=0){
		if((temp_movie->sumScore/temp_movie->watchedCounter)>newscore){
			if(global_counter!=0){
				printf(", ");
			}
			voithitikos[global_counter]=temp_movie;
			printf("<%d><%d>",temp_movie->movieID,(temp_movie->sumScore/temp_movie->watchedCounter));
			global_counter++;
		}
	}
	pointers_to_voithitikos(temp_movie->rc, newscore, voithitikos);
}

int filter_movies(int newuserID, int newscore){
	struct movie **voithitikos;
	int sum,  i;
	sum=0;
	for(i=0;i<6;i++){
		global_counter=0;
		count_movies_with_higher_score(categoryArray[i]->movie, newscore);
		sum+=global_counter;
	}
	voithitikos=malloc(sum * sizeof(struct movie *));
	global_counter=0;
	printf("F <%d><%d>\n    ",newuserID,newscore);
	for(i=0;i<6;i++){
		pointers_to_voithitikos(categoryArray[i]->movie, newscore, voithitikos);
	}
	printf("\nDONE\n");
	return 1;
}

void calculate_stats(struct user_movie *history, int *A, int *B){
	if(history==NULL){
		return;
	}
	calculate_stats(history->lc, A, B);
	*A= *A + history->score;
	*B = *B + 1;
	calculate_stats(history->rc, A, B);
}

int user_stats(int newuserID){
	struct user_movie *temp_history;
	int apotelesma;
	int ScoreSum=0;
	int movie_counter=0;
	int position = newuserID % hashtable_size;
	int flag=0;
	user_t *temp_user;
	temp_user=user_hashtable_p[position];
	while(flag==0){
		if(temp_user->userID==newuserID){
			flag=1;
		}
		if((temp_user->next!=NULL)&&(flag==0)){
			temp_user=temp_user->next;
		}else if((temp_user->next==NULL)&&(flag==0)){
			flag=-1;
		}
	}
	if(flag==-1){
		printf("Q <%d>\nUSER NOT FOUND!!!\nDONE\n",newuserID);
		return 0;
	}
	if(temp_user->history==NULL){
		apotelesma=0;
	}else{
		temp_history=temp_user->history;
		calculate_stats(temp_history, &ScoreSum, &movie_counter);
	}
	if(movie_counter==0){
		apotelesma=0;
	}else{
		apotelesma=ScoreSum/movie_counter;
	}
	printf("Q <%d><%d>\nDONE\n",newuserID,apotelesma);
	return 1;
}

int print_movies(void){
	printf("M\nMovie Category Array:");
	printf("\n    Horror: ");
	global_counter=0;
	print_distributed(categoryArray[0]->movie);
	printf("\n    Science-Fiction: ");
	global_counter=0;
	print_distributed(categoryArray[1]->movie);
	printf("\n    Drama: ");
	global_counter=0;
	print_distributed(categoryArray[2]->movie);
	printf("\n    Romance: ");
	global_counter=0;
	print_distributed(categoryArray[3]->movie);
	printf("\n    Documentary: ");
	global_counter=0;
	print_distributed(categoryArray[4]->movie);
	printf("\n    Comedy: ");
	global_counter=0;
	print_distributed(categoryArray[5]->movie);
	printf("\nDONE\n");
	return 1;
}

int print_users(void){
	user_t *temp;
	int i;
	printf("P");
	for(i=0;i<hashtable_size;i++){
		printf("\nChain <%d> of Users:",i);
		temp=user_hashtable_p[i];
		while(temp!=NULL){
			printf("\n    <%d>\n    History Tree:\n",temp->userID);
			print_watched(temp->history);
			temp=temp->next;
		}
	}
	printf("\nDONE\n");
	return 1;
}

int main(int argc, char** argv)
{
	FILE *fin = NULL;
	char buff[BUFFER_SIZE], event;
	
	int i;
	for(i=0;i<6;i++){
		categoryArray[i]=malloc(sizeof(struct movie_category));
	}
	categoryArray[0]->sentinel=malloc(sizeof(struct movie));
	categoryArray[0]->sentinel->lc=NULL;
	categoryArray[0]->sentinel->rc=NULL;
	categoryArray[0]->sentinel->movieID=-1;
	categoryArray[0]->sentinel->sumScore=-1;
	categoryArray[0]->sentinel->watchedCounter=-1;
	categoryArray[0]->sentinel->year=-1;
	categoryArray[0]->movie=categoryArray[0]->sentinel;
	for(i=1;i<6;i++){
		categoryArray[i]->sentinel=categoryArray[0]->sentinel;
		categoryArray[i]->movie=categoryArray[0]->sentinel;
	}
	
	/* Check command buff arguments */
	if ( argc != 2 ) {
		fprintf(stderr, "Usage: %s <input_file> \n", argv[0]);
		return EXIT_FAILURE;
	}

	/* Open input file */
	if (( fin = fopen(argv[1], "r") ) == NULL ) {
		fprintf(stderr, "\n Could not open file: %s\n", argv[1]);
		perror("Opening test file\n");
		return EXIT_FAILURE;
	}
	/* Read input file buff-by-buff and handle the events */
	while ( fgets(buff, BUFFER_SIZE, fin) ) {

		DPRINT("Event: %s \n", buff);

		switch(buff[0]) {

		/* Comment */
		case '#':
			break;
		/* max_users */
		case '0': {
			sscanf(buff, "%c %u", &event, &max_users);
			DPRINT("max users: %u\n", max_users);
			/********************************************/
				int a;/*Load Factor*/
				int i=0;/*Diktis twn prwtwn arithmwn*/
				int flag=0;
				while(flag==0){
					if((double)max_users/primes_g[i]<2.0){
						flag=1;
					}
					if(i==169){
						flag=1;
					}
					i++;
				}
				hashtable_size = primes_g[i-1];/*Megethos Hash Table twn users*/
				user_hashtable_p=malloc(hashtable_size * sizeof(user_t *));
				for(i=0;i<hashtable_size;i++){
					user_hashtable_p[i]=NULL;
				}
			/********************************************/
			break;
		}
		/* max_id */
		case '1': {
			sscanf(buff, "%c %u", &event, &max_id);
			DPRINT("max id: %u\n", max_id);
			break;
		}	
		/* Event R : R <userID> - Register user. */
		case 'R':
		{
			int userID;
			sscanf(buff, "%c %d", &event, &userID);
			DPRINT("%c %d\n", event, userID);
			if ( register_user(userID) ) {
				DPRINT("%c succeeded\n", event);
			} else {
				fprintf(stderr, "%c failed\n", event);
			}

			break;
		}
		/*  Event U : U <userID> - Unregister user. */
		case 'U':
		{
			int userID;
			sscanf(buff, "%c %d", &event, &userID);
			DPRINT("%c %d\n", event, userID);

			if ( unregister_user(userID) ) {
				DPRINT("%c %d succeeded\n", event, userID);
			} else {
				fprintf(stderr, "%c %d failed\n", event, userID);
			}

			break;
		}
		/*  Event A : A <movieID> <category> <year> - Add new movie. */
		case 'A':
		{
			int movieID,category, year;
			sscanf(buff, "%c %d %d %d", &event, &movieID, &category, &year);
			DPRINT("%c %d %d %d\n", event, movieID, category, year);

			if ( add_new_movie(movieID, category, year) ) {
				DPRINT("%c %d %d %d succeeded\n", event, movieID, category, year);
			} else {
				fprintf(stderr, "%c %d %d %d failed\n", event, movieID, category, year);
			}

			break;
		}
		/*  Event D : D  - Distribute movies. */
		case 'D':
		{
			sscanf(buff, "%c", &event);
			DPRINT("%c\n", event);

			if ( distribute_movies() ) {
				DPRINT("%c succeeded\n", event);
			} else {
				fprintf(stderr, "%c failed\n", event);
			}

			break;
		}
		/*  Event W : W <userID ><category><movieID><score> - Watch movie */
		case 'W':
		{
			int userID, movieID,category,score;

			sscanf(buff, "%c %d %d %d %d", &event,&userID,&category, &movieID, &score);
			DPRINT("%c %d %d %d %d\n", event,userID, category, movieID, score);

			if ( watch_movie(userID,category, movieID, score) ) {
				DPRINT("%c %d %d %d %d succeeded\n", event,userID, category, movieID, score);
			} else {
				fprintf(stderr, "%c %d %d %d %d failed\n", event, userID,category, movieID, score);
			}

			break;
		}
		/*  Event –	F  <userID ><category1><category2><score> Filter movies */
		case 'F':
		{
			int userID, score;
			sscanf(buff, "%c %d %d\n", &event, &userID,&score);
			DPRINT("%c %d %d\n", event, userID,score);

			if (filter_movies(userID,score) ) {
				DPRINT("%c %d %d succeeded\n", event, userID,score);
			} else {
				fprintf(stderr, "%c %d %d failed\n", event, userID,score);
			}

			break;
		}
		/*  Event Q : Q <userID> - User statistics */
		case 'Q':
		{
			int userID;
			sscanf(buff, "%c %d\n", &event, &userID);
			DPRINT("%c %d\n", event, userID);

			if ( user_stats(userID) ) {
				DPRINT("%c %d succeeded\n", event, userID);
			} else {
				fprintf(stderr, "%c %d failed\n", event, userID);
			}

			break;
		}
		/*  Event I : I <movieID> <category> - Search movie */
		case 'I':
		{
			int movieID,category;
			sscanf(buff, "%c %d %d\n", &event, &movieID, &category);
			DPRINT("%c %d %d\n", event, movieID, category);

			if ( search_movie(movieID, category) ) {
				DPRINT("%c %d %d succeeded\n", event, movieID, category);
			} else {
				fprintf(stderr, "%c %d %d failed\n", event, movieID, category);
			}

			break;
		}
		/*  Event M : M  - Print movies */
		case 'M':
		{
			sscanf(buff, "%c", &event);
			DPRINT("%c\n", event);

			if ( print_movies() ) {
				DPRINT("%c succeeded\n", event);
			} else {
				fprintf(stderr, "%c failed\n", event);
			}

			break;
		}
		/* Event P : P  - Print users */
		case 'P':
		{
			sscanf(buff, "%c", &event);
			DPRINT("%c\n", event);

			if ( print_users() ) {
				DPRINT("%c succeeded\n", event);
			} else {
				fprintf(stderr, "%c failed\n", event);
			}

			break;
		}
		/* Empty line */
		case '\n':
			break;

		/* Ignore everything else */
		default:
			DPRINT("Ignoring buff: %s \n", buff);
			break;
		}
	}

	return (EXIT_SUCCESS);
}