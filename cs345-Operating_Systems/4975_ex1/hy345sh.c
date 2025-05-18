/*PAPADAKIS GERORGIOS
 *CSD4975
 *ASSIGNMENT 1*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <limits.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>

#ifndef PATH_MAX
    #define PATH_MAX 4096
#endif
#define MAX_LETTERS 1000
#define MAX_COMMANDS 100
#define ENTER 10
#define SEMICOLON 59
#define QUOTATIONS 34
#define SPACE 32
#define EQUALS 61
#define DOLLAR 36
#define BACKSLASH 92

void prompt(){
	char cwd[PATH_MAX];
	char *username = getenv("USER");
	getcwd(cwd, sizeof(cwd));
	printf("csd4975-hy345sh@%s:%s ", username, cwd);
}

void setVar(char com[MAX_COMMANDS][MAX_LETTERS]){
	int i,k,flag_slash;
	char name[MAX_LETTERS];
	char value[MAX_LETTERS];
	for(i=0;(com[0][i]==SPACE)&&(i<MAX_LETTERS);i++);
	for(k=0;(k<MAX_LETTERS)&&(com[0][i]!=SPACE);k++){
		name[k]=com[0][i];
		i++;
	}
	if(strchr(com[1],QUOTATIONS)!=NULL){
		flag_slash=0;
		i=0;
		for(k=0;k<MAX_LETTERS;k++){
			if((com[1][k]!=QUOTATIONS)&&(flag_slash==0)){
				if(com[1][k]==BACKSLASH){
					flag_slash=1;
					continue;
				}else{
					value[i]=com[1][k];
					i++;
				}
			}else if((com[1][k]==QUOTATIONS)&&(flag_slash==0)){
				continue;
			}else if((com[1][k]!=QUOTATIONS)&&(flag_slash==1)){
				flag_slash=0;
				value[i]=BACKSLASH;
				i++;
			}else if((com[1][k]==QUOTATIONS)&&(flag_slash==1)){
				flag_slash=0;
				value[i]=QUOTATIONS;
				i++;
			}
		}
	}else{
		strncpy(value,com[1],MAX_LETTERS);
	}
	if(setenv(name,value,1)!=0){
		perror("SETENV FAILED!!\n");
	}
	return;
}

void getVar(char com[MAX_COMMANDS][MAX_LETTERS]){
	int i,k,flag_dollar,name_counter_w,name_counter_l,temp_count;
	char names[MAX_COMMANDS][MAX_LETTERS];
	char value[MAX_LETTERS];
	char *temp;
	for(i=0;(i<MAX_COMMANDS)&&(com[i][0]!='\0');i++){
		if(strchr(com[i],'$')!=NULL){
			memset(names,'\0',sizeof(names));
			memset(value,'\0',sizeof(value));
			temp=NULL;
			flag_dollar=0;
			name_counter_w=0;
			temp_count=0;
			for(k=0;com[i][k]!='\0';k++){
				if((com[i][k]!=DOLLAR)&&(flag_dollar==0)){
					value[temp_count]=com[i][k];
				}else if((com[i][k]==DOLLAR)&&(flag_dollar==0)){
					name_counter_l=0;
					flag_dollar=1;
				}else if((com[i][k]!=DOLLAR)&&(flag_dollar==1)){
					names[name_counter_w][name_counter_l]=com[i][k];
					name_counter_l++;
				}else if((com[i][k]==DOLLAR)&&(flag_dollar==1)&&(name_counter_l>0)){
					temp= getenv(names[name_counter_w]);
					strcat(value,temp);
					name_counter_w++;
					name_counter_l=0;
				}
			}
			if((flag_dollar==1)&&(name_counter_l>0)){
				temp= getenv(names[name_counter_w]);
				strcat(value,temp);
			}
			if(value != NULL){
				strncpy(com[i], value, MAX_LETTERS);
			}
		}
	}
}

void executeCom(char com[MAX_COMMANDS][MAX_LETTERS]){
	char *args[MAX_COMMANDS];
	int status;
	pid_t pid;

	char *token = strtok(com[0], " \n");
	int i = 0;
	while (token != NULL && i < MAX_COMMANDS - 1) {
		args[i++] = token;
		token = strtok(com[i], " \n");
	}
	args[i] = NULL;

	pid=fork();
	if(pid==0){
		if(execvp(args[0],args) == -1){
			perror("EXEC FAILED!\n");
			exit(EXIT_FAILURE);
		}
	}else if(pid >0){
		do {
			waitpid(pid, &status, WUNTRACED);
		} while (!WIFEXITED(status) && !WIFSIGNALED(status));
	}else{
		perror("FORK FAILED!\n");
		return;
	}
}

void split(char *str, int n, char com[MAX_COMMANDS][MAX_LETTERS]){
	int i,k,wc,lc, flag_qm;
	wc=0;
	lc=0;
	flag_qm=0;
	for(i=0;i<MAX_COMMANDS;i++){
		for(k=0;k<MAX_LETTERS;k++){
			com[i][k]='\0';
		}
	}
	for(i=0;str[i]!='\0';i++){
		if((flag_qm==0)&&(str[i]==QUOTATIONS)){
			flag_qm=1;
		}else if((flag_qm==1)&&(str[i]==QUOTATIONS)){
			flag_qm=0;
		}
		if((str[i]==n)&&(flag_qm==0)){
			if(lc!=0){
				wc++;
				lc=0;
			}
		}else{
			com[wc][lc]=str[i];
			lc++;
		}
	}
}

void input(char str[MAX_LETTERS]){
	int i;
	i=0;
	char c;
	c= getchar();
	while((c!= ENTER )&&(i<=999)){
		str[i]=c;
		i++;
		c= getchar();
	}
	str[i]='\0';
}	

int main(){
	int i;
	char inp_str[MAX_LETTERS];
	char com[MAX_COMMANDS][MAX_LETTERS];
	char args[MAX_COMMANDS][MAX_LETTERS];

	while(1){
		prompt();
		input(inp_str);
		if(strcmp(inp_str, "exit")==0){
			break;
		}else{
			split(inp_str, SEMICOLON, com);
			for(i=0;i<100;i++){
				if(com[i][0]!='\0'){
					if(strchr(com[i],'=')!=NULL){
						split(com[i],EQUALS, args);
						setVar(args);
					}else{
						split(com[i], SPACE, args);
						if(strchr(com[i],'$')!=NULL){
							getVar(args);
						}
						executeCom(args);
					}
				}
			}
		}
	}
	return 0;
}