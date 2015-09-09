/*
 * Object.hpp
 * This is a really useful class to have everything implement.
 * For example, you can use instanceof() to fix generics stuff,
 * toString() is incredibly helpful for debugging, and
 *  Created on: Feb 19, 2015
 *      Author: mailmindlin
 */

#ifndef STDLIB_OBJECT_HPP_
#define STDLIB_OBJECT_HPP_

#include "../x-stdint.h"

#if (!defined(EXC)) || (!defined(EXT))
#define EXC
#define EXT
#endif
class String;
EXC class Object {
public:
	Object() {
	}
	virtual ~Object() {
	}
	virtual bool equals(Object& other) const {
		return getPtr() == other.getPtr();
	}
	uint32_t hashCode() const {
		return (uint32_t) this;
	}
	virtual void notify() {/*do nothing*/
	}
	virtual void notifyAll() {/*do nothing*/
	}
	virtual String& toString() const {
		return valueOfPtr(this);
	}
	template<typename parent>
	bool instanceof() const {
		return dynamic_cast<const parent*>(this) != 0;
	}
	virtual const void* getPtr() const {
		return static_cast<const void*>(this);
	}
protected:
	virtual Object clone() const {
		return *this;
	}
	template<class to>
	to cloneAs() const {
		return (*this);
	}
	static String& valueOfPtr(const void* ptr);
};

#endif /* STDLIB_OBJECT_HPP_ */
