/*Papadakis Georgios csd4975
 *test.c
 *after compiling and running test_0 type "dmesg" to see the kernel's messages*/

#include "lib_for_tests.h"

void spin_task(int seconds) {
    int i;
    printf("Process %d spinning for %d seconds...\n", getpid(), seconds);
    time_t start = time(NULL);
    while (time(NULL) - start < seconds) {
        for (i = 0; i < 1000000; i++);
    }
    printf("Process %d finished spinning.\n", getpid());
}

void single_process_test() {
    struct d_params parametroi;
    struct d_params fetched;

    printf("\n=== Single Process Test ===\n");

    parametroi.d1 = 5;
    parametroi.d2 = 10;
    parametroi.ct = 4000;

    printf("Setting scheduling parameters...\n");
    if (syscall(SYS_SET_PARAMS, parametroi.d1, parametroi.d2, parametroi.ct) < 0) {
        perror("Error in set_scheduling_params");
        return;
    }

    printf("Getting scheduling parameters...\n");
    if (syscall(SYS_GET_PARAMS, &fetched) < 0) {
        perror("Error in get_scheduling_params");
        return;
    }

    printf("Parameters fetched:\n");
    printf("Deadline 1: %ld seconds\n", fetched.d1);
    printf("Deadline 2: %ld seconds\n", fetched.d2);
    printf("Computation time: %ld milliseconds\n", fetched.ct);

    printf("Getting scheduling score...\n");
    long score = syscall(SYS_GET_SCORE);
    if (score < 0) {
        perror("Error in get_scheduling_score");
        return;
    }
    printf("Scheduling score: %ld\n", score);

    spin_task(5);
}

/*void multiple_processes_test() {
    pid_t pids[3];
    int i;
    long deadlines[][3] = {
        {2, 5, 2000},
        {3, 7, 3000},
        {4, 9, 1000}
    };

    for (i = 0; i < 3; i++) {
        if ((pids[i] = fork()) == 0) {
            printf("Process %d setting scheduling params (D1=%ld, D2=%ld, C=%ld)...\n",
                   getpid(), deadlines[i][0], deadlines[i][1], deadlines[i][2]);
            if (syscall(SYS_SET_PARAMS, deadlines[i][0], deadlines[i][1], deadlines[i][2]) < 0) {
                perror("Error in set_scheduling_params");
                exit(1);
            }
            spin_task(6);
            exit(0);
        }
    }

    for (i = 0; i < 3; i++) {
        waitpid(pids[i], NULL, 0);
    }
}*/

int main() {
    single_process_test();
    /*multiple_processes_test();*/
    return 0;
}