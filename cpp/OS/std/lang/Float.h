/*
 * Float.h
 *
 *  Created on: Mar 6, 2015
 *      Author: mailmindlin
 */

#ifndef STD_LANG_FLOAT_H_
#define STD_LANG_FLOAT_H_

#include "Comparable.hpp"
#include "Number.hpp"

class Float: public Number, public Comparable<Float> {
public:
	Float();
	virtual ~Float();
	const static float MAX_VALUE = 0x3FFFFFFF, MIN_VALUE = 0x80;
	const static float UNSIGNED_MASK = 0xFF;
	const static float SIGNED_MASK = 0x7F;
};

#endif /* STD_LANG_FLOAT_H_ */
