/*
 * Long.h
 *
 *  Created on: Feb 23, 2015
 *      Author: mailmindlin
 */

#ifndef STD_LANG_LONG_H_
#define STD_LANG_LONG_H_

#include "../x-stddef.h"
#include "../x-stdint.h"
#include "Comparable.hpp"
#include "Number.hpp"
#include "String.h"

class Long: public Number, public Comparable<Long> {
public:
	Long(long l);
	Long(String& s);
	~Long();
	String& toString();
	uint32_t hashCode();
	bool equals(Object& other);
	int compareTo(Long& anotherLong);
	virtual uint8_t byteValue() const;
	virtual int intValue() const;
	virtual long longValue() const;
	virtual short shortValue() const;
	virtual float floatValue() const;
	virtual double doubleValue() const;
	Long& operator+(Number& n) const;
	Long& operator+(long n) const;
	Long& operator+(int n) const;
	Long& operator+(short n) const;
	Long& operator+(uint8_t n) const;
	Long& operator-(Number& n) const;
	Long& operator-(long n) const;
	Long& operator-(int n) const;
	Long& operator-(short n) const;
	Long& operator-(uint8_t n) const;
	Long& operator*(Number& n) const;
	Long& operator*(long n) const;
	Long& operator*(int n) const;
	Long& operator*(short n) const;
	Long& operator*(uint8_t n) const;
	Long& operator/(Number& n) const;
	Long& operator/(long n) const;
	Long& operator/(int n) const;
	Long& operator/(short n) const;
	Long& operator/(uint8_t n) const;
	Long& operator=(Number& n);
	Long& operator=(long n);
	Long& operator=(int n);
	Long& operator=(short n);
	Long& operator=(uint8_t n);
	Long& operator+=(Number& n);
	Long& operator+=(long n);
	Long& operator+=(int n);
	Long& operator+=(short n);
	Long& operator+=(uint8_t n);
	int compareTo(Long& other);
	bool equals(Long& other);
	static long parseLong(String& s, size_t radix);
	static Long& valueOf(long l);
	static Long& valueOf(String& s);
	static Long& valueOf(String& s, size_t radix);
	static long highestOneBit(long i);
	static long lowestOneBit(long i);
	static size_t numberOfLeadingZeroes(long i);
	static size_t numberOfTrailingZeros(long i);
	static size_t bitCount(long i);
	static long rotateLeft(long i, size_t distance);
	static long rotateRight(long i, int distance);
	static long reverse(long i);
	static uint8_t signum(long i);
	static long reverseBytes(long i);
	static void getChars(long i, int index, char* buf);
	static String& stringFor(long i);
	static String& stringFor(long i, size_t radix);
	const static uint32_t SIZE = 64;
	const static long MIN_VALUE = 0x8000000000000000L;
	const static long MAX_VALUE = 0x7fffffffffffffffL;
	const static long UNSIGNED_MASK = 0xFFFFFFFFFFFFFFFF;
	const static long SIGNED_MASK = 0x7FFFFFFFFFFFFFFF;
	const static long SIGN_MASK = 0x8000000000000000;
protected:
	static size_t stringSize(long x);
	const long value;
};

#endif /* STD_LANG_LONG_H_ */
