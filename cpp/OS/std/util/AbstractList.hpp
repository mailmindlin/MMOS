/*
 * AbstractList.hpp
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef STD_UTIL_ABSTRACTLIST_HPP_
#define STD_UTIL_ABSTRACTLIST_HPP_

#include <x-stddef.h>
#include "AbstractCollection.hpp"
#include "Collection.hpp"
#include "Iterator.hpp"
#include "List.hpp"
#include "ListIterator.hpp"

template<class T>
class AbstractList: public List<T>, public AbstractCollection<T> {
public:
	virtual ~AbstractList();
	virtual bool add(T* element) {
		List<T>::addAt(0, element);
		return true;
	}
	virtual bool addAll(size_t index, Collection<T>* x) {
		size_t offset=0;
		Iterator<T>* iterator = x->iterator();
		while(iterator->hasNext())
			addAt(index+(offset++),iterator->next());
		delete iterator;
		return true;
	}
	virtual T& get(size_t index) {
		Iterator<T>* itr = Collection<T>::iterator();
		itr->skip(index);
		T* result = itr->next();
		delete itr;
		return result;
	}
	virtual bool remove(T& o) {
			ListIterator<T> * li = List<T>::listiterator();
			if (o == nullptr) {
				while (li->hasNext()) {
					if (li->next() == nullptr) {
						li->remove();
						return true;
					}
				}
			} else {
				while (li->hasNext()) {
					if (o.equals(li->next())) {
						li->remove();
						return true;
					}
				}
			}
			return false;
		}
};
#endif /* STD_UTIL_ABSTRACTLIST_HPP_ */
