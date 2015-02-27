/*
 * Exception
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef STD_EXCEPTIONS_EXCEPTION_H_
#define STD_EXCEPTIONS_EXCEPTION_H_

#include "../lang/Object.hpp"
#include "../lang/String.hpp"
#if (!defined(EXC)) || (!defined(EXT))
#define EXC
#define EXT
#endif
EXC class Exception: public Object {
public:
	Exception(const char* msg) {
		this->message = new String(msg);
	}
	String* toString() const{
		return message;
	}
	~Exception() {
		delete message;
	}
protected:
	String* message;
};
#endif /* STD_EXCEPTIONS_EXCEPTION_H_ */
