/*
 * Comparator.hpp
 *
 *  Created on: Mar 6, 2015
 *      Author: mailmindlin
 */

#ifndef STD_LANG_COMPARATOR_HPP_
#define STD_LANG_COMPARATOR_HPP_
template<typename T>
class Comparator : public Object {
public:
	virtual int compare(Comparable<T>& a, Comparable<T>& b);
};




#endif /* STD_LANG_COMPARATOR_HPP_ */
