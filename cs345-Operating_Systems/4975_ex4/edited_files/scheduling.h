/*linux-2.6.38.1>kernel>scheduling.h
 *Papadakis Georgios csd4975*/

#ifndef _LINUX_SCHEDULING_H
#define _LINUX_SCHEDULING_H

#include <linux/sched.h> 
#include <linux/kernel.h>
#include <linux/linkage.h>
#include <asm/current.h> 
#include <asm-generic/errno-base.h>
#include <linux/uaccess.h> 
#include <linux/time.h>

struct d_params{
    long d1;
    long d2;
    long ct;
};

asmlinkage long sys_set_scheduling_params(long deadline_1, long deadline_2, long computation_time);
asmlinkage long sys_get_scheduling_params(struct d_params __user *params);
asmlinkage long sys_get_scheduling_score(void);

#endif /* _LINUX_SCHEDULING_H */