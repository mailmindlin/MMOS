/*
 * LinkedList.hpp
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef STD_UTIL_LINKEDLIST_HPP_
#define STD_UTIL_LINKEDLIST_HPP_

#include "../../std/stddef.h"
#include "../../std/util/AbstractList.hpp"
#include "../../std/util/Collection.hpp"
#include "../../std/util/Node.hpp"

template<typename T>
class LinkedList: public AbstractList<T> {
public:
	LinkedList();
	LinkedList(Collection<T>* c);
	void addFirst(T* o);
	void addLast(T* o);
	void addAt(size_t index, T* o);
	T& get(size_t index);
	bool contains(T* t) const;
	void clear();
	T& removeAt(size_t index);
	size_t size() const;
	List<T>* subList(size_t start, size_t end);
	ListIterator<T>* listiterator() const;
	Iterator<T>* iterator() const;
//	bool addAll(size_t index, Collection<T>* x);

	size_t indexOf(T& e);
	~LinkedList();
protected:
	Node<T>* first;
	Node<T>* last;
	Node<T>* nodeAt(size_t index) const;
};

#endif /* STD_UTIL_LINKEDLIST_HPP_ */
