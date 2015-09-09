/*
 * stdlib.h
 *
 *  Created on: Feb 24, 2015
 *      Author: mailmindlin
 */

#ifndef STDLIB_H_
#define STDLIB_H_

#ifdef __LIB_STD
#define EXT extern "C"
#define EXC extern "C++"
#else
#define EXT
#define EXC
#endif
#include "algorithm.h"
//#include "arm.h"
#include "limits.h"
#include "Math.h"
#include "memcpy.h"
#include "memory.h"
#include "RaspberryPi.h"
#include "stdasm.h"
#include "strlen.h"
#include "Time.cpp"
#include "exceptions/ArrayIndexOutOfBoundsException.h"
#include "exceptions/Exception.h"
#include "exceptions/NumberFormatException.h"
#include "exceptions/OutOfMemoryException.h"
#include "x-errno.h"
#include "x-iterator.h"
#include "x-stdarg.h"
#include "x-stddef.h"
#include "x-stdint.h"
#include "x-stdio.h"
#include "x-vector.h"
#undef EXT
#undef EXC
#endif /* STDLIB_H_ */
