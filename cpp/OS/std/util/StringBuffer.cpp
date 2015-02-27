/*
 * StringBuffer.cpp
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#include "../../std/util/StringBuffer.h"

#include "../../std/util/Collection.hpp"
#include "../../std/util/Iterator.hpp"
#include "../memory.h"

LinkedList<String> * StringBuffer::buff;
StringBuffer::StringBuffer() {
	buff = new LinkedList<String>();
}

StringBuffer::StringBuffer(String& str) {
	buff = new LinkedList<String>();
	append(str);
}

StringBuffer::~StringBuffer() {
	delete buff;
}

String& StringBuffer::toString() {
	String * result = new String(length);
	size_t index = 0;
	Iterator<String> * i = buff->iterator();
	while (i->hasNext()) {
		String* s = i->next();
		Array::copy(s->data, result->data, 0, index, s->length());
		index += s->length();
	}
	return result;
}

StringBuffer& StringBuffer::operator <<(String& str) {
	append(str);
	return this;
}

StringBuffer& StringBuffer::operator <<(const char* c) {
	append(c);
	return this;
}
StringBuffer& StringBuffer::operator <<(bool b) {
	append(String::valueOf(b));
	return this;
}

void StringBuffer::append(String& str) {
	buff->addLast(&str);
}

void StringBuffer::append(const char* c) {
	buff->addLast(new String(c));
}
void StringBuffer::append(String* s) {
	buff->addLast(s);
}

StringBuffer& StringBuffer::operator <<(char c) {
	buff->addLast(String::valueOf(c));
	return this;
}

StringBuffer& StringBuffer::operator <<(int i) {
	buff->addLast(String::valueOf(i, 10));
	return this;
}

StringBuffer& StringBuffer::operator <<(long l) {
	buff->addLast(String::valueOf(l, 10));
	return this;
}

size_t StringBuffer::length() {
	size_t result = 0;
	Iterator<String> * i = buff->iterator();
	while (i->hasNext())
		result += i->next()->length();
	return result;
}
const char* StringBuffer::toCharArray() {
	String tmp = toString();
	const char* result = tmp.toCharArray();
	delete tmp;
	return result;
}
}
