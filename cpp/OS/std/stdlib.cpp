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
#include "../std/algorithm.h"
#include "../std/limits.h"
#include "../std/Math.h"
#include "../std/memcpy.h"
#include "../std/memory.h"
#include "../std/RaspberryPi.h"
#include "../std/stdasm.h"
#include "../std/stddef.h"
#include "../std/stdint.h"
#include "../std/strlen.h"
#include "../std/Time.cpp"
#undef EXT
#undef EXC
#endif /* STDLIB_H_ */
