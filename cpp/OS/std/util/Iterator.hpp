/*
 * Iterator
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef STD_UTIL_ITERATOR_HPP_
#define STD_UTIL_ITERATOR_HPP_

#include <x-stddef.h>

#include "../lang/Object.cpp"

template<class E>
class Iterator : public Object {
public:
	virtual bool hasNext() const;
	virtual E* next();
	virtual void remove();
	virtual ~Iterator();
	virtual bool skip(size_t amt) {size_t i;for(i=0;i<amt && hasNext();i++)next();return i+1==amt;}
};

#endif /* STD_UTIL_ITERATOR_HPP_ */
