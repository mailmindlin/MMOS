/*
 * x-iterator.h
 *
 *  Created on: Mar 10, 2015
 *      Author: mailmindlin
 */

#ifndef STD_X_ITERATOR_H_
#define STD_X_ITERATOR_H_

#ifdef __REALCOMP__
#include <iterator>
#else
#warning "Using custom/incomplete/broken implementation of iterator!"
namespace std {
template<class Category, class T, class Distance = ptrdiff_t, class Pointer = T*, class Reference = T&>
struct iterator {
	typedef T value_type;
	typedef Distance difference_type;
	typedef Pointer pointer;
	typedef Reference reference;
	typedef Category iterator_category;
};
template<class Category, class T, class Distance = ptrdiff_t, class Pointer = T*, class Reference = T&>
struct const_iterator {
	typedef T value_type;
	typedef Distance difference_type;
	typedef Pointer pointer;
	typedef Reference reference;
	typedef Category iterator_category;
};
}
#endif

#endif /* STD_X_ITERATOR_H_ */
