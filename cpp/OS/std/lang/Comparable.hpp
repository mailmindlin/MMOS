/*
 * Comparator.hpp
 *
 *  Created on: Feb 23, 2015
 *      Author: mailmindlin
 */

#ifndef STD_LANG_COMPARABLE_HPP_
#define STD_LANG_COMPARABLE_HPP_

#include "Object.hpp"

template<class T>
class Comparable: Object {
public:
	virtual int compareTo(Comparable<T>& o);
};
#endif /* STD_LANG_COMPARABLE_HPP_ */
