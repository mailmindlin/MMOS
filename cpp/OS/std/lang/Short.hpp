/*
 * Short.hpp
 *
 *  Created on: Feb 23, 2015
 *      Author: wfeehery17
 */

#ifndef STD_LANG_SHORT_HPP_
#define STD_LANG_SHORT_HPP_

#include "Number.hpp"

class Short: public Number, public Comparable {
public:
	Short(signed short s);
	virtual ~Short();
	const static unsigned int MAX_VALUE = 0x7fff, MIN_VALUE = 0x8000;
	const static unsigned int UNSIGNED_MASK = 0xFFFF;
	const static unsigned int SIGNED_MASK = 0x7FFF;
};

#endif /* STD_LANG_SHORT_HPP_ */
