/*
 * x-stdlib.h
 *
 *  Created on: Apr 20, 2015
 *      Author: wfeehery17
 */

#ifndef STD_X_STDLIB_H_
#define STD_X_STDLIB_H_

#ifdef __REALCOMP__
#include <cstdlib>
#else
#include "x-stddef.h"
void* malloc(size_t size);
#endif
#endif /* STD_X_STDLIB_H_ */
