/*
 * Double.h
 *
 *  Created on: Feb 23, 2015
 *      Author: wfeehery17
 */

#ifndef STD_LANG_DOUBLE_HPP_
#define STD_LANG_DOUBLE_HPP_

#include <x-stddef.h>
#include <x-stdint.h>
#include "Number.hpp"
#include "String.hpp"

class Double: public Number, public Comparable {
public:
	Double();
	~Double();
	String& toString();
	uint32_t hashCode();
	bool equals(Object& other);
	int compareTo(Double& anotherdouble);
	virtual uint8_t byteValue() const;
	virtual int intValue() const;
	virtual double doubleValue() const;
	virtual short shortValue() const;
	virtual float floatValue() const;
	virtual double doubleValue() const;
	Double& operator+(Number& n) const;
	Double& operator+(double n) const;
	Double& operator+(int n) const;
	Double& operator+(short n) const;
	Double& operator+(uint8_t n) const;
	Double& operator-(Number& n) const;
	Double& operator-(double n) const;
	Double& operator-(int n) const;
	Double& operator-(short n) const;
	Double& operator-(uint8_t n) const;
	Double& operator*(Number& n) const;
	Double& operator*(double n) const;
	Double& operator*(int n) const;
	Double& operator*(short n) const;
	Double& operator*(uint8_t n) const;
	Double& operator/(Number& n) const;
	Double& operator/(double n) const;
	Double& operator/(int n) const;
	Double& operator/(short n) const;
	Double& operator/(uint8_t n) const;
	Double& operator=(Number& n);
	Double& operator=(double n);
	Double& operator=(int n);
	Double& operator=(short n);
	Double& operator=(uint8_t n);
	Double& operator+=(Number& n);
	Double& operator+=(double n);
	Double& operator+=(int n);
	Double& operator+=(short n);
	Double& operator+=(uint8_t n);
	static double parsedouble(String& s, size_t radix);
	static Double& valueOf(double l);
	static Double& valueOf(String& s);
	static Double& valueOf(String& s, size_t radix);
	static double highestOneBit(double i);
	static double lowestOneBit(double i);
	static size_t numberOfLeadingZeroes(double i);
	static size_t numberOfTrailingZeros(double i);
	static size_t bitCount(double i);
	static double rotateLeft(double i, size_t distance);
	static double rotateRight(double i, int distance);
	static double reverse(double i);
	static uint8_t signum(double i);
	static double reverseBytes(double i);
	static void getChars(double i, int index, char* buf);
	static String& stringFor(double i);
	static String& stringFor(double i, size_t radix);
protected:
	const double value;
};

#endif /* STD_LANG_DOUBLE_HPP_ */
