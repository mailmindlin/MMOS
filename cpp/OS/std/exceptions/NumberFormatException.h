/*
 * NumberFormatException.hpp
 *
 *  Created on: Feb 23, 2015
 *      Author: wfeehery17
 */

#ifndef STDLIB_EXCEPTIONS_NUMBERFORMATEXCEPTION_
#define STDLIB_EXCEPTIONS_NUMBERFORMATEXCEPTION_

#include "../../std/exceptions/Exception"
#include "../lang/String.hpp"

class NumberFormatException: public Exception {
public:
	NumberFormatException(const char* c) :
			Exception(c) {
	}
	static NumberFormatException& forInputString(String& s) {
		return new NumberFormatException("For input string: \"" + s + "\"");
	}
};

#endif /* STDLIB_EXCEPTIONS_NUMBERFORMATEXCEPTION_ */
