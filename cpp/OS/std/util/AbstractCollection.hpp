/*
 * AbstractCollection.hpp
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef STD_UTIL_ABSTRACTCOLLECTION_HPP_
#define STD_UTIL_ABSTRACTCOLLECTION_HPP_

#include "../../std/exceptions/OutOfMemoryException.h"
#include "../../std/limits.h"
#include "../../std/memory.h"
#include "../../std/stddef.h"
#include "../../std/util/Collection.hpp"
#include "../../std/util/Iterator.hpp"

template<class T>
class AbstractCollection: public Collection<T> {
public:
	virtual bool isEmpty() {
		return Collection<T>::size() == 0;
	}
	virtual bool contains(T* e) {
		Iterator<T> * i = Collection<T>::iterator();
		if (nullptr==e) {//check if e is null
			while (i->hasNext())
				if (nullptr==i->next())
					return true;
		} else {
			while (i->hasNext())
				if (e->equals(i->next()))
					return true;
		}
		return false;
	}
	virtual T* toArray() {
		// Estimate size of array; be prepared to see more or fewer elements
		T* r = new T[Collection<T>::size()];
		Iterator<T> * it = Collection<T>::iterator();
		AbstractCollection<T>::copyToArray(r, Collection<T>::size(), it);
		return r;
	}
	virtual T* toArray(T* r) {
		// Estimate size of array; be prepared to see more or fewer elements
		size_t sz = Collection<T>::size();
		Iterator<T> * it = Collection<T>::iterator();
		size_t i = 0;
		for (i = 0; i < sz; i++) {
			if (!(it->hasNext())) { // fewer elements than expected
				delete it;
				r[i] = nullptr; // NULL-terminate
				return r;
			}
			r[i] = (T) it->next();
		}
		if(it->hasNext())
			return finishToArray(r,i,it);
		else {
			delete it;
			return r;
		}
	}
	virtual bool removeAll(Collection<T>& x) {
		Iterator<T>* itr = x->iterator();
		while (itr->hasNext())
			if (!Collection<T>::remove(itr->next())) {
				delete itr;
				return false;
			}
		delete itr;
		return true;
	}
	virtual bool retainAll(Collection<T>& x) {
		Iterator<T>* itr = x->iterator();
		while (itr->hasNext()) {
			T& o = itr->next();
			if (!(x->contains(o)))
				if(!Collection<T>::remove(o)) {
					delete itr;
					return false;//failed
				}
		}
		delete itr;
		return true;
	}
	virtual ~AbstractCollection();
private:
	/**
	 * Copies iterator to array
	 * @param r partially filled array
	 * @param rlen length of array
	 * @param it iterator to copy from
	 * @return copied array
	 * @throws OutOfMemoryException if iterator has more than {@code SIZE_T_MAX} elements.
	 */
	static T* copyToArray(T* r, size_t rlen, Iterator<T> * it) {
		size_t len = rlen;
		size_t index = 0;
		while (it->hasNext()) {
			//array is filled, so grow it
			if (index >= len) {
				//calculate new size for array
				size_t newCap = ((len / 2) + 1) * 3;
				//integer overflow, so make it really big or throw an error
				if (newCap < len) {
					if (len >= SIZE_T_MAX)
						throw new OutOfMemoryException("Required array size too large!");
					newCap = SIZE_T_MAX; //otherwise, make it as big as possible
				}
				Array::grow(r, len, newCap);
				len = newCap;
			}
			r[index++] = it->next();
		}
		//clean up
		delete it;
		// trim if too big
		return (index == len) ? r : Array::grow(r, len, index);
	}

};
#endif /* STD_UTIL_ABSTRACTCOLLECTION_HPP_ */
