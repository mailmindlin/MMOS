/*
 * StringBuffer.h
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef STD_UTIL_STRINGBUFFER_HPP_
#define STD_UTIL_STRINGBUFFER_HPP_

#include <x-stddef.h>

#include "../lang/String.hpp"
#include "LinkedList.hpp"

class StringBuffer: public Object {
public:
	StringBuffer();
	StringBuffer(String& str);
	~StringBuffer();
	String& toString();
	void append(String& str);
	void append(const char* str);
	void append(String& s);
	const char* toCharArray();
	StringBuffer& operator<<(String& str);
	StringBuffer& operator<<(const char* c);
	StringBuffer& operator<<(bool c);
	StringBuffer& operator<<(char c);
	StringBuffer& operator<<(int c);
	StringBuffer& operator<<(long c);
	size_t length();
protected:
	LinkedList<String> * buff;
};

#endif /* STD_UTIL_STRINGBUFFER_HPP_ */
