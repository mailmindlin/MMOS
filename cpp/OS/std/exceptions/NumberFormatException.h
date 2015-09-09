/*
 * NumberFormatException.hpp
 *
 *  Created on: Feb 23, 2015
 *      Author: mailmindlin
 */

#ifndef STDLIB_EXCEPTIONS_NUMBERFORMATEXCEPTION_
#define STDLIB_EXCEPTIONS_NUMBERFORMATEXCEPTION_

#include "../lang/Object.hpp"
#include "../lang/String.h"

#if (!defined(EXC)) || (!defined(EXT))
#define EXC
#define EXT
#endif
#include "../util/StringBuffer.hpp"
#include "Exception.h"

EXC class NumberFormatException: public Exception {
public:
	NumberFormatException(const char* c) :
			Exception(c) {
	}
	static NumberFormatException& forInputString(String& s) {
		StringBuffer* sb = new StringBuffer();
		(*sb)<<"For input string: \"";
		(*sb)<<s;
		(*sb)<<'"';
		const char* result = sb->toCharArray();
		delete sb;
		return *(new NumberFormatException(result));
	}
};

#endif /* STDLIB_EXCEPTIONS_NUMBERFORMATEXCEPTION_ */
