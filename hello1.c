//Name:Ovie Umukoro
//UBID:0991251
//CLASS: CPSC 503 - OPERATING SYSTEMS
//PROJECT: UNIX SHELL 


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

//  max size of command line characters- 100 characters
#define MAX_COMMAND_LINE 100

//  max command line arguments
#define MAX_ARGV  10 

//this function parse & split command into array of parameters...
void convertInputToCommand(char* usr_input, char** argv ){       
        for(int i = 0; i <= MAX_ARGV ; i++) {
        argv [i] = strsep(&usr_input, " ");               
        if(argv [i] == NULL) break;
    }
}

//this is the function to create child & parent process...
int executeCommand(char** argv ){                   

    //  create a fork of the parent process
    int pid = fork();                         

    //  returns a negative number in the event
    //  of a fork failure  
    if (pid < 0) {                                                    
        printf("Error:: process is neither child nor parent!!!\n");          
        return -1;
    }

    //  if fork succeeds and a chilid process
    //  is created, fork returns 0 in the child
    //  process and executes the user command  
    else if (pid == 0) {                                         
        execvp(argv [0], argv );                    
        return 0;
    }
    
    //  parent process waits for the execution of
    //  the child process
    else {                                                  
        int ch_status;                          
        waitpid(pid, &ch_status, 0);
        return 1;
    }
}   

int main() {
    //  maximum characters to be entered in the command line is 50
    char usr_input[MAX_COMMAND_LINE/2 + 1];  

    //  maximum number of command line arguments should be 6
    char* argv [MAX_ARGV/2  + 1];

    //  always true.
    int should_run = 1;

    while (should_run) {
        
        //  creates the os prompt
        printf("ubos>");
        fflush(stdout);

        //  receive user input    
        if(fgets(usr_input, sizeof(usr_input), stdin) == NULL) {
            break;                                            
        }
        
        if(usr_input[strlen(usr_input)-1] == '\n') {          //helps to remove trail of that character, if is there any newline character 
            usr_input[strlen(usr_input)-1] = '\0';           //this function helps to returns the length of string-here returns lenght of entered string
        }

        //function to parse the input...        
        convertInputToCommand(usr_input, argv );

        //  end the program if the user types exit
        if(strcmp(argv [0], "exit") == 0) {
            break;
        }

        //  do nothing if the user 
        //  does not enter any command
        if(executeCommand(argv ) == 0) {
            break;   
        }
    }
    
    return 0;
}
       
