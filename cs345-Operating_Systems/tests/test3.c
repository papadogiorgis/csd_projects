#include "lib_for_tests.h"

void spin_task(int seconds) {
    printf("Process %d spinning for %d seconds...\n", getpid(), seconds);
    time_t start = time(NULL);
    while (time(NULL) - start < seconds) {
        volatile int i;
        for (i = 0; i < 1000000; i++); /* Dummy load */

        /* Fetch and print score periodically */
        if ((time(NULL) - start) % 1 == 0) {
            long score = syscall(SYS_GET_SCORE);
            if (score < 0) {
                perror("Error in syscall(SYS_GET_SCORE)");
            } else {
                printf("Process %d: Score=%ld\n", getpid(), score);
            }
        }
    }
    printf("Process %d finished spinning.\n", getpid());
}

void process_1() {
    /* Calculate deadlines relative to current time */
    unsigned long now = time(NULL); /* Current time in seconds */
    struct d_params params = {
        .d1 = now + 3, /* Deadline 1 is current time + 3 seconds */
        .d2 = now + 7, /* Deadline 2 is current time + 7 seconds */
        .ct = 5000     /* Computation time is 5000 milliseconds */
    };

    printf("Process %d setting parameters: D1=%ld, D2=%ld, C=%ld\n", getpid(), params.d1, params.d2, params.ct);
    if (syscall(SYS_SET_PARAMS, params.d1, params.d2, params.ct) < 0) {
        perror("Error in syscall(SYS_SET_PARAMS)");
        exit(1);
    }

    /* Fetch and verify parameters */
    struct d_params fetched;
    if (syscall(SYS_GET_PARAMS, &fetched) < 0) {
        perror("Error in syscall(SYS_GET_PARAMS)");
        exit(1);
    }
    printf("Process %d fetched parameters: D1=%ld, D2=%ld, C=%ld\n", getpid(), fetched.d1, fetched.d2, fetched.ct);

    spin_task(3);
}

void process_2() {
    /* Calculate deadlines relative to current time */
    unsigned long now = time(NULL); /* Current time in seconds */
    struct d_params params = {
        .d1 = now + 4, /* Deadline 1 is current time + 4 seconds */
        .d2 = now + 9, /* Deadline 2 is current time + 9 seconds */
        .ct = 4000     /* Computation time is 4000 milliseconds */
    };

    printf("Process %d setting parameters: D1=%ld, D2=%ld, C=%ld\n", getpid(), params.d1, params.d2, params.ct);
    if (syscall(SYS_SET_PARAMS, params.d1, params.d2, params.ct) < 0) {
        perror("Error in syscall(SYS_SET_PARAMS)");
        exit(1);
    }

    /* Fetch and verify parameters */
    struct d_params fetched;
    if (syscall(SYS_GET_PARAMS, &fetched) < 0) {
        perror("Error in syscall(SYS_GET_PARAMS)");
        exit(1);
    }
    printf("Process %d fetched parameters: D1=%ld, D2=%ld, C=%ld\n", getpid(), fetched.d1, fetched.d2, fetched.ct);

    spin_task(2);
}

int main() {
    pid_t p1, p2;

    printf("=== Example Test for HVF Scheduler ===\n");

    /* Start Process 1 */
    if ((p1 = fork()) == 0) {
        process_1();
        exit(0);
    }

    sleep(1); /* Let Process 1 run for 1 second */

    /* Start Process 2 */
    if ((p2 = fork()) == 0) {
        process_2();
        exit(0);
    }

    /* Wait for both processes to finish */
    waitpid(p1, NULL, 0);
    waitpid(p2, NULL, 0);

    printf("=== Test Completed ===\n");
    return 0;
}