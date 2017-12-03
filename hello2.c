//Name:Ovie Umukoro
//UBID:0991251
//CLASS: CPSC 503 - OPERATING SYSTEMS
//PROJECT: UNIX SHELL WITH SELECTION
//PROJECT 1-B


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <dirent.h>
#include <sstream>
#include <iostream>
#include <sys/types.h>
#include <sys/wait.h>

using namespace std;

//  max size of command line characters- 100 characters
#define MAX_COMMAND_LINE 100

//  max command line arguments
#define MAX_ARGV  10 

//  represents the structure of the directory
struct dirent **curDirectory;

//  the number of executables in the current 
//  directory
int execCount = 0;

//  the strings for building the command
//  to execute
char cmdString[40];

//  the length of the argument
char *arg[40];

//  check if selected was used
int sel_flag = 0;

//  the length of the temporary argument
int cmdString_length = 0;

//  store the reult of fork
pid_t pid;

//  the value of the child status
int ch_status = 0;

//  regular loop coutner
int i = 0;


//  list all the execuatble files in the current directory
static int getExecutablesInDirectory(const struct dirent *currDirectory)
{
    //  get the name of the first file in the directory
    const char *filename = currDirectory->d_name;

    //  shows the ith char in the filename
    char charAtIndex[50];
    
    //  saves the file if its an exec file
    char execFile[50];

    //  get the length of the first file
    int fileLength = strlen(filename)-4;

    //  copy the filename to the first temp variable
    strcpy(charAtIndex,filename);

    //  assign the contents o
    int i = 0;
    int charCount = 0;

    //  check if the current file is an executable file
    //  file is executable if it contains the postfix ".out"

    for ( i = 0; charAtIndex[i] != '\0'; i++) {
        if (charAtIndex[i] =='.' || charCount>0) {
            execFile[charCount]=charAtIndex[i];
            charCount++;
        }
    }

    execFile[charCount]='\0';

    if(fileLength >= 0)
    {
        if(charCount==0)
        {
            return 1;
        }
        else if (strncmp(execFile, ".out",4) == 0)
        {
            return 1;
        }
    }
    return 0;
}


int main() {
    //  maximum characters to be entered in the command line is 50
    string  usr_input;  

    //  maximum number of command line arguments should be 6
    char* argv [MAX_ARGV/2  + 1];

    //  always true.
    int should_run = 1;

    while (should_run) {
                //  Execute the command using the UNIX FORK MODEL

                //  create a forked process to execute the 
                //  user's command
                pid = fork();

                //  Process cceation failed
                if (pid < 0) {
                    cout << "Process creation Failed " << endl;
                    return -1;
                }

                //  if pid = 0, its a child process
                else if (pid == 0) {

                    //  execute the child process
                    if (execvp(*arg, arg) < 0) {       
                        return 0;
                    }
                
                }

                //  the parent process waits for the child
                //  process to complete
                else {
                    while (wait(&ch_status) != pid);
                }
            } else {

                if (j > 0) {
                    //  set the length to zero
                    cmdString[j] = '\0';

                    //  create a new array
                    arg[cmdString_length] = (char*)malloc(strlen(cmdString) + 1);

                    //  copy the command to the arguments
                    strcpy(arg[cmdString_length], cmdString);
                    cmdString_length++;
                }

                //  set the command variable to null
                arg[cmdString_length] = NULL;

                //  create process to execute the command
                pid = fork();

                //  process creation failed
                if (pid < 0) {
                    printf("Process creation failed\n");
                    return -1;

                }else if (pid == 0) {
                    if (execvp(*arg, arg) < 0) {
                    return 0;
                    }
                }else {
                    //  this is parent process
                    //  wait for the child process
                    while(wait(&ch_status) != pid);
                }
            }

        } else {


        }
        
    }
    
    return 0;
}