/*
 * List
 *
 *  Created on: Feb 19, 2015
 *      Author: mailmindlin
 */

#ifndef STDLIB_LIST_HPP_
#define STDLIB_LIST_HPP_

#include "../x-stddef.h"
#include "Collection.hpp"
#include "ListIterator.hpp"

template<class T>
class List: public Collection<T> {
public:
	virtual bool add(T& element);
	virtual void addAt(size_t index, T& element);
	virtual bool addAll(size_t index, Collection<T>* x);
	virtual T& get(size_t index);
	virtual size_t indexOf(T& e);
	virtual size_t lastIndexOf(T& e);
	virtual T& removeAt(size_t index);
	virtual T& set(size_t index, T& newE);
	virtual List<T>* subList(size_t start, size_t end);
	virtual ListIterator<T>* listiterator() const;
	virtual ~List();
};
#endif /* STDLIB_LIST_HPP_ */
