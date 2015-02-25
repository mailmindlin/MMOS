/*
 * Character.hpp
 *
 *  Created on: Feb 23, 2015
 *      Author: wfeehery17
 */

#ifndef STD_LANG_CHARACTER_HPP_
#define STD_LANG_CHARACTER_HPP_

#include "../../std/lang/Object.hpp"
#include "../../std/stdint.h"

class Character: public Object {
public:
	Character();
	~Character();
	static int digit(char ch, int radix);
	static int digit(uint32_t ch, int radix);
	static const uint32_t MIN_RADIX = 2, MAX_RADIX = 36;
};

#endif /* STD_LANG_CHARACTER_HPP_ */
