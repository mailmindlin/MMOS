/*
 * OutOfMemoryException.hpp
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef STD_EXCEPTIONS_OUTOFMEMORYEXCEPTION_H_
#define STD_EXCEPTIONS_OUTOFMEMORYEXCEPTION_H_

#include "../lang/Object.cpp"
#include "Exception.h"

#if (!defined(EXC)) || (!defined(EXT))
#define EXC
#define EXT
#endif
EXC class OutOfMemoryException: public Exception {
public:
	OutOfMemoryException(const char* c) :
			Exception(c) {

	}
};
#endif /* STD_EXCEPTIONS_OUTOFMEMORYEXCEPTION_H_ */
