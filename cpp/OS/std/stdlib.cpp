/*
 * stdlib.h
 *
 *  Created on: Feb 24, 2015
 *      Author: wfeehery17
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
#include "limits.h"
#include "Math.h"
#include "memcpy.h"
#include "memory.h"
#include "RaspberryPi.h"
#include "stdasm.h"
#include "stddef.h"
#include "strlen.h"
#include "Time.cpp"
#include "exceptions/ArrayIndexOutOfBoundsException.h"
#include "exceptions/Exception.h"
#include "exceptions/NumberFormatException.h"
#include "exceptions/OutOfMemoryException.h"
#undef EXT
#undef EXC
#endif /* STDLIB_H_ */
