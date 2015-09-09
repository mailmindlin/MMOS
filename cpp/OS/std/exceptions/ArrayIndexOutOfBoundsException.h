/*
 * IndexOutOfBoundException.h
 *
 *  Created on: Feb 23, 2015
 *      Author: mailmindlin
 */

#ifndef STD_EXCEPTIONS_ARRAYINDEXOUTOFBOUNDSEXCEPTION_H_
#define STD_EXCEPTIONS_ARRAYINDEXOUTOFBOUNDSEXCEPTION_H_

#include "../lang/Object.hpp"
#include "Exception.h"

#if (!defined(EXC)) || (!defined(EXT))
#define EXC
#define EXT
#endif
EXC class ArrayIndexOutOfBoundsException: public Exception {
public:
	ArrayIndexOutOfBoundsException() :
			Exception("Index out of range.") {
	}
};
#endif /* STD_EXCEPTIONS_ARRAYINDEXOUTOFBOUNDSEXCEPTION_H_ */
