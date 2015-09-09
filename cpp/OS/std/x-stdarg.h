/*
 * x-stdarg.h
 *
 *  Created on: Feb 27, 2015
 *      Author: mailmindlin
 */

#ifndef STD_X_STDARG_H_
#define STD_X_STDARG_H_

#include "x-stdint.h"

#ifdef __REALCOMP__
#include <stdarg.h>
#else
#warning "Using custom/incomplete/broken implementation of stdarg"
typedef uint32_t va_list;
#define va_start(vl,n) vl=n
#define va_arg(vl,typ) (typ)vl
#define va_end(vl) vl=-1
extern int vsnprintf (unsigned char * s, size_t n, const char * format, va_list arg );
#endif
#endif /* STD_X_STDARG_H_ */
