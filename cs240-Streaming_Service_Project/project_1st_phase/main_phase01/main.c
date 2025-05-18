/*
 * ============================================
 * file: main.c
 * @Author John Malliotakis (jmal@csd.uoc.gr)
 * @Version 23/10/2023
 *
 * @e-mail hy240@csd.uoc.gr
 *
 * @brief Main function
 *        for CS240 Project Phase 1,
 *        Winter Semester 2023-2024
 * @see   Compile using supplied Makefile by running: make
 * ============================================
 */
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>

#include "streaming_service.h"

/* Maximum input line size */
#define MAX_LINE 1024

/* 
 * Uncomment the following line to
 * enable debugging prints
 * or comment to disable it
 */
/* #define DEBUG */
#ifdef DEBUG
#define DPRINT(...) fprintf(stderr, __VA_ARGS__);
#else
#define DPRINT(...)
#endif /* DEBUG */

struct user *head_user;
struct new_movie *head_new_movie;
struct new_movie *tail_new_movie;
struct movie *hor;
struct movie *sci;
struct movie *dra;
struct movie *rom;
struct movie *doc;
struct movie *com;

void init_structures(void)
{
	head_user = malloc(sizeof(struct user));
	head_user->uid=-1;
	head_user->next=NULL;
	head_user->suggestedHead=NULL;
	head_user->suggestedTail=NULL;
	head_user->watchHistory=NULL;


	head_new_movie = malloc(sizeof(struct new_movie));
	head_new_movie->info.mid=-1;
	head_new_movie->next=NULL;
	tail_new_movie=head_new_movie;

	hor = malloc(sizeof(struct new_movie));
	hor->info.mid=-1;
	hor->next=NULL;

	sci = malloc(sizeof(struct new_movie));
	sci->info.mid=-1;
	sci->next=NULL;
	
	dra = malloc(sizeof(struct new_movie));
	dra->info.mid=-1;
	dra->next=NULL;
	
	rom = malloc(sizeof(struct new_movie));
	rom->info.mid=-1;
	rom->next=NULL;
	
	doc = malloc(sizeof(struct new_movie));
	doc->info.mid=-1;
	doc->next=NULL;
	
	com = malloc(sizeof(struct new_movie));
	com->info.mid=-1;
	com->next=NULL;
	
	/*
	 * TODO: Initialize your
	 * global structures here,
	 * i.e., the user list (and sentinel
	 * node), new releases list, category
	 * table
	 */
}

void destroy_structures(void)
{
	/*
	 * TODO: For a bonus
	 * empty all lists and stacks
	 * and free all memory associated
	 * with list/stack nodes here
	 */
}

int register_user(int newuid){
	struct user *u=head_user;
	struct user *newuser;
	while(u->next!=NULL){
		if(u->uid==newuid){
			printf("THIS USERNAME ALREADY EXISTS\n");
			return -1;
		}
		u=u->next;
	}
	if(u->uid==newuid){
		printf("THIS USERNAME ALREADY EXISTS\n");
		return -1;
	}else{
		newuser= malloc(sizeof(struct user));
		u->next=newuser;
		newuser->next=NULL;
		newuser->uid=newuid;
		newuser->suggestedHead=NULL;
		newuser->suggestedTail=NULL;
		newuser->watchHistory=NULL;
	}

	u=head_user;
	u=u->next;
	printf("R <%d>\n  Users:",newuid);
	while(u->next!=NULL){
		printf(" <%d>,",u->uid);
		u=u->next;
	}
	printf(" <%d>\nDONE\n",u->uid);
	return 0;
}

void unregister_user(int newuid){
	struct user *u=head_user;
	struct user *deluser=NULL;
	struct user *p=u;
	struct user *prev=NULL;
	while(u->next!=NULL){
		if(u->uid==newuid){
			prev=p;
			deluser=u;
		}
		p=u;
		u=u->next;
	}
	if(u->uid==newuid){
		deluser=u;
		prev=p;
	}
	if(prev==NULL){
		printf("USER DOES NOT EXIST!\n");
	}else if(prev==head_user){
		head_user->next=deluser->next;
	}else{
		prev->next=deluser->next;
	}
	u=head_user;
	u=u->next;
	printf("U <%d>\n  Users:",newuid);
	while(u->next!=NULL){
		printf(" <%d>,",u->uid);
		u=u->next;
	}
	printf(" <%d>\nDONE\n",u->uid);
	return;
}

int add_new_movie(unsigned newmid, movieCategory_t newcategory, unsigned newyear){
	struct new_movie *u=head_new_movie;
	struct new_movie *prev=head_new_movie;
	struct new_movie *p=u;
	struct new_movie *curr=u;
	struct new_movie *nmovie;
	int flag=0;
	if(head_new_movie==tail_new_movie){
		flag=2;
	}else{
		u=u->next;
		while((flag==0)){
			if(u->info.mid>newmid){
				flag=1;
				prev=p;
				curr=u;
			}
			if(u->info.mid==newmid){
				printf("MOVIE ALREADY EXISTS!\n");
				return -1;
			}
			if((u->next==NULL)&&(flag==0)){
				flag=2;
			}
			p=u;
			u=u->next;
		}
	}
	if(flag==1){
		nmovie = malloc(sizeof(struct new_movie));
		prev->next=nmovie;
		nmovie->next=curr;
		nmovie->info.mid=newmid;
		nmovie->info.year=newyear;
		nmovie->category=newcategory;
	}else if(flag==2){
		nmovie = malloc(sizeof(struct new_movie));
		tail_new_movie->next=nmovie;
		tail_new_movie=nmovie;
		nmovie->next=NULL;
		nmovie->info.mid=newmid;
		nmovie->info.year=newyear;
		nmovie->category=newcategory;
	}

	u=head_new_movie;
	u=u->next;
	printf("A <%d> <%d> <%d>\n  New movies=",newmid,newcategory,newyear);
	while(u->next!=NULL){
		printf(" <%d,%d,%d>,",u->info.mid,u->category,u->info.year);
		u=u->next;
	}
	printf(" <%d,%d,%d>\nDONE\n",u->info.mid,u->category,u->info.year);
	return 0;
}

void distribute_new_movies(void){
	struct new_movie *u=head_new_movie;
	struct new_movie *temp;
	struct movie *a=hor;
	struct movie *b=sci;
	struct movie *c=dra;
	struct movie *d=rom;
	struct movie *e=doc;
	struct movie *f=com;
	struct movie *t;
	int num=0;
	if(u->next==NULL){
		return;
	}
	temp = malloc(sizeof(struct new_movie));
	temp->info.mid=-1;
	temp->next=NULL;
	tail_new_movie->next=temp;
	u=u->next;
	while(u!=temp){
		if(u->category==HORROR){
			t=malloc(sizeof(struct movie));
			a->next=t;
			a=a->next;
			a->info.mid=u->info.mid;
			a->info.year=u->info.year;
			a->next=NULL;
		}else if(u->category==SCIFI){
			t=malloc(sizeof(struct movie));
			b->next=t;
			b=b->next;
			b->info.mid=u->info.mid;
			b->info.year=u->info.year;
			b->next=NULL;
		}else  if(u->category==DRAMA){
			t=malloc(sizeof(struct movie));
			c->next=t;
			c=c->next;
			c->info.mid=u->info.mid;
			c->info.year=u->info.year;
			c->next=NULL;
		}else if(u->category==ROMANCE){
			t=malloc(sizeof(struct movie));
			d->next=t;
			d=d->next;
			d->info.mid=u->info.mid;
			d->info.year=u->info.year;
			d->next=NULL;
		}else if(u->category==DOCUMENTARY){
			t=malloc(sizeof(struct movie));
			e->next=t;
			e=e->next;
			e->info.mid=u->info.mid;
			e->info.year=u->info.year;
			e->next=NULL;
		}else if(u->category==COMEDY){
			t=malloc(sizeof(struct movie));
			f->next=t;
			f=f->next;
			f->info.mid=u->info.mid;
			f->info.year=u->info.year;
			f->next=NULL;
		}
		u=u->next;
	}
	tail_new_movie=head_new_movie;
	a=hor;
	b=sci;
	c=dra;
	d=rom;
	e=doc;
	f=com;
	printf("D\nCategorized Movies:\n  Horror:");
	while(a->next!=NULL){
		if(num>=1){
			printf(",");
		}
		a=a->next;
		num++;
		printf(" <%d,%d>",a->info.mid,num);
	}
	num=0;
	printf("\n  Sci-fi:");
	while(b->next!=NULL){
		if(num>=1){
			printf(",");
		}
		b=b->next;
		num++;
		printf(" <%d,%d>",b->info.mid,num);
	}
	num=0;
	printf("\n  Drama:");
	while(c->next!=NULL){
		if(num>=1){
			printf(",");
		}
		c=c->next;
		num++;
		printf(" <%d,%d>",c->info.mid,num);
	}
	num=0;
	printf("\n  Romance:");
	while(d->next!=NULL){
		if(num>=1){
			printf(",");
		}
		d=d->next;
		num++;
		printf(" <%d,%d>",d->info.mid,num);
	}
	num=0;
	printf("\n  Documentary:");
	while(e->next!=NULL){
		if(num>=1){
			printf(",");
		}
		e=e->next;
		num++;
		printf(" <%d,%d>",e->info.mid,num);
	}
	num=0;
	printf("\n  Comedy:");
	while(f->next!=NULL){
		if(num>=1){
			printf(",");
		}
		f=f->next;
		num++;
		printf(" <%d,%d>",f->info.mid,num);
	}
	printf("\nDONE\n");
}

int watch_movie(int newuid, unsigned newmid){
	struct user *u=head_user;
	struct user *this_user;

	struct movie *a=hor;
	struct movie *b=sci;
	struct movie *c=dra;
	struct movie *d=rom;
	struct movie *e=doc;
	struct movie *f=com;
	struct movie *t=a;
	struct movie  *temp_movie;

	struct movie *temp_history;
	int flag=0;
	while(u->next!=NULL){
		if(u->uid==newuid){
			this_user=u;
			flag=1;
		}
		u=u->next;
	}
	if(u->uid==newuid){
		this_user=u;
		flag=1;
	}
	if(flag==0){
		printf("USER NOT FOUND!\n");
		return -1;
	}
	flag=1;
	while((a->next!=NULL)&&(flag==1)){
		a=a->next;
		if(a->info.mid==newmid){
			flag=2;
			t=a;
		}
	}
	while((b->next!=NULL)&&(flag==1)){
		b=b->next;
		if(b->info.mid==newmid){
			flag=2;
			t=b;
		}
	}
	while((c->next!=NULL)&&(flag==1)){
		c=c->next;
		if(c->info.mid==newmid){
			flag=2;
			t=c;
		}
	}
	while((d->next!=NULL)&&(flag==1)){
		d=d->next;
		if(d->info.mid==newmid){
			flag=2;
			t=d;
		}
	}
	while((e->next!=NULL)&&(flag==1)){
		e=e->next;
		if(e->info.mid==newmid){
			flag=2;
			t=e;
		}
	}
	while((f->next!=NULL)&&(flag==1)){
		f=f->next;
		if(f->info.mid==newmid){
			flag=2;
			t=f;
		}
	}
	if(flag==1){
		printf("MOVIE NOT FOUND!\n");
		return -1;
	}
	printf("W <%d> <%d>\n  User <%d> Watch History = ",newuid,newmid,newuid);
	if(this_user->watchHistory==NULL){
		temp_movie=malloc(sizeof(struct movie));
		this_user->watchHistory=temp_movie;
		this_user->watchHistory->info.mid=t->info.mid;
		this_user->watchHistory->info.year=t->info.year;
		this_user->watchHistory->next=NULL;
		printf("<%d>\n",newmid);
	}else{
		temp_history=this_user->watchHistory;
		while(temp_history->next!=NULL){
			printf("<%d>, ",temp_history->info.mid);
			temp_history=temp_history->next;
		}
		printf("<%d>, <%d>\n",temp_history->info.mid,newmid);
		temp_movie=malloc(sizeof(struct movie));
		temp_history->next=temp_movie;
		temp_history=temp_history->next;
		temp_history->info.mid=t->info.mid;
		temp_history->info.year=t->info.year;
		temp_history->next=NULL;
	}
	printf("DONE\n");
	return 0;
}

int suggest_movies(int newuid){
	struct user *u=head_user;
	struct user *this_user;
	struct movie  *temp_movie;
	struct movie *temp_history;
	struct suggested_movie *temp_sugg;
	struct suggested_movie *last_head=NULL;
	struct suggested_movie *last_tail=NULL;
	int m=0;
	while(u->next!=NULL){
		if(u->uid==newuid){
			this_user=u;
			m=1;
		}
		u=u->next;
	}
	if(u->uid==newuid){
		this_user=u;
		m=1;
	}
	if(m==0){
		printf("USER NOT FOUND!\n");
		return -1;
	}
	u=head_user;
	m=0;
	while(u->next!=NULL){
		u=u->next;
		if((u->uid!=newuid)&&(m%2==0)&&(u->watchHistory!=NULL)){
			if(this_user->suggestedHead==NULL){
				temp_sugg=malloc(sizeof(struct suggested_movie));
				this_user->suggestedHead=temp_sugg;
				this_user->suggestedHead->next=NULL;
				this_user->suggestedHead->prev=NULL;
				this_user->suggestedHead->info.mid=u->watchHistory->info.mid;
				this_user->suggestedHead->info.year=u->watchHistory->info.year;
				last_head=this_user->suggestedHead;
			}else{
				temp_sugg=malloc(sizeof(struct suggested_movie));
				last_head->next=temp_sugg;
				temp_sugg->prev=last_head;
				temp_sugg->next=NULL;
				temp_sugg->info.mid=u->watchHistory->info.mid;
				temp_sugg->info.year=u->watchHistory->info.year;
				last_head=temp_sugg;
			}
		}
		if((u->uid!=newuid)&&(m%2==1)&&(u->watchHistory!=NULL)){
			if(this_user->suggestedTail==NULL){
				temp_sugg=malloc(sizeof(struct suggested_movie));
				this_user->suggestedTail=temp_sugg;
				this_user->suggestedTail->next=this_user->suggestedHead;
				this_user->suggestedHead->prev=this_user->suggestedTail;
				this_user->suggestedTail->prev=NULL;
				this_user->suggestedTail->info.mid=u->watchHistory->info.mid;
				this_user->suggestedTail->info.year=u->watchHistory->info.year;
				last_tail=this_user->suggestedTail;
			}else{
				temp_sugg=malloc(sizeof(struct suggested_movie));
				last_tail->prev=temp_sugg;
				temp_sugg->next=last_tail;
				temp_sugg->prev=NULL;
				temp_sugg->info.mid=u->watchHistory->info.mid;
				temp_sugg->info.year=u->watchHistory->info.year;
				last_tail=temp_sugg;
			}
		}
		if((u->uid!=newuid)&&(u->watchHistory!=NULL)){
			m++;
		}
	}
	if((last_head!=NULL)&&(last_tail!=NULL)){
		last_head->next=last_tail;
		last_tail->prev=last_head;
	}else if((last_head!=NULL)&&(last_tail==NULL)){
		last_tail=last_head;
	}else if((last_head==NULL)&&(last_tail==NULL)){
		printf("S <%d>\n  User <%d> Suggested Movies =\nDONE\n",newuid,newuid);
		return 0;
	}
	printf("S <%d>\n  User <%d> Suggested Movies = ",newuid,newuid);
	temp_sugg=this_user->suggestedHead;
	while(temp_sugg!=this_user->suggestedTail){
		printf("<%d>, ",temp_sugg->info.mid);
		temp_sugg=temp_sugg->next;
	}
	printf("<%d>\nDONE\n",temp_sugg->info.mid);
}

int filtered_movie_search(int newuid, movieCategory_t category1,movieCategory_t category2, unsigned newyear){
	struct user *u=head_user;
	struct user *this_user;

	struct movie *a=hor;
	struct movie *b=sci;
	struct movie *c=dra;
	struct movie *d=rom;
	struct movie *e=doc;
	struct movie *f=com;

	struct suggested_movie *temp_sugg;
	struct suggested_movie *t;
	struct suggested_movie *find;
	struct suggested_movie *sugg_head=NULL;
	struct suggested_movie *sugg_tail=NULL;
	int flag=0;
	while(u->next!=NULL){
		if(u->uid==newuid){
			this_user=u;
			flag=1;
		}
		u=u->next;
	}
	if(u->uid==newuid){
		this_user=u;
		flag=1;
	}
	if(flag==0){
		printf("USER NOT FOUND!\n");
		return -1;
	}
	while(((category1==HORROR)||(category2==HORROR))&&(a->next!=NULL)){
		a=a->next;
		if(a->info.year>=newyear){
			if(sugg_head==NULL){
				temp_sugg=malloc(sizeof(struct suggested_movie));
				sugg_head=temp_sugg;
				sugg_head->next=NULL;
				sugg_head->prev=NULL;
				sugg_head->info.mid=a->info.mid;
				sugg_head->info.year=a->info.year;
				sugg_tail=sugg_head;
			}else{
				temp_sugg=malloc(sizeof(struct suggested_movie));
				sugg_tail->next=temp_sugg;
				temp_sugg->prev=sugg_tail;
				temp_sugg->next=NULL;
				temp_sugg->info.mid=a->info.mid;
				temp_sugg->info.year=a->info.year;
				sugg_tail=temp_sugg;
			}
		}
	}
	while(((category1==SCIFI)||(category2==SCIFI))&&(b->next!=NULL)){
		b=b->next;
		if(b->info.year>=newyear){
			if(sugg_head==NULL){
				temp_sugg=malloc(sizeof(struct suggested_movie));
				sugg_head=temp_sugg;
				sugg_head->next=NULL;
				sugg_head->prev=NULL;
				sugg_head->info.mid=b->info.mid;
				sugg_head->info.year=b->info.year;
				sugg_tail=sugg_head;
			}else{
				find=sugg_head;
				while((find->info.mid<b->info.mid)&&(find->next!=NULL)){
					find=find->next;
				}
				temp_sugg=malloc(sizeof(struct suggested_movie));
				if(find->next==NULL){
					sugg_tail->next=temp_sugg;
					temp_sugg->prev=sugg_tail;
					temp_sugg->next=NULL;
					temp_sugg->info.mid=b->info.mid;
					temp_sugg->info.year=b->info.year;
					sugg_tail=temp_sugg;
				}else{
					if(find->prev==NULL){
						find->prev=temp_sugg;
						temp_sugg->next=find;
						sugg_head=temp_sugg;
					}else{
						t=find->prev;
						t->next=temp_sugg;
						temp_sugg->prev=t;
						temp_sugg->next=find;
						find->prev=temp_sugg;
					}
					temp_sugg->info.mid=b->info.mid;
					temp_sugg->info.year=b->info.year;
				}
			}
		}
	}
	while(((category1==DRAMA)||(category2==DRAMA))&&(c->next!=NULL)){
		c=c->next;
		if(c->info.year>=newyear){
			if(sugg_head==NULL){
				temp_sugg=malloc(sizeof(struct suggested_movie));
				sugg_head=temp_sugg;
				sugg_head->next=NULL;
				sugg_head->prev=NULL;
				sugg_head->info.mid=c->info.mid;
				sugg_head->info.year=c->info.year;
				sugg_tail=sugg_head;
			}else{
				find=sugg_head;
				while((find->info.mid<c->info.mid)&&(find->next!=NULL)){
					find=find->next;
				}
				temp_sugg=malloc(sizeof(struct suggested_movie));
				if(find->next==NULL){
					sugg_tail->next=temp_sugg;
					temp_sugg->prev=sugg_tail;
					temp_sugg->next=NULL;
					temp_sugg->info.mid=c->info.mid;
					temp_sugg->info.year=c->info.year;
					sugg_tail=temp_sugg;
				}else{
					if(find->prev==NULL){
						find->prev=temp_sugg;
						temp_sugg->next=find;
						sugg_head=temp_sugg;
					}else{
						t=find->prev;
						t->next=temp_sugg;
						temp_sugg->prev=t;
						temp_sugg->next=find;
						find->prev=temp_sugg;
					}
					temp_sugg->info.mid=c->info.mid;
					temp_sugg->info.year=c->info.year;
				}
			}
		}
	}
	while(((category1==ROMANCE)||(category2==ROMANCE))&&(d->next!=NULL)){
		d=d->next;
		if(d->info.year>=newyear){
			if(sugg_head==NULL){
				temp_sugg=malloc(sizeof(struct suggested_movie));
				sugg_head=temp_sugg;
				sugg_head->next=NULL;
				sugg_head->prev=NULL;
				sugg_head->info.mid=d->info.mid;
				sugg_head->info.year=d->info.year;
				sugg_tail=sugg_head;
			}else{
				find=sugg_head;
				while((find->info.mid<d->info.mid)&&(find->next!=NULL)){
					find=find->next;
				}
				temp_sugg=malloc(sizeof(struct suggested_movie));
				if(find->next==NULL){
					sugg_tail->next=temp_sugg;
					temp_sugg->prev=sugg_tail;
					temp_sugg->next=NULL;
					temp_sugg->info.mid=d->info.mid;
					temp_sugg->info.year=d->info.year;
					sugg_tail=temp_sugg;
				}else{
					if(find->prev==NULL){
						find->prev=temp_sugg;
						temp_sugg->next=find;
						sugg_head=temp_sugg;
					}else{
						t=find->prev;
						t->next=temp_sugg;
						temp_sugg->prev=t;
						temp_sugg->next=find;
						find->prev=temp_sugg;
					}
					temp_sugg->info.mid=d->info.mid;
					temp_sugg->info.year=d->info.year;
				}
			}
		}
	}
	while(((category1==DOCUMENTARY)||(category2==DOCUMENTARY))&&(e->next!=NULL)){
		e=e->next;
		if(e->info.year>=newyear){
			if(sugg_head==NULL){
				temp_sugg=malloc(sizeof(struct suggested_movie));
				sugg_head=temp_sugg;
				sugg_head->next=NULL;
				sugg_head->prev=NULL;
				sugg_head->info.mid=e->info.mid;
				sugg_head->info.year=e->info.year;
				sugg_tail=sugg_head;
			}else{
				find=sugg_head;
				while((find->info.mid<e->info.mid)&&(find->next!=NULL)){
					find=find->next;
				}
				temp_sugg=malloc(sizeof(struct suggested_movie));
				if(find->next==NULL){
					sugg_tail->next=temp_sugg;
					temp_sugg->prev=sugg_tail;
					temp_sugg->next=NULL;
					temp_sugg->info.mid=e->info.mid;
					temp_sugg->info.year=e->info.year;
					sugg_tail=temp_sugg;
				}else{
					if(find->prev==NULL){
						find->prev=temp_sugg;
						temp_sugg->next=find;
						sugg_head=temp_sugg;
					}else{
						t=find->prev;
						t->next=temp_sugg;
						temp_sugg->prev=t;
						temp_sugg->next=find;
						find->prev=temp_sugg;
					}
					temp_sugg->info.mid=e->info.mid;
					temp_sugg->info.year=e->info.year;
				}
			}
		}
	}
	while(((category1==COMEDY)||(category2==COMEDY))&&(f->next!=NULL)){
		f=f->next;
		if(f->info.year>=newyear){
			if(sugg_head==NULL){
				temp_sugg=malloc(sizeof(struct suggested_movie));
				sugg_head=temp_sugg;
				sugg_head->next=NULL;
				sugg_head->prev=NULL;
				sugg_head->info.mid=f->info.mid;
				sugg_head->info.year=f->info.year;
				sugg_tail=sugg_head;
			}else{
				find=sugg_head;
				while((find->info.mid<f->info.mid)&&(find->next!=NULL)){
					find=find->next;
				}
				temp_sugg=malloc(sizeof(struct suggested_movie));
				if(find->next==NULL){
					sugg_tail->next=temp_sugg;
					temp_sugg->prev=sugg_tail;
					temp_sugg->next=NULL;
					temp_sugg->info.mid=f->info.mid;
					temp_sugg->info.year=f->info.year;
					sugg_tail=temp_sugg;
				}else{
					if(find->prev==NULL){
						find->prev=temp_sugg;
						temp_sugg->next=find;
						sugg_head=temp_sugg;
					}else{
						t=find->prev;
						t->next=temp_sugg;
						temp_sugg->prev=t;
						temp_sugg->next=find;
						find->prev=temp_sugg;
					}
					temp_sugg->info.mid=f->info.mid;
					temp_sugg->info.year=f->info.year;
				}
			}
		}
	}
	if(this_user->suggestedHead==NULL){
		this_user->suggestedHead=sugg_head;
		this_user->suggestedTail=sugg_tail;
		this_user->suggestedHead->prev=this_user->suggestedTail;
		this_user->suggestedTail->next=this_user->suggestedHead;
	}else{
		this_user->suggestedTail->next=sugg_head;
		sugg_head->prev=this_user->suggestedTail;
		sugg_tail->next=this_user->suggestedHead;
		this_user->suggestedHead->prev=sugg_tail;
	}
	printf("F <%d> <%d> <%d> <%d>\n  User <%d> Suggested Movies = ",newuid,category1,category2,newyear,newuid);
	temp_sugg=this_user->suggestedHead;
	while(temp_sugg->next!=this_user->suggestedHead){
		printf("<%d>, ",temp_sugg->info.mid);
		temp_sugg=temp_sugg->next;
	}
	printf("<%d>\nDONE\n",temp_sugg->info.mid);
	return 0;
}

void take_off_movie(unsigned newmid){
	struct user *u=head_user;
	struct suggested_movie *temp_sugg;
	struct suggested_movie *temp_suggprev;
	struct suggested_movie *temp_suggnext;

	struct movie *a=hor;
	struct movie *b=sci;
	struct movie *c=dra;
	struct movie *d=rom;
	struct movie *e=doc;
	struct movie *f=com;
	struct movie *temp_movie;
	int flag=0;
	printf("T <%d>\n",newmid);
	while(u->next!=NULL){
		u=u->next;
		if((u->suggestedHead!=NULL)&&(u->suggestedHead!=u->suggestedTail)){
			temp_sugg=u->suggestedHead;
			while((temp_sugg->info.mid!=newmid)&&(temp_sugg!=u->suggestedHead)){
				temp_sugg=temp_sugg->next;
			}
			if(temp_sugg->info.mid==newmid){
				printf("<%d> removed from <%d> suggested list.\n",newmid,u->uid);
				temp_suggprev=temp_sugg->prev;
				temp_suggnext=temp_sugg->next;
				temp_suggprev->next=temp_suggnext;
				temp_suggnext->prev=temp_suggprev;
			}
		}else if((u->suggestedHead!=NULL)&&(u->suggestedHead==u->suggestedTail)){
			temp_sugg=u->suggestedHead;
			if(temp_sugg->info.mid==newmid){
				printf("<%d> removed from <%d> suggested list.\n",newmid,u->uid);
				u->suggestedHead=NULL;
				u->suggestedTail=NULL;
			}
		}
	}
	while(a->next!=NULL){
		temp_movie=a;
		a=a->next;
		if(a->info.mid==newmid){
			flag=1;
			printf("<%d> removed from HORROR category list.\n",newmid);
			temp_movie->next=a->next;
		}
	}
	if(flag==1){
		flag=2;
		a=hor;
		printf("Category list = ");
		while(a->next!=NULL){
			a=a->next;
			printf("<%d>",a->info.mid);
			if(a->next!=NULL){
				printf(", ");
			}
		}
	}
	while((b->next!=NULL)&&(flag==0)){
		temp_movie=b;
		b=b->next;
		if(b->info.mid==newmid){
			flag=1;
			printf("<%d> removed from SCIFI category list.\n",newmid);
			temp_movie->next=b->next;
		}
	}
	if(flag==1){
		flag=2;
		b=sci;
		printf("Category list = ");
		while(b->next!=NULL){
			b=b->next;
			printf("<%d>",b->info.mid);
			if(b->next!=NULL){
				printf(", ");
			}
		}
	}
	while((c->next!=NULL)&&(flag==0)){
		temp_movie=c;
		c=c->next;
		if(c->info.mid==newmid){
			flag=1;
			printf("<%d> removed from DRAMA category list.\n",newmid);
			temp_movie->next=c->next;
		}
	}
	if(flag==1){
		flag=2;
		c=dra;
		printf("Category list = ");
		while(c->next!=NULL){
			c=c->next;
			printf("<%d>",c->info.mid);
			if(c->next!=NULL){
				printf(", ");
			}
		}
	}
	while((d->next!=NULL)&&(flag==0)){
		temp_movie=d;
		d=d->next;
		if(d->info.mid==newmid){
			flag=1;
			printf("<%d> removed from ROMANCE category list.\n",newmid);
			temp_movie->next=d->next;
		}
	}
	if(flag==1){
		flag=2;
		d=rom;
		printf("Category list = ");
		while(d->next!=NULL){
			d=d->next;
			printf("<%d>",d->info.mid);
			if(d->next!=NULL){
				printf(", ");
			}
		}
	}
	while((e->next!=NULL)&&(flag==0)){
		temp_movie=e;
		e=e->next;
		if(e->info.mid==newmid){
			flag=1;
			printf("<%d> removed from DOCUMENTARY category list.\n",newmid);
			temp_movie->next=e->next;
		}
	}
	if(flag==1){
		flag=2;
		e=doc;
		printf("Category list = ");
		while(e->next!=NULL){
			e=e->next;
			printf("<%d>",e->info.mid);
			if(e->next!=NULL){
				printf(", ");
			}
		}
	}
	while((f->next!=NULL)&&(flag==0)){
		temp_movie=f;
		f=f->next;
		if(f->info.mid==newmid){
			flag=1;
			printf("<%d> removed from COMEDY category list.\n",newmid);
			temp_movie->next=f->next;
		}
	}
	if(flag==1){
		flag=2;
		f=com;
		printf("Category list = ");
		while(f->next!=NULL){
			f=f->next;
			printf("<%d>",f->info.mid);
			if(f->next!=NULL){
				printf(", ");
			}
		}
	}
	printf("\nDONE\n");
}

void print_movies(void){
	struct movie *a=hor;
	struct movie *b=sci;
	struct movie *c=dra;
	struct movie *d=rom;
	struct movie *e=doc;
	struct movie *f=com;
	int m=0;
	printf("M\nCategorized Movies:\n  Horror: ");
	while(a->next!=NULL){
		a=a->next;
		m++;
		printf("<%d,%d>",a->info.mid,m);
		if(a->next!=NULL){
				printf(", ");
		}
	}
	printf("\n  Sci-fi: ");
	m=0;
	while(b->next!=NULL){
		b=b->next;
		m++;
		printf("<%d,%d>",b->info.mid,m);
		if(b->next!=NULL){
				printf(", ");
		}
	}
	printf("\n  Drama: ");
	m=0;
	while(c->next!=NULL){
		c=c->next;
		m++;
		printf("<%d,%d>",c->info.mid,m);
		if(c->next!=NULL){
				printf(", ");
		}
	}
	printf("\n  Romance: ");
	m=0;
	while(d->next!=NULL){
		d=d->next;
		m++;
		printf("<%d,%d>",d->info.mid,m);
		if(d->next!=NULL){
				printf(", ");
		}
	}
	printf("\n  Documentary: ");
	m=0;
	while(e->next!=NULL){
		e=e->next;
		m++;
		printf("<%d,%d>",e->info.mid,m);
		if(e->next!=NULL){
				printf(", ");
		}
	}
	printf("\n  Comedy: ");
	m=0;
	while(f->next!=NULL){
		f=f->next;
		m++;
		printf("<%d,%d>",f->info.mid,m);
		if(f->next!=NULL){
				printf(", ");
		}
	}
	printf("\nDONE\n");
}

void print_users(void){
	struct user *u=head_user;
	struct suggested_movie *temp_sugg;
	struct movie *temp_movie;
	int m,n;
	printf("P\nUsers:\n");
	while(u->next!=NULL){
		u=u->next;
		m=0;
		n=0;
		printf("  <%d>:\n    Suggested: ",u->uid);
		if(u->suggestedHead!=NULL){
			temp_sugg=u->suggestedHead;
			while(temp_sugg->next!=u->suggestedHead){
				m++;
				if(m!=1){
					printf(", ");
				}
				printf("<%d,%d>",temp_sugg->info.mid,m);
				temp_sugg=temp_sugg->next;
			}
			m++;
			if(m!=1){
				printf(", ");
			}
			printf("<%d,%d>",temp_sugg->info.mid,m);
		}
		printf("\n    Watch History: ");
		if(u->watchHistory!=NULL){
			temp_movie=u->watchHistory;
			while(temp_movie->next!=NULL){
				n++;
				if(n!=1){
					printf(", ");
				}
				printf("<%d,%d>",temp_movie->info.mid,n);
				temp_movie=temp_movie->next;
			}
			n++;
			if(n!=1){
				printf(", ");
			}
			printf("<%d,%d>",temp_movie->info.mid,n);
		}
		printf("\n");
	}
	printf("DONE\n");
}

int main(int argc, char *argv[])
{
	FILE *event_file;
	char line_buffer[MAX_LINE];

	
	if (argc != 2) {
		fprintf(stderr, "Usage: %s <input_file>\n",
				argv[0]);
		exit(EXIT_FAILURE);
	}

	event_file = fopen(argv[1], "r");
	if (!event_file) {
		perror("fopen error for event file open");
		exit(EXIT_FAILURE);
	}

	init_structures();
	while (fgets(line_buffer, MAX_LINE, event_file)) {
		char *trimmed_line;
		char event;
		int uid;
		unsigned mid, year;
		movieCategory_t category1, category2;
		/*
		 * First trim any whitespace
		 * leading the line.
		 */
		trimmed_line = line_buffer;
		while (trimmed_line && isspace(*trimmed_line))
			trimmed_line++;
		if (!trimmed_line)
			continue;
		/* 
		 * Find the event,
		 * or comment starting with #
		 */
		if (sscanf(trimmed_line, "%c", &event) != 1) {
			fprintf(stderr, "Could not parse event type out of input line:\n\t%s",
					trimmed_line);
			fclose(event_file);
			exit(EXIT_FAILURE);
		}

		switch (event) {
			/* Comment, ignore this line */
			case '#':
				break;
			case 'R':
				if (sscanf(trimmed_line, "R %d", &uid) != 1) {
					fprintf(stderr, "Event R parsing error\n");
					break;
				}
				register_user(uid);
				break;
			case 'U':
				if (sscanf(trimmed_line, "U %d", &uid) != 1) {
					fprintf(stderr, "Event U parsing error\n");
					break;
				}
				unregister_user(uid);
				break;
			case 'A':
				if (sscanf(trimmed_line, "A %u %d %u", &mid, &category1,
							&year) != 3) {
					fprintf(stderr, "Event A parsing error\n");
					break;
				}
				add_new_movie(mid, category1, year);
				break;
			case 'D':
				distribute_new_movies();
				break;
			case 'W':
				if (sscanf(trimmed_line, "W %d %u", &uid, &mid) != 2) {
					fprintf(stderr, "Event W parsing error\n");
					break;
				}
				watch_movie(uid, mid);
				break;
			case 'S':
				if (sscanf(trimmed_line, "S %d", &uid) != 1) {
					fprintf(stderr, "Event S parsing error\n");
					break;
				}
				suggest_movies(uid);
				break;
			case 'F':
				if (sscanf(trimmed_line, "F %d %d %d %u", &uid, &category1,
							&category2, &year) != 4) {
					fprintf(stderr, "Event F parsing error\n");
					break;
				}
				filtered_movie_search(uid, category1, category2, year);
				break;
			case 'T':
				if (sscanf(trimmed_line, "T %u", &mid) != 1) {
					fprintf(stderr, "Event T parsing error\n");
					break;
				}
				take_off_movie(mid);
				break;
			case 'M':
				print_movies();
				break;
			case 'P':
				print_users();
				break;
			default:
				fprintf(stderr, "WARNING: Unrecognized event %c. Continuing...\n",
						event);
				break;
		}
	}
	fclose(event_file);
	destroy_structures();
	return 0;
}