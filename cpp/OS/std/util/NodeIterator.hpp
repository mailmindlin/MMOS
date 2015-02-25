/*
 * NodeIterator.hpp
 *
 *  Created on: Feb 23, 2015
 *      Author: wfeehery17
 */

#ifndef STD_UTIL_NODEITERATOR_HPP_
#define STD_UTIL_NODEITERATOR_HPP_

#include "../../std/stddef.h"
#include "../../std/util/ListIterator.hpp"
#include "../../std/util/Node.hpp"

template<typename T>
class NodeIterator: public ListIterator<T> {
public:
	bool hasNext() const;
	bool hasPrevious() const;
	size_t nextIndex() const;
	size_t previousIndex() const;
	T* next();
	T* previous();
	void remove();
	void set(T* nv);
	NodeIterator(Node<T>* first);
	~NodeIterator();
protected:
	size_t count;
	Node<T>* current;
};

#endif /* STD_UTIL_NODEITERATOR_HPP_ */
