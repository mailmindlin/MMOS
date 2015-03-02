/*
 * x-stdio.h
 *
 *  Created on: Feb 27, 2015
 *      Author: wfeehery17
 */

#ifndef STD_X_STDIO_H_
#define STD_X_STDIO_H_
#include <x-stddef.h>
#ifdef __REALCOMP__
#include <stdio.h>
#else
extern int vsnprintf (char * s, size_t n, const char * format, va_list arg );
#endif


#endif /* STD_X_STDIO_H_ */
