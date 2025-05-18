/*PAPADAKIS GEORGIOS
 *CSD4975
 *ASSIGNMENT 2    */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <semaphore.h>
#include <time.h>
#include <unistd.h>

pthread_mutex_t printing_mutex;

char* printDep(int n){
    switch (n)
    {
    case 0:
        return "Math";
    case 1:
        return "Physics";
    case 2:
        return "Chemistry";
    case 3:
        return "CSD";
    default:
        return "VOSKOS!!";
    }
}

sem_t f_studying;

struct foititis{
    int AM;
    int dep;
    int study_time;
    pthread_t thr;
    int location;
    struct Args* bargs;
    int diavasmenos;
};

struct stopA{
    struct foititis* f;
    struct stopA* next;
};

void enq_sa(struct stopA* sa,struct foititis* foit){
    struct stopA* temp= sa;
    while(temp->next!=NULL){
        temp=temp->next;
    }
    if(temp->f==NULL){
        temp->f=foit;
    }else{
        struct stopA* new= (struct stopA*)malloc(sizeof(struct stopA));
        temp->next=new;
        new->next=NULL;
        new->f=foit;
    }
    foit->location=0;
}

struct stopA* deq_sa(struct stopA* sa, struct foititis* foit){
    struct stopA* temp_sa=sa;
    struct stopA* prev=sa;
    int count=0;
    while(temp_sa->next!=NULL){
        if(temp_sa->f->AM==foit->AM){
            break;
        }
        count++;
        prev=temp_sa;
        temp_sa=temp_sa->next;
    }
    if(count==0){
        if(temp_sa->next==NULL){
            sa->f=NULL;
        }else{
            prev=sa;
            sa=sa->next;
            prev->f=NULL;
            free(prev);
        }
    }else{
        if(temp_sa->next==NULL){
            prev->next=NULL;
            temp_sa->f=NULL;
            free(temp_sa);
        }else{
            prev->next=temp_sa->next;
            temp_sa->f=NULL;
            free(temp_sa);
        }
    }
    return sa;
}

struct Bus{
    struct foititis* f;
    struct Bus* next;
};

void enq_b(struct Bus* b,struct foititis* foit){
    struct Bus* temp= b;
    while(temp->next!=NULL){
        temp=temp->next;
    }
    if(temp->f==NULL){
        temp->f=foit;
    }else{
        struct Bus* new= (struct Bus*)malloc(sizeof(struct Bus));
        temp->next=new;
        new->next=NULL;
        new->f=foit;
    }
    foit->location=1;
}

struct Bus* deq_b(struct Bus* b, struct foititis* foit){
    struct Bus* temp_b=b;
    struct Bus* prev=b;
    int count=0;
    while(temp_b->next!=NULL){
        if(temp_b->f->AM==foit->AM){
            break;
        }
        count++;
        prev=temp_b;
        temp_b=temp_b->next;
    }
    if(count==0){
        if(temp_b->next==NULL){
            b->f=NULL;
        }else{
            prev=b;
            b=b->next;
            prev->f=NULL;
            free(prev);
        }
    }else{
        if(temp_b->next==NULL){
            prev->next=NULL;
            temp_b->f=NULL;
            free(temp_b);
        }else{
            prev->next=temp_b->next;
            temp_b->f=NULL;
            free(temp_b);
        }
    }
    return b;
}

struct University{
    struct foititis* f;
    struct University* next;
};

void enq_u(struct University* u,struct foititis* foit){
    struct University* temp= u;
    while(temp->next!=NULL){
        temp=temp->next;
    }
    if(temp->f==NULL){
        temp->f=foit;
    }else{
        struct University* new= (struct University*)malloc(sizeof(struct University));
        temp->next=new;
        new->next=NULL;
        new->f=foit;
    }
    foit->location=2;
}

struct University* deq_u(struct University* u, struct foititis* foit){
    struct University* temp_u=u;
    struct University* prev=u;
    int count=0;
    while(temp_u->next!=NULL){
        if(temp_u->f->AM==foit->AM){
            break;
        }
        count++;
        prev=temp_u;
        temp_u=temp_u->next;
    }
    if(count==0){
        if(temp_u->next==NULL){
            u->f=NULL;
        }else{
            prev=u;
            u=u->next;
            prev->f=NULL;
            free(prev);
        }
    }else{
        if(temp_u->next==NULL){
            prev->next=NULL;
            temp_u->f=NULL;
            free(temp_u);
        }else{
            prev->next=temp_u->next;
            temp_u->f=NULL;
            free(temp_u);
        }
    }
    return u;
}

struct stopB{
    struct foititis* f;
    struct stopB* next;
};

void enq_sb(struct stopB* sb,struct foititis* foit){
    struct stopB* temp= sb;
    while(temp->next!=NULL){
        temp=temp->next;
    }
    if(temp->f==NULL){
        temp->f=foit;
    }else{
        struct stopB* new= (struct stopB*)malloc(sizeof(struct stopB));
        temp->next=new;
        new->next=NULL;
        new->f=foit;
    }
    foit->location=3;
}

struct stopB* deq_sb(struct stopB* sb, struct foititis* foit){
    struct stopB* temp_sb=sb;
    struct stopB* prev=sb;
    int count=0;
    while(temp_sb->next!=NULL){
        if(temp_sb->f->AM==foit->AM){
            break;
        }
        count++;
        prev=temp_sb;
        temp_sb=temp_sb->next;
    }
    if(count==0){
        if(temp_sb->next==NULL){
            sb->f=NULL;
        }else{
            prev=sb;
            sb=sb->next;
            prev->f=NULL;
            free(prev);
        }
    }else{
        if(temp_sb->next==NULL){
            prev->next=NULL;
            temp_sb->f=NULL;
            free(temp_sb);
        }else{
            prev->next=temp_sb->next;
            temp_sb->f=NULL;
            free(temp_sb);
        }
    }
    return sb;
}

struct Args{
    struct stopA* g_sa;
    struct Bus* g_b;
    struct University* g_u;
    struct stopB* g_sb;
    struct foititis* g_f;
    int NoSeats_dep;
    int NoSeats;
};

void init_structs(struct stopA* sa,struct Bus* b,struct University* u,struct stopB* sb){
    sa->f=NULL;
    sa->next=NULL;

    b->f=NULL;
    b->next=NULL;

    u->f=NULL;
    u->next=NULL;

    sb->f=NULL;
    sb->next=NULL;
}

int createAM(struct stopA* q, int N){
    int temp_am, flag;
    struct stopA* temp = q;
    flag=0;
    temp_am= rand() % (1000*N+1);
    if(temp->f!=NULL){
        do{
            while(temp->next!=NULL){
                if(temp->f->AM==temp_am){
                    flag=1;
                }
                temp= temp->next;
            }
            if(temp->f->AM==temp_am){
                flag=1;
            }
        }while(flag==1);
    }
    return temp_am;
}

void print_all_queues(struct Args* bargs, int type_of_message, int arg1, int arg2, int opt_arg){
    struct stopA* tsa=bargs->g_sa;
    struct Bus* tb=bargs->g_b;
    struct University* tu=bargs->g_u;
    struct stopB* tsb=bargs->g_sb;

    pthread_mutex_lock(&printing_mutex);

    switch (type_of_message)
    {
    case 0:
        printf("\nStudent %d (%s) created.\n",arg1,printDep(arg2));
        break;
    case 1:
        printf("\nStudent %d (%s) boarded to the bus.\n",arg1,printDep(arg2));
        break;
    case (-1):
        printf("\nStudent %d (%s) cannot enter the Bus\n",arg1,printDep(arg2));
        break;
    case 2:
        printf("\nBus arrived at University!\n");
        fflush(stdout);
        break;
    case 3:
        printf("\nStudent %d (%s) got off the bus.\n",arg1,printDep(arg2));
        break;
    case (-2):
        printf("\nBus is on the way to University...!");
        fflush(stdout);
        break;
    case 4:
        printf("\nStudent %d (%s) went to University.\n",arg1,printDep(arg2));
        break;
    case 5:
        printf("\nStudent %d (%s) studied for %d seconds, and now is heading to stop B.\n",arg1,printDep(arg2),opt_arg);
        break;
    case (-3):
        printf("\nBus is heading to Stop A\n");
        fflush(stdout);
        break;
    case (-4):
        printf("\nBus arrived to Stop A\n");
        fflush(stdout);
        break;
    case 6:
        printf("\nStudent %d (%s) went home.\n",arg1,printDep(arg2));
        break;
    default:
        break;
    }

    if(type_of_message>=0){
        printf("\nStop A: ");
        while(tsa->next!=NULL){
            printf("[%d, %s] ",tsa->f->AM, printDep(tsa->f->dep));
            tsa=tsa->next;
        }
        if((tsa->f!=NULL)&&(tsa->next==NULL)){
            printf("[%d, %s] ",tsa->f->AM, printDep(tsa->f->dep));
        }

        printf("\nBus: ");
        while(tb->next!=NULL){
            printf("[%d, %s] ",tb->f->AM, printDep(tb->f->dep));
            tb=tb->next;
        }
        if((tb->f!=NULL)&&(tb->next==NULL)){
            printf("[%d, %s] ",tb->f->AM, printDep(tb->f->dep));
        }

        printf("\nUniversity: ");
        while(tu->next!=NULL){
            printf("[%d, %s] ",tu->f->AM, printDep(tu->f->dep));
            tu=tu->next;
        }
        if((tu->f!=NULL)&&(tu->next==NULL)){
            printf("[%d, %s] ",tu->f->AM, printDep(tu->f->dep));
        }

        printf("\nStopB: ");
        while(tsb->next!=NULL){
            printf("[%d, %s] ",tsb->f->AM, printDep(tsb->f->dep));
            tsb=tsb->next;
        }
        if((tsb->f!=NULL)&&(tsb->next==NULL)){
            printf("[%d, %s] ",tsb->f->AM, printDep(tsb->f->dep));
        }
        printf("\n");
    }
    
    pthread_mutex_unlock(&printing_mutex);
    return;
}

void init_student(struct stopA* q, int N, struct Args* bargs){
    int am;
    am = createAM(q,N);
    struct foititis* newfoit= (struct foititis*)malloc(sizeof(struct foititis));
    newfoit->diavasmenos=0;
    newfoit->AM=am;
    newfoit->dep= (rand()%4);
    newfoit->study_time= (rand()%9)+7;
    newfoit->location=0;
    newfoit->bargs=bargs;
    enq_sa(q,newfoit);
    print_all_queues(bargs,0,newfoit->AM,newfoit->dep,-1);
}

void * foititozoi(void* args){
    sem_wait(&f_studying);

    struct foititis* foit=(struct foititis*)args;
    struct Args* bargs= foit->bargs;
    sleep(foit->study_time);
    foit->bargs->g_u=deq_u(foit->bargs->g_u,foit);
    enq_sb(foit->bargs->g_sb,foit);
    print_all_queues(bargs,5,foit->AM,foit->dep,foit->study_time);
    foit->diavasmenos=1;

    sem_post(&f_studying);
    return NULL;
}

void * go_to_uni(void* args){
    struct Args* bargs= (struct Args*)args;
    struct foititis* foit;
    struct stopB* sb=bargs->g_sb;
    struct University* u= bargs->g_u;
    int f_count=1;

    while(sb->next!=NULL){
        foit=sb->f;
        if(foit->diavasmenos==0){
            f_count++;
            bargs->g_sb=deq_sb(sb,foit);
            enq_u(u,foit);
            print_all_queues(bargs,4,foit->AM,foit->dep,-1);
            if(bargs->g_sb!=NULL){
                sb=bargs->g_sb;
            }
        }else{
            sb=sb->next;
        }
    }
    if((sb->f!=NULL)&&(sb->next==NULL)){
        foit=sb->f;
        if(foit->diavasmenos==0){
            bargs->g_sb=deq_sb(sb,foit);
            enq_u(u,foit);
            print_all_queues(bargs,4,foit->AM,foit->dep,-1);
        }
    }

    sem_init(&f_studying, 0, f_count);

    sb=bargs->g_sb;
    while(u->next!=NULL){
        foit=u->f;
        if(foit->diavasmenos==0){
            if(pthread_create(&(foit->thr), NULL, foititozoi,foit)!=0){
                perror("FAILED TO CREATE THREAD!\n");
                return NULL;
            }
            pthread_detach(foit->thr);
        }
        u=u->next;
    }
    if(u->f!=NULL){
        foit=u->f;
        if(foit->diavasmenos==0){
            if(pthread_create(&(foit->thr), NULL, foititozoi,foit)!=0){
                perror("FAILED TO CREATE THREAD!\n");
                return NULL;
            }
            pthread_detach(foit->thr);
        }
    }

    sem_destroy(&f_studying);

    return NULL;
}

void * bus_trip(void* args){
    struct Args* bargs=(struct Args*)args;
    int seats=0;
    int seats_dep[4]={0,0,0,0};
    struct stopA* sa;
    struct stopB* sb;
    struct foititis* foit;
    struct Bus* b;
    pthread_t diavasma;
    while((bargs->g_sa->f!=NULL)||(bargs->g_b->f!=NULL)||(bargs->g_u->f!=NULL)||(bargs->g_sb->f!=NULL)){
        if(bargs->g_sa->f!=NULL){
            sa=bargs->g_sa;
            b=bargs->g_b;
            while(((seats+1)<=bargs->NoSeats)&&(sa->next!=NULL)){
                if(seats_dep[sa->f->dep]+1<=bargs->NoSeats_dep){
                    foit=sa->f;
                    bargs->g_sa=deq_sa(bargs->g_sa,foit);
                    enq_b(bargs->g_b,foit);
                    seats_dep[foit->dep]++;
                    seats++;
                    print_all_queues(bargs,1,foit->AM,foit->dep,-1);
                    if(bargs->g_sa!=NULL){
                        sa=bargs->g_sa;
                    }
                }else{
                    print_all_queues(bargs,-1,sa->f->AM,sa->f->dep,-1);
                    if(sa->next!=NULL){
                        sa=sa->next;
                    }
                }
            }
            if((sa->f!=NULL)&&(sa->next==NULL)){
                if((seats_dep[sa->f->dep]+1<=bargs->NoSeats_dep)&&((seats+1)<=bargs->NoSeats)){
                    foit=sa->f;
                    bargs->g_sa= deq_sa(bargs->g_sa,foit);
                    enq_b(bargs->g_b,foit);
                    seats_dep[foit->dep]++;
                    seats++;
                    print_all_queues(bargs,1,foit->AM,foit->dep,-1);
                }else{
                    print_all_queues(bargs,-1,sa->f->AM,sa->f->dep,-1);
                }
            }
        }
        
        print_all_queues(bargs,-2,-1,-1,-1);
        sleep(10);
        print_all_queues(bargs,2,-1,-1,-1);

        sb=bargs->g_sb;
        b=bargs->g_b;
        while(((seats-1)>=0)&&(b->next!=NULL)){
            foit=b->f;
            bargs->g_b=deq_b(bargs->g_b,foit);
            enq_sb(bargs->g_sb,foit);
            seats_dep[foit->dep]--;
            seats--;
            print_all_queues(bargs,3,foit->AM,foit->dep,-1);
            if(bargs->g_b!=NULL){
                b=bargs->g_b;
            }
        }
        if((b->f!=NULL)&&(b->next==NULL)){
            foit=b->f;
            bargs->g_b=deq_b(bargs->g_b,foit);
            enq_sb(bargs->g_sb,foit);
            seats_dep[foit->dep]--;
            seats--;
            print_all_queues(bargs,3,foit->AM,foit->dep,-1);
        }

        b=bargs->g_b;
        while(sb->next!=NULL){
            foit=sb->f;
            if(foit->diavasmenos==1){
                bargs->g_sb=deq_sb(bargs->g_sb,foit);
                enq_b(bargs->g_b,foit);
                seats_dep[foit->dep]++;
                seats++;
                print_all_queues(bargs,1,foit->AM,foit->dep,-1);
                sb=bargs->g_sb;
            }else{
                sb=sb->next;
            }
        }
        if((sb->f!=NULL)&&(sb->next==NULL)){
            foit=sb->f;
            if(foit->diavasmenos==1){
                bargs->g_sb=deq_sb(bargs->g_sb,foit);
                enq_b(bargs->g_b,foit);
                seats_dep[foit->dep]++;
                seats++;
                print_all_queues(bargs,1,foit->AM,foit->dep,-1);
            }
        }

        if(pthread_create(&diavasma, NULL, go_to_uni, bargs)!=0){
            perror("FAILED TO CREATE THREAD!\n");
            return NULL;
        }
        pthread_detach(diavasma);

        print_all_queues(bargs,-3,-1,-1,-1);
        sleep(10);
        print_all_queues(bargs,-4,-1,-1,-1);

        b=bargs->g_b;
        while(((seats-1)>=0)&&(b->next!=NULL)){
            foit=b->f;
            bargs->g_b=deq_b(bargs->g_b,foit);
            seats_dep[foit->dep]--;
            seats--;
            print_all_queues(bargs,6,foit->AM,foit->dep,-1);
            free(foit);
            if(bargs->g_b!=NULL){
                b=bargs->g_b;
            }
        }
        if((b->f!=NULL)&&(b->next==NULL)){
            foit=b->f;
            bargs->g_b=deq_b(bargs->g_b,foit);
            seats_dep[foit->dep]--;
            seats--;
            print_all_queues(bargs,6,foit->AM,foit->dep,-1);
            free(foit);
        }
    }
    return NULL;
}

int main(int argc, char * argv[] ){
    pthread_mutex_init(&printing_mutex,NULL);
    srand(time(NULL));
    int N,i;
    printf("Enter the number of students: ");
    scanf("%d",&N);
    
    struct stopA* sa= (struct stopA*)malloc(sizeof(struct stopA));
    struct Bus* b= (struct Bus*)malloc(sizeof(struct Bus));
    struct University* u= (struct University*)malloc(sizeof(struct University));
    struct stopB* sb= (struct stopB*)malloc(sizeof(struct stopB));
    init_structs(sa,b,u,sb);

    struct Args* bargs= (struct Args*)malloc(sizeof(struct Args));
    if(N<=2){
        bargs->NoSeats=1;
    }else{
        bargs->NoSeats=(int)(0.8*N);
    }
    if(N<=5){
        bargs->NoSeats_dep=1;
    }else{
        bargs->NoSeats_dep=(int)(0.2*N);
    }
    bargs->g_sa=sa;
    bargs->g_b=b;
    bargs->g_u=u;
    bargs->g_sb=sb;

    for(i=0;i<N;i++){
        init_student(sa, N,bargs);
    }

    pthread_t bus_thread;
    if(pthread_create(&bus_thread, NULL, bus_trip, bargs)!=0){
        perror("FAILED TO CREATE THREAD!\n");
        return -1;
    }

    pthread_join(bus_thread, NULL);
    
    return 0;
}