/*
 * Number.hpp
 *
 *  Created on: Feb 23, 2015
 *      Author: mailmindlin
 */

#ifndef STD_LANG_NUMBER_HPP_
#define STD_LANG_NUMBER_HPP_

#include "../x-stddef.h"
#include "../x-stdint.h"
#include "Comparable.hpp"

class Number: public Object, Comparable<Number> {
public:
	virtual uint8_t byteValue() const;
	virtual int intValue() const;
	virtual long longValue() const;
	virtual short shortValue() const;
	virtual float floatValue() const;
	virtual double doubleValue() const;
	virtual Number& operator=(Number& n) const;
	virtual Number& operator+(Number& n) const;
	virtual Number& operator-(Number& n) const;
	virtual Number& operator*(Number& n) const;
	virtual Number& operator/(Number& n) const;
	virtual Number& operator<<(Number& n) const;
	virtual Number& operator>>(Number& n) const;
	virtual Number& operator=(Number& n) const;
	virtual Number& operator+=(Number& n) const;
	virtual Number& operator-=(Number& n) const;
	virtual Number& operator++() const;
	virtual Number& operator--() const;
	virtual Number& operator++(int unused) const;
	virtual Number& operator--(int unused) const;
	virtual Number& rotateLeft(size_t amt) const;
	virtual Number& rotateRight(size_t amt) const;
};
#endif /* STD_LANG_NUMBER_HPP_ */
