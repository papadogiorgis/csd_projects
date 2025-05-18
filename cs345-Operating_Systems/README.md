# CS345 Operating Systems

## Assignment 1: Linux Shell
I implement a C shell and learn how it works by creating and managing processes, implementing pipes, and handling input/output redirection.

## Assignment 2: Process/Thread Synchronization
I implement a C program using threads and semaphores to simulate students from four departments taking a bus to and from the university, with boarding limits per department and FIFO order.

## Assignment 3: System Call Implementation
I implement a custom scheduling policy in the Linux kernel by adding three system calls, recompile the kernel, and test it in QEMU with a user-level demo program.

## Assignment 4: Scheduling Policy Implementation
I implement a preemptive Linux kernel scheduler called Highest Value First, which runs the process with the highest value based on its execution time and deadline window [D1, D2]. The scheduler preempts lower-value processes and terminates each process after its declared execution time.