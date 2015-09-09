/*
 * Character.hpp
 *
 *  Created on: Feb 23, 2015
 *      Author: mailmindlin
 */

#ifndef STD_LANG_CHARACTER_H_
#define STD_LANG_CHARACTER_H_

#include "../x-stdint.h"
#include "Object.hpp"

class Character: public Object {
public:
	Character();
	~Character();
	static int digit(char ch, int radix);
	static int digit(uint32_t ch, int radix);
	static const uint32_t MIN_RADIX = 2, MAX_RADIX = 36;
};

#endif /* STD_LANG_CHARACTER_H_ */
