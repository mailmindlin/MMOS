/*
 * Long.cpp
 *
 *  Created on: Feb 23, 2015
 *      Author: wfeehery17
 */

#include "../../std/lang/Long.hpp"

#include "../../std/exceptions/NumberFormatException.h"
#include "../../std/lang/Byte.hpp"
#include "../../std/lang/Character.hpp"
#include "../../std/lang/Integer.hpp"
#include "../../std/lang/Object.hpp"
#include "../../std/lang/Short.hpp"

Long::Long(long l) :
		value(l) {
}

Long::Long(String& s) :
		value(parseLong(s, 10)) {
}

Long::~Long() {

}

uint8_t Long::byteValue() const {
	return (uint8_t) value & 0xFF;
}

short Long::shortValue() const {
	return (short) value & 0xFFFF;
}

int Long::intValue() const {
	return (int) value & 0xFFFFFFFF;
}

float Long::floatValue() const {
	return value / 1.f;
}

double Long::doubleValue() const {
	return value / 1.0;
}
long Long::longValue() const {
	return value & UNSIGNED_MASK;
}

String& Long::toString() {
	return String::valueOf(value);
}

uint32_t Long::hashCode() {
	return (uint32_t) (value ^ (value >> 32));
}

bool Long::equals(Object& other) {
	if (other.instanceof<Long>()) {
		return value == ((Long&) other).longValue();
	}
	return false;
}

int Long::compareTo(Long& anotherLong) {
	long thisVal = this->value;
	long anotherVal = anotherLong.value;
	return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
}

Long& Long::valueOf(long l) {
	return new Long(l);
}

Long& Long::valueOf(String& s) {
	return new Long(s);
}

Long& Long::valueOf(String& s, size_t radix) {
	return new Long(parseLong(s, radix));
}

long Long::highestOneBit(long i) {
	// HD, Figure 3-1

	i |= ((i & SIGNED_MASK) >> 1);
	i |= ((i & SIGNED_MASK) >> 2);
	i |= ((i & SIGNED_MASK) >> 4);
	i |= ((i & SIGNED_MASK) >> 8);
	i |= ((i & SIGNED_MASK) >> 16);
	i |= ((i & SIGNED_MASK) >> 32);
	return i - (i >> 1);
}

long Long::lowestOneBit(long i) {
	// HD, Section 2-1
	return i & -i;
}

size_t Long::numberOfLeadingZeroes(long i) { // HD, Figure 5-6
	if (i == 0)
		return 64;
	int n = 1;
	int x = (int) (i >> 32);
	if (x == 0) {
		n += 32;
		x = (int) i;
	}
	if (x >> 16 == 0) {
		n += 16;
		x <<= 16;
	}
	if (x >> 24 == 0) {
		n += 8;
		x <<= 8;
	}
	if (x >> 28 == 0) {
		n += 4;
		x <<= 4;
	}
	if (x >> 30 == 0) {
		n += 2;
		x <<= 2;
	}
	n -= x >> 31;
	return n;
}

size_t Long::numberOfTrailingZeros(long i) {
	// HD, Figure 5-14
	int x, y;
	if (i == 0)
		return 64;
	int n = 63;
	y = (int) i;
	if (y != 0) {
		n = n - 32;
		x = y;
	} else
		x = (int) (i >> 32);
	y = x << 16;
	if (y != 0) {
		n = n - 16;
		x = y;
	}
	y = x << 8;
	if (y != 0) {
		n = n - 8;
		x = y;
	}
	y = x << 4;
	if (y != 0) {
		n = n - 4;
		x = y;
	}
	y = x << 2;
	if (y != 0) {
		n = n - 2;
		x = y;
	}
	return n - ((x << 1) >> 31);
}

size_t Long::bitCount(long i) {
	// HD, Figure 5-14
	i = i - ((i >> 1) & 0x5555555555555555L);
	i = (i & 0x3333333333333333L) + ((i >> 2) & 0x3333333333333333L);
	i = (i + (i >> 4)) & 0x0f0f0f0f0f0f0f0fL;
	i = i + (i >> 8);
	i = i + (i >> 16);
	i = i + (i >> 32);
	return (int) i & 0x7f;
}

long Long::rotateLeft(long i, size_t distance) {
}

long Long::rotateRight(long i, int distance) {
}

long Long::reverse(long i) {
// HD, Figure 7-1
	i = (i & 0x5555555555555555L) << 1 | ((i >> 1) & 0x5555555555555555L);
	i = (i & 0x3333333333333333L) << 2 | ((i >> 2) & 0x3333333333333333L);
	i = (i & 0x0f0f0f0f0f0f0f0fL) << 4 | ((i >> 4) & 0x0f0f0f0f0f0f0f0fL);
	i = (i & 0x00ff00ff00ff00ffL) << 8 | ((i >> 8) & 0x00ff00ff00ff00ffL);
	i = (i << 48) | ((i & 0xffff0000L) << 16) | ((i >> 16) & 0xffff0000L) | (i >> 48);
	return i;
}

uint8_t Long::signum(long i) {
	return (uint8_t) (((i & SIGNED_MASK) >> 63) | (-i >> 63));
}

long Long::parseLong(String& s, size_t radix) {
	if (s == NULL)
		throw new NumberFormatException("null");
	if (radix < Character::MIN_RADIX)
		throw new NumberFormatException("radix " + radix + " less than Character.MIN_RADIX");
	if (radix > Character::MAX_RADIX)
		throw new NumberFormatException("radix " + radix + " greater than Character.MAX_RADIX");
	long result = 0;
	bool negative = false;
	int i = 0, len = s.length();
	long limit = -Long::MAX_VALUE;
	long multmin;
	int digit;

	if (len > 0) {
		char firstChar = s.charAt(0);
		if (firstChar < '0') { // Possible leading "-"
			if (firstChar == '-') {
				negative = true;
				limit = Long::MIN_VALUE;
			} else
				throw NumberFormatException::forInputString(s);

			if (len == 1) // Cannot have lone "-"
				throw NumberFormatException::forInputString(s);
			i++;
		}
		multmin = limit / radix;
		while (i < len) {
			// Accumulating negatively avoids surprises near MAX_VALUE
			digit = Character::digit(s.charAt(i++), radix);
			if (digit < 0 || result < multmin)
				throw NumberFormatException::forInputString(s);

			result *= radix;
			if (result < limit + digit) {
				throw NumberFormatException::forInputString(s);
			}
			result -= digit;
		}
	} else {
		throw NumberFormatException::forInputString(s);
	}
	return negative ? result : -result;
}

long Long::reverseBytes(long i) {
	i = (i & 0x00ff00ff00ff00ffL) << 8 | ((i >> 8) & 0x00ff00ff00ff00ffL);
	return (i << 48) | ((i & 0xffff0000L) << 16) | ((i >> 16) & 0xffff0000L) | (i >> 48);
}

Long& Long::operator =(int n) {
	long cv = n & Integer::SIGNED_MASK;
	if (n < 0)
		cv = -cv;
	delete this;
	return new Long(cv);
}

Long& Long::operator =(short n) {
	long cv = n & Short::SIGNED_MASK;
	if (n < 0)
		cv = -cv;
	delete this;
	return new Long(cv);
}

Long& Long::operator =(uint8_t n) {
	long cv = n & Byte::SIGNED_MASK;
	if (n < 0)
		cv = -cv;
	delete this;
	return new Long(cv);
}

Long& Long::operator +=(int n) {
	Number*i = new Integer(i);
	Long* result = operator+=((Number&) i);
	delete i;
	return result;
}

Long& Long::operator +=(short n) {
	Number*i = new Short(i);
	Long* result = operator+=((Number&) i);
	delete i;
	return result;
}

Long& Long::operator +=(uint8_t n) {
	Number*i = new Byte(i);
	Long* result = operator+=((Number&) i);
	delete i;
	return result;
}

size_t Long::stringSize(long x) {
	long p = 10;
	for (int i = 1; i < 19; i++) {
		if (x < p)
			return i;
		p *= 10;
	}
	return 19;
}

Long& Long::operator +(Number& n) const {
	return new Long(value + n.longValue());
}

Long& Long::operator +(long n) const {
	return new Long(value + n);
}

Long& Long::operator +(int n) const {
	return operator+((Number&) new Integer(n));
}

Long& Long::operator +(short n) const {
	return operator+((Number&) new Short(n));
}

Long& Long::operator +(uint8_t n) const {
	return operator+((Number&) new Byte(n));
}

Long& Long::operator -(Number& n) const {
	return new Long(value - n.longValue());
}

Long& Long::operator -(long n) const {
	return new Long(value - n);
}

Long& Long::operator -(int n) const {
	return operator-((Number&) new Integer(n));
}

Long& Long::operator -(short n) const {
	return operator-((Number&) new Short(n));
}

Long& Long::operator -(uint8_t n) const {
	return operator-((Number&) new Byte(n));
}

Long& Long::operator *(Number& n) const {
	return new Long(value * n.longValue());
}

Long& Long::operator *(long n) const {
	return new Long(value * n);
}

Long& Long::operator *(int n) const {
	return operator*((Number&) new Integer(n));
}

Long& Long::operator *(short n) const {
	return operator*((Number&) new Short(n));
}

Long& Long::operator *(uint8_t n) const {
	return operator*((Number&) new Byte(n));
}

Long& Long::operator /(Number& n) const {
	return new Long(value * n.longValue());
}

Long& Long::operator /(long n) const {
	return new Long(value / n);
}

Long& Long::operator /(int n) const {
	return operator/((Number&) new Integer(n));
}

Long& Long::operator /(short n) const {
	return operator/((Number&) new Short(n));
}

Long& Long::operator /(uint8_t n) const {
	return operator/((Number&) new Byte(n));
}

Long& Long::operator =(Number& n) {
	Long* result = new Long(n.longValue());
	delete this;
	return result;
}

Long& Long::operator =(long n) {
	Long* result = new Long(n);
	delete this;
	return n;
}

Long& Long::operator +=(Number& n) {
	Long* result = operator+(n);
	delete this;
	return result;
}

Long& Long::operator +=(long n) {
	Long* result = new Long(value + n);
	delete this;
	return result;
}
void Long::getChars(long i, int index, char* buf) {
	long q;
	int r;
	int charPos = index;
	char sign = 0;

	if (i < 0) {
		sign = '-';
		i = -i;
	}

	// Get 2 digits/iteration using longs until quotient fits into an int
	while (i > Integer::MAX_VALUE) {
		q = i / 100;
		// really: r = i - (q * 100);
		r = (int) (i - ((q << 6) + (q << 5) + (q << 2)));
		i = q;
		buf[--charPos] = Integer::DigitOnes[r];
		buf[--charPos] = Integer::DigitTens[r];
	}

	// Get 2 digits/iteration using ints
	int q2;
	int i2 = (int) i;
	while (i2 >= 65536) {
		q2 = i2 / 100;
		// really: r = i2 - (q * 100);
		r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
		i2 = q2;
		buf[--charPos] = Integer::DigitOnes[r];
		buf[--charPos] = Integer::DigitTens[r];
	}

	// Fall thru to fast mode for smaller numbers
	// assert(i2 <= 65536, i2);
	for (;;) {
		q2 = (i2 * 52429) >> (16 + 3);
		r = i2 - ((q2 << 3) + (q2 << 1));  // r = i2-(q2*10) ...
		buf[--charPos] = Integer::digits[r];
		i2 = q2;
		if (i2 == 0)
			break;
	}
	if (sign != 0) {
		buf[--charPos] = sign;
	}
}

// Requires positive x
size_t Long::stringSize(long x) {
	long p = 10;
	for (int i = 1; i < 19; i++) {
		if (x < p)
			return i;
		p = 10 * p;
	}
	return 19;
}
String& Long::stringFor(long i) {
	if (i == Long::MIN_VALUE)
		return "-9223372036854775808";
	int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
	char* buf = new char[size];
	getChars(i, size, buf);
	return new String(0, size, buf);
}
String& Long::stringFor(long i, size_t radix) {
	if (radix < Character::MIN_RADIX || radix > Character::MAX_RADIX)
		radix = 10;
	if (radix == 10)
		return stringFor(i);
	char* buf = new char[65];
	int charPos = 64;
	bool negative = (i < 0);

	if (!negative) {
		i = -i;
	}

	while (i <= -radix) {
		buf[charPos--] = Integer::digits[(int) (-(i % radix))];
		i = i / radix;
	}
	buf[charPos] = Integer::digits[(int) (-i)];

	if (negative) {
		buf[--charPos] = '-';
	}

	return new String(buf, charPos, (65 - charPos));
}
