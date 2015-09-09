/*
 * LinkedList.cpp
 *
 *  Created on: Feb 19, 2015
 *      Author: mailmindlin
 */

#include "LinkedList.hpp"

#include "../exceptions/ArrayIndexOutOfBoundsException.h"
#include "NodeIterator.hpp"

template<typename T>
LinkedList<T>::LinkedList() {
	first = nullptr;
	last = nullptr;
}

template<typename T>
LinkedList<T>::LinkedList(Collection<T>* c) {
	first = nullptr;
	last = nullptr;
	addAll(0, c);
}

template<typename T>
void LinkedList<T>::addFirst(T& o) {
	Node<T>* tmp = first;
	if (tmp != nullptr)
		first = new Node<T>(o);
	else
		first = new Node<T>(o,tmp);
}

template<class T>
void LinkedList<T>::addLast(T& o) {
	Node<T>* tmp = last;
	if(last==nullptr)
		last = new Node<T>(o);
	else
		last = new Node<T>(tmp,o);
	if (first == nullptr)
		first = last;
}

template<typename T>
void LinkedList<T>::addAt(size_t index, T* element) {
	Node<T>* n = nodeAt(index);
	Node<T>* tmp = new Node<T>(n->prev, element, n);
	if (index == 0)
		first = tmp;
	else if (n == nullptr)
		throw new ArrayIndexOutOfBoundsException();
	if (n->next == nullptr)
		last = tmp;
}
/*
template<typename T>
bool LinkedList<T>::addAll(size_t index, Collection<T>* x) {
	Node<T>* n = nodeAt(index);
	Iterator<T>* iterator = x->iterator();
	while (iterator->hasNext()) {
		Node<T>* prev = n;
		n = new Node<T>(n->prev, iterator->next(), n);
		if (prev == nullptr)
			first = n;
	}
}
*/
template<typename T>
T& LinkedList<T>::get(size_t index) {
	return nodeAt(index)->getData();
}

template<typename T>
size_t LinkedList<T>::indexOf(T& e) {
	Node<T>* current = first;
	size_t counter = 0;
	while (true) {
		if (current->getData() == e)
			return counter;
		current = current->getNext();
		counter++;
		if (current == nullptr)
			return -1;
	}
}
template<typename T>
bool LinkedList<T>::contains(T* e) const {
	Node<T>* current = first;
	while (true) {
		if (current == nullptr)
			return false;
		if (current->getData() == e)
			return true;
		current = current->getNext();
	}
}

template<typename T>
LinkedList<T>::~LinkedList() {
	Node<T> * tmp;
	while (first != 0) {
		tmp = first;
		first = first->next;
		delete tmp;
	}
}

template<typename T>
void LinkedList<T>::clear() {
	Node<T> * tmp;
	while (first != nullptr) {
		first = (tmp = first)->getNext();
		delete tmp;
	}
}
template<typename T>
T& LinkedList<T>::removeAt(size_t index) {
	Node<T> * n = nodeAt(index);
	if (first == n)
		first = n->getNext();
	if (last == n)
		last = n->getPrev();
	if (n->getPrev() != nullptr)
		n->getPrev()->next = n->getNext();
	if (n->getNext() != nullptr)
		n->getNext()->prev = n->getPrev();
	T* data = n->getData();
	delete n;
	return data;
}
template<typename T>
Node<T>* LinkedList<T>::nodeAt(size_t index) const {
	Node<T>* current = first;
	for (size_t i = 0; i < index; i++) {
		if (current == nullptr)
			throw new ArrayIndexOutOfBoundsException();
		current = current->getNext();
	}
	return current;
}
template<typename T>
size_t LinkedList<T>::size() const {
	Node<T>* current = first;
	size_t counter;
	for (counter = 1; current != nullptr; counter++)
		current = current->getNext();
	return counter;
}
template<typename T>
List<T>* LinkedList<T>::subList(size_t start, size_t end) {
	LinkedList<T>* result = new LinkedList<T>();
	Iterator<T>* itr = iterator();
	itr->skip(start - 1);
	for (size_t i = 0; i < end - start && itr->hasNext(); i++) {
		result->add(itr->next());
	}
	delete itr;
	return result;
}
template<typename T>
ListIterator<T>* LinkedList<T>::listiterator() const {
	NodeIterator<T>* result = new NodeIterator<T>(first);
	return reinterpret_cast<ListIterator<T>*>(result);
}
template<class T>
inline Iterator<T>* LinkedList<T>::iterator() const {
	return new NodeIterator<T>(first);
}
