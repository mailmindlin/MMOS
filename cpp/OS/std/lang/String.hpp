
/*
 * String.h
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef STDLIB_STRING_HPP_
#define STDLIB_STRING_HPP_

#include "../x-stddef.h"
#include "Object.hpp"

class String : Object {
public:
	String(const String& str);
	String(const String& str, size_t pos, size_t len = npos);
	String(const char* s);
	String(const char* s, size_t n);
	String(size_t n, char c);
	String& operator=(const String& str);
	String& operator=(const char* s);
	String& operator=(char c);
	String& operator+(const char* c);
	String& operator+(const String& str);
	~String();
	static const size_t npos = -1;
	//some java functions
	/**
	 * Returns the character at the given index of this string
	 * @param index to get character from
	 * @return character at index
	 * @throws IndexOutOfBoundsException if {@code index>size()}
	 */
	char charAt(unsigned int index);
	/**
	 * Creates another string that contains the characters in this and {@code c}
	 * @param c string to add
	 * @return new string from concatenating this and {@code c}
	 */
	String& concat(const char* c);
	/**
	 * Creates another string that contains the characters in this and {@code str}
	 * @param str string to add
	 * @return new string from concatenating this and {@code str}
	 */
	String& concat(const String& str);
	/**
	 * Checks if {@code this.indexOf(str)>-1}
	 * @param c string to find
	 * @return if this contains {@code str}
	 */
	bool contains(const char* c) const;
	/**
	 * Checks if {@code this.indexOf(str)>-1}
	 * @param str string to find
	 * @return if this contains {@code str}
	 */
	bool contains(String& str) const;
	/**
	 * Tests if this string ends with the specified suffix.
	 * @param suffix to end with
	 * @return if it ends with {@code suffix}
	 */
	bool endsWith(String& suffix) const;
	bool equals(Object o) const;
	bool equalsIgnoreCase(String& str) const;
	void getChars(size_t srcBegin, size_t srcEnd, char* dst, size_t dstBegin) const;
	size_t indexOf(char c) const;
	size_t indexOf(char c, size_t start) const;
	size_t indexOf(String& str) const;
	size_t indexOf(String& str, size_t start) const;
	size_t indexOf(const char* c) const;
	size_t indexOf(const char* c, size_t start) const;
	bool isEmpty() const;
	size_t lastIndexOf(char c);
	size_t lastIndexOf(char c, size_t fromIndex);
	size_t lastIndexOf(String& str);
	size_t lastIndexOf(String& str, size_t fromIndex);
	size_t length() const;
	bool regionMatches(bool ignoreCase, size_t toffset, String& other, size_t oofset, size_t len);
	bool regionMatches(size_t toffset, String other, size_t ooffset, size_t len);
	String& replace(char oldChar, char newChar);
	String& replace(const char* target, const char* replacement);
	String& replace(String& target, String& replacement);
	bool startsWith(String& prefix);
	bool startsWith(String& prefix, size_t toffset);
	char* subSequence(size_t beginIndex, size_t endIndex);
	String& substring(size_t beginIndex);
	String& substring(size_t beginIndex, size_t endIndex);
	char* toCharArray() const;
	String& toLowerCase() const;
	String& toString() const;
	String& toUpperCase() const;
	String& trim() const;
	static String& valueOf(bool b);
	static String& valueOf(char c);
	static String& valueOf(const char* c);
	static String& valueOf(char* data, size_t offset, size_t count);
	static String& valueOf(double d);
	static String& valueOf(double d, size_t radix);
	static String& valueOf(float f);
	static String& valueOf(int i, size_t radix);
	static String& valueOf(long l);
	static String& valueOf(long l, size_t radix);
	static String& valueOf(Object& obj);
	static String& valueOfPtr(const void* ptr);
protected:
	String(size_t length);
	void expand();
	const size_t size;
	char* data;
	static constexpr const char* const digits = "012345678989abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+=";
};

#endif /* STDLIB_STRING_HPP_ */
