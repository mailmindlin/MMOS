/*
 * Node.h
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef NODE_H_
#define NODE_H_

#include "../../std/stddef.h"

template<typename T>
class Node {
public:
	Node(T* o):prev(nullptr),v(o),next(nullptr) {
	}
	Node(Node<T>* prev, T* o):prev(prev),v(o),next(nullptr) {
		if (prev != nullptr)
			prev->next = this;
	}
	Node(T* o, Node<T>* next):v(o),next(next),prev(nullptr) {
		if (next != nullptr)
			next->prev = this;
	}
	Node(Node<T>* prev, T* o, Node<T>* next):v(o),next(next),prev(prev) {
		if (prev != nullptr)
			prev->next = this;
		if (next != nullptr)
			next->prev = this;
	}
	Node<T>* getNext() {
		return next;
	}
	Node<T>* getPrev(){
		return prev;
	}
	T* getData(){
		return v;
	}
	T* v;
	Node<T>* prev;
	Node<T>* next;
};
#endif /* NODE_H_ */
