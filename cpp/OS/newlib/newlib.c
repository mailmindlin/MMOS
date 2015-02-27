/*
 * newlibimpl.c
 *
 *  Created on: Feb 26, 2015
 *      Author: wfeehery17
 */
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/fcntl.h>
#include <sys/times.h>
#include <sys/errno.h>
#include <sys/time.h>
#include <stdio.h>
#include <errno.h>
#undef errno
extern int errno;
#ifndef EINVAL
extern int EINVAL;
#endif
#ifndef S_IFCHR
extern int S_IFCHR;
#endif
#ifndef caddr_t
typedef char caddr_t;
#endif
#ifdef __cplusplus
extern "C" {
#endif
/*
extern void _exit();
extern int close(int file);
extern char **environ; // pointer to array of char * strings that define the current environment variables
extern int fstat(int file, struct stat *st);
extern int getpid();
extern int isatty(int file);
extern int kill(int pid, int sig);
extern int lseek(int file, int ptr, int dir);
int read(int file, char *ptr, int len);
caddr_t _sbrk(int incr);
int write(int file, char *ptr, int len);*/
extern void abort(void) {
	while(1);
}
//TODO used
extern void _exit(int code) {
	abort();
}
//TODO used
extern int _close(int file) {
	return -1;
}
//TODO used
extern int _fstat(int file, struct stat *st) {
	st->st_mode = S_IFCHR;
	return 0;
}
//TODO used
extern int _getpid(void) {
	return 1;
}
//TODO used
extern int _isatty(int file) {
	return 1;
}
//TODO used
extern int _kill(int pid, int sig) {
	errno = EINVAL;
	return -1;
}
//TODO used
extern int _lseek(int file, int ptr, int dir) {
	return 0;
}
//TODO used
extern int _read(int file, char *ptr, int len) {
	return 0;
}
//TODO used
extern caddr_t _sbrk(int incr) {
	unsigned int* stack_ptr = ({ register unsigned int* arg0 __asm("sp"); arg0; });
	extern char _end; /* Defined by the linker */
	static char *heap_end;
	char *prev_heap_end;

	if (heap_end == 0) {
		heap_end = &_end;
	}
	prev_heap_end = heap_end;
	if (heap_end + incr > stack_ptr) {
		write(1, "Heap and stack collision\n", 25);
		abort();
	}

	heap_end += incr;
	return (caddr_t) prev_heap_end;
}
//TODO used
extern int _write(int file, char *ptr, int len) {
	int todo;

	for (todo = 0; todo < len; todo++) {
//		outbyte(*ptr++);
	}
	return len;
}
#ifdef __cplusplus
}
#endif
