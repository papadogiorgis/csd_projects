


PAPADAKIS GEORGIOS
CSD4975
ASSIGNMENT 2

In this program there are 4 queues for each stop A, Bus, University and stop B.
Each node of these queues has a pointer to a student node(struct foititis),
depending on where this student is.
There is a printing function(print_all_queues) that prints a certain message
first and then the nodes of each queue.
This printing function is locked by a mutex in order to keep the messages
synchronized.

There is also a semaphore(f_studying). This ensures that students
study "by batches".
Each batch is the students that get to stopB and then to uni.
The first batch of students that got into uni must finish their studying
in order to let the second batch of students study and so on.

The function bus_trip is used for controlling the bus. It is a while loop that
recycles every time the bus makes a full trip from stop A to stop B and back
and there are still existing students.

The number of the bus seats is set to be the 80% of the existing students,
because i wanted to make sure that this program runs for very small to very
large number of students.
This number can be manipulated in function main (bargs->NoSeats=...)
The number of seats per department is 1/4 of bus seats, so 20% of all the
students (in main NoSeats_dep).

There is also an Args struct that contains the first node of every queue and
the number of seats, so i can pass these arguments easily to my threads
and functions.

For any questions about my code please contact
csd4975@csd.uoc.gr

Papadakis Georgios


