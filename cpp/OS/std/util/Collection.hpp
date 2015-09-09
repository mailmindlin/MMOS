/*
 * Collection.hpp
 *
 *  Created on: Feb 19, 2015
 *      Author: mailmindlin
 */

#ifndef STD_UTIL_COLLECTION_HPP_
#define STD_UTIL_COLLECTION_HPP_

#include "../x-stddef.h"
#include "Iterator.hpp"

template<class T>
class Collection: public Object {
public:
	virtual bool add(T* x);
	virtual bool addAll(Collection<T>* x);
	virtual bool contains(T* x) const;
	virtual bool containsAll(Collection<T>* x) const;
	virtual bool equals(Object& o) const;
	virtual bool isEmpty() const;
	virtual bool remove(T* o);
	virtual bool removeAll(Collection<T>* x);
	virtual bool retainAll(Collection<T>* x);
	virtual size_t size() const;
	virtual T* toArray() const;
	virtual T* toArray(T* arr) const;
	virtual Iterator<T>* iterator() const;
	virtual Collection<T>& operator+(Collection<T>& x) {
		addAll(x);
		return this;
	}
	virtual Collection<T>& operator-(Collection<T>& x) {
		removeAll(x);
		return this;
	}
};

#endif /* STD_UTIL_COLLECTION_HPP_ */
