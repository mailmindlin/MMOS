/*
 * OutOfMemoryException.hpp
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef STD_EXCEPTIONS_OUTOFMEMORYEXCEPTION_H_
#define STD_EXCEPTIONS_OUTOFMEMORYEXCEPTION_H_

#include "../../std/exceptions/Exception"

class OutOfMemoryException: public Exception {
public:
	OutOfMemoryException(const char* c) :
			Exception(c) {

	}
};
#endif /* STD_EXCEPTIONS_OUTOFMEMORYEXCEPTION_H_ */
