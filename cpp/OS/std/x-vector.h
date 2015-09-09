/*
 * x-vector.h
 *
 *  Created on: Feb 27, 2015
 *      Author: mailmindlin
 */

#ifndef STD_X_VECTOR_H_
#define STD_X_VECTOR_H_

#include "x-stddef.h"
#include "x-iterator.h"

#ifdef __REALCOMP__
#include <vector>
#else
#warning "Using custom/incomplete/broken implementation of vector!"
namespace std {
template<typename T>
class vector {
public:
	void push_back(const T& x);
	size_t size() const noexcept;
	T& operator[] (size_t n);
	const T& operator[] (size_t n) const;
	bool empty() const noexcept;
	void pop_back();
	std::iterator erase (std::const_iterator position);
	std::iterator erase (std::const_iterator first, std::const_iterator last);
	std::iterator begin() noexcept;
	std::const_iterator begin() const noexcept;
	std::iterator end() noexcept;
	std::const_iterator end() const noexcept;
};
}
#endif
#endif /* STD_X_VECTOR_H_ */
