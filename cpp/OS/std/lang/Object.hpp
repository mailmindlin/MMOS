/*
 * Object.hpp
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
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
	virtual bool equals(Object* other) const {
		return this == other;
	}
	uint32_t hashCode() {
		return (uint32_t) this;
	}
	virtual void notify() {/*do nothing*/
	}
	virtual void notifyAll() {/*do nothing*/
	}
	virtual String& toString() const {
		void* ptr=nullptr;
		getPtr(ptr);
		return valueOfPtr(ptr);
	}
	template<typename parent>
	bool instanceof() const {
		return dynamic_cast<const parent*>(this) != 0;
	}
	virtual void getPtr(const void* ptr) const {
		if(ptr!=this)
			ptr=this;
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
