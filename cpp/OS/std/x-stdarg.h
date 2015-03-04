/*
 * x-stdarg.h
 *
 *  Created on: Feb 27, 2015
 *      Author: wfeehery17
 */

#ifndef STD_X_STDARG_H_
#define STD_X_STDARG_H_

#include "x-stdint.h"

#ifdef __REALCOMP__
#include <stdarg.h>
#else
typedef uint32_t va_list;
#define va_start(vl,n) vl=n
#define va_arg(vl,typ) (typ)vl
#define va_end(vl) vl=-1
#endif
#endif /* STD_X_STDARG_H_ */
