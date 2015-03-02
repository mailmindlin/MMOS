/*
 * ListIterator.hpp
 *
 *  Created on: Feb 23, 2015
 *      Author: wfeehery17
 */

#ifndef STD_UTIL_LISTITERATOR_HPP_
#define STD_UTIL_LISTITERATOR_HPP_

#include <x-stddef.h>
#include "Iterator.hpp"

template<typename T>
class ListIterator: public Iterator<T> {
public:
	virtual bool hasPrevious() const;
	virtual size_t nextIndex() const;
	virtual size_t previousIndex() const;
	virtual T* previous();
	virtual void remove();
	virtual void set(T* nv);
	virtual ~ListIterator();
};

#endif /* STD_UTIL_LISTITERATOR_HPP_ */
