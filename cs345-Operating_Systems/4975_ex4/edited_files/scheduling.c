/*linux-2.6.38.1>kernel>scheduling.c
 *Papadakis Georgios csd4975*/

#include "scheduling.h"


asmlinkage long sys_set_scheduling_params(long deadline_1, long deadline_2, long computation_time){
    printk(KERN_INFO "SYS_SET CALLED! PAPADAKIS GEORGIOS 4975!! PROCESS: %d\n", current->pid);
    if((deadline_1<=0)||(deadline_2 <= deadline_1)||(computation_time<=0)||(computation_time>(deadline_2-deadline_1)*1000)){
        printk(KERN_ERR "INVALID PARAMETERS FOR SETTER!!\n");
        return -EINVAL;
    }

    current->deadline_1 = deadline_1;
    current->deadline_2 = deadline_2;
    current->computation_time = computation_time;
    printk(KERN_INFO "PARAMETERS FOR SYS_SET:\nDEADLINE_1 = %ld sec.\nDEADLINE_2 = %ld sec.\nCOMPUTATION TIME: %ld ms\n", deadline_1, deadline_2, computation_time);
    return 0;
}

asmlinkage long sys_get_scheduling_params(struct d_params __user *params){
    struct d_params kernel_params;
    printk(KERN_INFO "SYS_GET CALLED! PAPADAKIS GEORGIOS 4975!! PROCESS: %d\n", current->pid);
    if(!params){
        printk(KERN_ERR "INVALID POINTER IN SYS_GET\n");
        return -EINVAL;
    }

    kernel_params.d1 = current->deadline_1;
    kernel_params.d2 = current->deadline_2;
    kernel_params.ct = current->computation_time;

    if(copy_to_user(params, &kernel_params, sizeof(kernel_params))){
        printk(KERN_ERR "FAILED TO COPY DATA TO USER SPACE IN SYS_GET!\n");
        return -EFAULT;
    }

    return 0;
}

asmlinkage long sys_get_scheduling_score(void){
    long current_time = get_seconds();
    long value;
    printk(KERN_INFO "GET_SCHED_TIME CALLED! PAPADAKIS GEORGIOS 4975!! PROCESS: %d\n", current->pid);

    if(current_time <= current->deadline_1){
        value=100;
    }else if(current_time > current->deadline_2){
        value=0;
    }else{
        value=((current->deadline_2 - current_time)*100)/ (current->deadline_2 - current->deadline_1);
    }

    printk(KERN_INFO "SCORE: %ld\n", value);
    return value;
}
