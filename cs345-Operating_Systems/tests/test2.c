#include "lib_for_tests.h"

void spin_task(int seconds) {
    printf("Process %d spinning for %d seconds...\n", getpid(), seconds);
    time_t start = time(NULL);
    while (time(NULL) - start < seconds) {
        for (volatile int i = 0; i < 1000000; i++); /*Dummy load*/
    }
    printf("Process %d finished spinning.\n", getpid());
}

void process_1() {
    struct d_params params;
    params.d1 = 3; /* // Deadline 1 */
    params.d2 = 7; /* // Deadline 2 */
    params.ct = 5; /* // Computation time (seconds) */

    printf("Process %d setting parameters: D1=%ld, D2=%ld, C=%ld\n", getpid(), params.d1, params.d2, params.ct);
    if (syscall(SYS_SET_PARAMS, params.d1, params.d2, params.ct) < 0) {
        perror("Error in syscall(SYS_SET_PARAMS)");
        exit(1);
    }

    spin_task(3); /* // Spin for 3 seconds (simulate computation) */
}

void process_2() {
    struct d_params params;
    params.d1 = 4; /* // Deadline 1 */
    params.d2 = 9;/*  // Deadline 2 */
    params.ct = 4; /* // Computation time (seconds) */

    printf("Process %d setting parameters: D1=%ld, D2=%ld, C=%ld\n", getpid(), params.d1, params.d2, params.ct);
    if (syscall(SYS_SET_PARAMS, params.d1, params.d2, params.ct) < 0) {
        perror("Error in syscall(SYS_SET_PARAMS)");
        exit(1);
    }

    spin_task(2); /* // Spin for 2 seconds (simulate computation) */
}

int main() {
    pid_t p1, p2;

    printf("=== Example Test for HVF Scheduler ===\n");

    /* // Start Process 1 */
    if ((p1 = fork()) == 0) {
        process_1();
        exit(0);
    }

    /* // Let Process 1 execute for 1 second */
    sleep(1);

    /* // Start Process 2 */
    if ((p2 = fork()) == 0) {
        process_2();
        exit(0);
    }

    /* // Wait for both processes to finish */
    waitpid(p1, NULL, 0);
    waitpid(p2, NULL, 0);

    printf("=== Test Completed ===\n");
    return 0;
}