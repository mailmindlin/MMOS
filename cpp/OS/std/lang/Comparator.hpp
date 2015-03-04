/*
 * Comparator.hpp
 *
 *  Created on: Feb 23, 2015
 *      Author: wfeehery17
 */

#ifndef STD_LANG_COMPARATOR_HPP_
#define STD_LANG_COMPARATOR_HPP_

#include "Object.hpp"

template<class T>
class Comparable: Object {
public:
	virtual int compareTo(Comparable<T>& o);
};
#endif /* STD_LANG_COMPARATOR_HPP_ */
