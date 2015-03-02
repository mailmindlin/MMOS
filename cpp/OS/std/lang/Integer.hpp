/*
 * Integer.h
 *
 *  Created on: Feb 23, 2015
 *      Author: wfeehery17
 */

#ifndef STD_LANG_INTEGER_HPP_
#define STD_LANG_INTEGER_HPP_

#include <x-stddef.h>
#include <x-stdint.h>
#include "Number.hpp"
#include "String.hpp"

class String;

class Integer: public Number {
public:
	Integer(int l);
	Integer(String& s);
	~Integer();
	String& toString();
	uint32_t hashCode();
	bool equals(Object& other);
	int compareTo(Integer& anotherint);
	virtual uint8_t byteValue() const;
	virtual int intValue() const;
	virtual int intValue() const;
	virtual short shortValue() const;
	virtual float floatValue() const;
	virtual double doubleValue() const;
	Integer& operator+(Number& n) const;
	Integer& operator+(int n) const;
	Integer& operator+(int n) const;
	Integer& operator+(short n) const;
	Integer& operator+(uint8_t n) const;
	Integer& operator-(Number& n) const;
	Integer& operator-(int n) const;
	Integer& operator-(int n) const;
	Integer& operator-(short n) const;
	Integer& operator-(uint8_t n) const;
	Integer& operator*(Number& n) const;
	Integer& operator*(int n) const;
	Integer& operator*(int n) const;
	Integer& operator*(short n) const;
	Integer& operator*(uint8_t n) const;
	Integer& operator/(Number& n) const;
	Integer& operator/(int n) const;
	Integer& operator/(int n) const;
	Integer& operator/(short n) const;
	Integer& operator/(uint8_t n) const;
	Integer& operator=(Number& n);
	Integer& operator=(int n);
	Integer& operator=(int n);
	Integer& operator=(short n);
	Integer& operator=(uint8_t n);
	Integer& operator+=(Number& n);
	Integer& operator+=(int n);
	Integer& operator+=(int n);
	Integer& operator+=(short n);
	Integer& operator+=(uint8_t n);
	static int parseint(String& s, size_t radix);
	static Integer& valueOf(int l);
	static Integer& valueOf(String& s);
	static Integer& valueOf(String& s, size_t radix);
	static int highestOneBit(int i);
	static int lowestOneBit(int i);
	static size_t numberOfLeadingZeroes(int i);
	static size_t numberOfTrailingZeros(int i);
	static size_t bitCount(int i);
	static int rotateLeft(int i, size_t distance);
	static int rotateRight(int i, int distance);
	static int reverse(int i);
	static uint8_t signum(int i);
	static int reverseBytes(int i);
	static void getChars(int i, int index, char* buf);
	static String& stringFor(int i);
	static String& stringFor(int i, size_t radix);
	const static uint32_t SIZE = 64;
	const static unsigned int MAX_VALUE = 0x7fffffff, MIN_VALUE = 0x80000000;
	const static unsigned int UNSIGNED_MASK = 0xFFFFFFFF;
	const static unsigned int SIGNED_MASK = 0x7FFFFFFF;
protected:
	const static char* digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };
	const static char* DigitOnes = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1',
			'2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
			'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', };
	const static char* DigitTens = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1',
			'1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3',
			'3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5',
			'5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7',
			'7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
			'9', '9', '9', '9', '9', '9', '9', '9', '9', '9', };
};

#endif /* STD_LANG_INTEGER_HPP_ */
