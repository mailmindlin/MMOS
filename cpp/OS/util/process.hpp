#include "../std/x-stddef.h"

/*
 * process.c
 *
 *  Created on: Apr 16, 2015
 *      Author: wfeehery17
 */
#ifndef _UTIL_PROCESS_C
#include "../std/x-stdint.h"
typedef bool(*PROCCONT)(void);
struct Stack {
	void* ptr;
	size_t length;
};
struct Process {
	uint32_t pid;
	uint8_t priority;
	uint8_t flags;
	Stack** stack;
};

#endif
