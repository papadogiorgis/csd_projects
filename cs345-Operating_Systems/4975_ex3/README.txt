Papadakis Georgios csd4975

In the directory "edited_files" there are the .c, .h and Makefile files that
i edited for this assignment. On the top lines of every file there is a
comment that gives the path of this file inside the directory "linux-2.6.38.1"
and the line that i edited. Ive made a new file named "scheduling.c" that is
inside kernel's folder. I didnt make a new file called "d_params", but i wrote
the task_struct inside the already existing "sched.h".

In the directory "tests" there are the test files, a Makefile and a .h file
that is included in every test file. Just type "make all" while inside the
"tests" directory and the executables will be made. "test_0" scores 0 on the
scheduling score, because it sleeps for 11 secs while the deadline is 10.
"test_50" scores 50 and "test_100" scores 100. It is absolutely necessary to
type "dmesg" after running the executables of the tests in order to see
kernel's messages.

STEPS FOR COMPLETING THIS ASSIGNMENT;
1)add system call numbers on unistd_32.h for each function
2)add system call numbers on syscall_table_32.S for each function
3)create the task_struct on linux-2.6.38.1/include/linux/sched.h with
long ints for every deadline_1, deadline_2 and computation_time
4)create scheduling.c inside linux-2.6.38.1/kernel that has the source code
for the new system calls
5)edit the kernel's Makefile adding the new scheduling.o file
6)compile linux, get the new bzImage and run QEMU
7)load the test files on QEMU using scp commands and run them
8)type "dmesg" to get kernel's messages
9)load every edited file, test and this readme on "4975_ex3" directory
10)write this readme and submit "4975_ex3" directory