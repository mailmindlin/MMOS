/*
 * NodeIterator.cpp
 *
 *  Created on: Feb 23, 2015
 *      Author: wfeehery17
 */

#include "../../std/util/NodeIterator.hpp"
template<typename T>
NodeIterator<T>::NodeIterator(Node<T>* first) {
	count = 0;
	current = first;
}

template<typename T>
size_t NodeIterator<T>::nextIndex() const {
	return count+1;
}

template<typename T>
size_t NodeIterator<T>::previousIndex() const{
	return this->count-1;
}

template<typename T>
NodeIterator<T>::~NodeIterator() {
}
template<typename T>
bool NodeIterator<T>::hasNext() const{
	return current->getNext() != NULL;
}
template<typename T>
bool NodeIterator<T>::hasPrevious() const{
	return current->getPrev() != NULL;
}
template<typename T>
T* NodeIterator<T>::next() {
	count++;
	return (current = current->getNext())->getData();
}
template<typename T>
T* NodeIterator<T>::previous() {
	count--;
	return (current = current->getPrev())->getData();
}
template<typename T>
void NodeIterator<T>::remove() {
	Node<T>* tmp = current;
	if (tmp->getPrev() != NULL)
		tmp->getPrev()->next = tmp->getNext();
	if (tmp->getNext() != NULL)
		tmp->getNext()->prev = tmp->getPrev();
	current=tmp->next;
	delete current;
}
template<typename T>
void NodeIterator<T>::set(T* e) {
	current->v=e;
}
