#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <time.h>
#include <sys/syscall.h>
#include <asm/unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>

#define SYS_SET_PARAMS 341
#define SYS_GET_PARAMS 342
#define SYS_GET_SCORE  343

struct d_params {
    long d1;
    long d2;
    long ct;
};