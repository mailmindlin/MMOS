/*
 * Byte.hpp
 *
 *  Created on: Feb 23, 2015
 *      Author: wfeehery17
 */

#ifndef STD_LANG_BYTE_HPP_
#define STD_LANG_BYTE_HPP_

#include "../x-stdint.h"
#include "Number.hpp"

class Byte: public Number, public Comparable {
public:
	Byte(uint8_t b);
	virtual ~Byte();
	const static unsigned int MAX_VALUE = 0x7f, MIN_VALUE = 0x80;
	const static unsigned int UNSIGNED_MASK = 0xFF;
	const static unsigned int SIGNED_MASK = 0x7F;
};

#endif /* STD_LANG_BYTE_HPP_ */
