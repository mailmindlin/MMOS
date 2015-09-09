/*
 * scheduler.c
 *
 *  Created on: Mar 30, 2015
 *      Author: wfeehery17
 */

#include "scheduler.h"
/**
 * Get current process's PID
 * @return PID
 */
extern "C"
unsigned int scheduler_getPID() {
	return Scheduler::getPID();
}
namespace Scheduler {
/**
 * Current process
 */
struct Process* currentProcess = NULL;
std::vector<Process*>* processes = NULL;
static bool requestShutdown=false;
unsigned int next_pid=0;
/**
 * Get current process's PID
 * @return PID
 */
unsigned int getPID() {
	return currentProcess==NULL?0:currentProcess->pid;
}
/**
 * Begin scheduler. Will return as forked process 0
 */
void begin() {
	processes = new std::vector<Process*>();

}
void startProcess(Process* p) {
//TODO finish
}
void fork() {
//TODO finish
}
void run() {
	if(processes == NULL)
		throw -1;
	while(!requestShutdown) {
		//get next process
		//TODO finish
	}
}
void end() {
	requestShutdown=true;
}
}
