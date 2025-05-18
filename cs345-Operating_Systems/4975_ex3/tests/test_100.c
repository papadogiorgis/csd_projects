/*Papadakis Georgios csd4975
 *test_100.c
 *after compiling and running test_100 type "dmesg" to see the kernel's messages
 *this test will get a scheduling score of 100*/

#include "lib_for_tests.h"

int main() {
    struct d_params parametroi;
    struct d_params fetched;
    long score;
    time_t cur_time = time(NULL);

    
    parametroi.d1 = cur_time + 5;
    parametroi.d2 = cur_time + 15;
    parametroi.ct = 3000;

    printf("Setting the Deadlines:\n");
    printf("  Deadline 1: %ld sec\n  Deadline 2: %ld sec\n  Computation Time: %ld ms\n", parametroi.d1, parametroi.d2, parametroi.ct);

    if (syscall(SYS_SET_PARAMS, parametroi.d1, parametroi.d2, parametroi.ct) < 0) {
        perror("FAILED TO SET THE PARAMETERS!!!!\n");
        return -1;
    }

    if (syscall(SYS_GET_PARAMS, &fetched) < 0) {
        perror("FAILED TO FETCH THE PARAMETERS!!!!\n");
        return -1;
    }

    printf("Fetched Parameters:\n  Deadline 1: %ld sec\n  Deadline 2: %ld sec\n  Computation Time: %ld ms\n",fetched.d1, fetched.d2, fetched.ct);

    /*IMMEDIATELY GET THE SCHEDULING SCORE, SO IT GETS A VALUE OF 100*/

    score = syscall(SYS_GET_SCORE);
    if (score < 0) {
        perror("FAILED TO GET SCHEDULING SCORE!!!!\n");
        return -1;
    }

    printf("Scheduling Score: %ld\n", score);

    return 0;
}