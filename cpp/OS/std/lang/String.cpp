/*
 * String.cpp
 *
 *  Created on: Feb 19, 2015
 *      Author: mailmindlin
 */

#include "String.h"

#include "../algorithm.h"
#include "../limits.h"
#include "../memory.h"
#include "../Math.h"
#include "../strlen.h"
#include "Double.h"
#include "Integer.h"
#include "Long.h"

String::String(const String& str) :
		size(str.length) {
	expand();
	for (size_t i = 0; i < str.length(); i++)
		this->data[i] = str.data[i];
}

String::String(const String& str, size_t pos, size_t len) :
		size(len) {
	expand();
	for (size_t i = 0; i < len; i++)
		if ((this->data[i] = str.data[i + pos]) == 0)
			break;
}

String::String(const char* s) :
		size(strlen(s)) {
	expand();
	for (size_t i = 0; i < length; i++)
		data[i] = s[i];
}

String::String(const char* s, size_t n) :
		size(n) {
	expand();
	for (size_t i = 0; i < n; i++)
		data[i] = s[i];
}

String::String(size_t n, char c) :
		size(n) {
	expand();
	for (size_t i = 0; i < length; i++)
		data[i] = c;
}

String::String(size_t length) :
		size(length) {
	expand();
}

String& String::operator =(const String& str) {
	if (str == this)
		return this;
	String * result = new String(str);
	delete this;
	return result;
}

String& String::operator =(const char* s) {
	String * result = new String(s);
	delete this;
	return result;
}

String& String::operator =(char c) {
	String * result = new String(1, c);
	delete this;
	return result;
}

String& String::operator +(const char* c) {
	return new String(c);
}
String& String::operator +(const String& str) {
	return concat(str);
}
bool String::isEmpty() const {
	return length() == 0;
}

char String::charAt(unsigned int index) {
	return data[index];
}

String::~String() {
	delete data;
}

String& String::concat(const char* c) {
	size_t len = strlen(c);
	String* result = new String(length() + len);
	for (size_t i = 0; i < length(); i++)
		result->data[i] = this->data[i];
	for (size_t i = 0; i < len; i++)
		result->data[i + length()] = c[i];
	return result;
}

String& String::concat(const String& str) {
	String* result = new String(length() + str.length());
	for (size_t i = 0; i < length(); i++)
		result->data[i] = this->data[i];
	for (size_t i = 0; i < str.length(); i++)
		result->data[i + length()] = str.data[i];
	return result;
}

bool String::contains(const char* c) const {
	return indexOf(c) != SIZE_T_MAX;
}

bool String::contains(String& str) const {
	return indexOf(str) != SIZE_T_MAX;
}

bool String::endsWith(String& suffix) const {
	return indexOf(suffix) == length() - suffix.length();
}

bool String::equals(Object o) const {
	if (o == this)
		return true;
	if (!o.instanceof<String>())
		return false;
	String s = dynamic_cast<String>(o);
	if (s.length() != length())
		return false;
	for (size_t i = 0; i < length(); i++)
		if (s.data[i] != data[i])
			return false;
	return true;
}

bool String::equalsIgnoreCase(String& str) const {
	if (str == this)
		return true;
	if (str.length() != length())
		return false;
	return str.toLowerCase().equals(toLowerCase());
}

void String::getChars(size_t srcBegin, size_t srcEnd, char* dst, size_t dstBegin) const {
	const size_t delta = dstBegin - srcBegin;
	for (size_t i = srcBegin; i < srcEnd; i++)
		dst[i] = data[i + delta];
}

size_t String::indexOf(char c) const {
	return indexOf(c, 0);
}

size_t String::indexOf(char c, size_t start) const {
	for (size_t i = 0; i < length(); i++)
		if (data[i] == c)
			return i;
	return SIZE_T_MAX;
}

size_t String::indexOf(String& str) const {
	return indexOf(str, 0);
}

size_t String::indexOf(String& str, size_t start) const {
	const size_t len = str.length();
	for (size_t i = start; i < length() - len; i++) {
		bool match = true;
		for (size_t j = 0; match && j < len; j++)
			match = (data[i + j] == str.data[j]) && match;
		if (match)
			return i;
	}
	return SIZE_T_MAX;
}

size_t String::indexOf(const char* c) const {
	return indexOf(c, 0);
}

size_t String::indexOf(const char* c, size_t start) const {
	const size_t len = strlen(c);
	for (size_t i = start; i < length() - len; i++) {
		bool match = true;
		for (size_t j = 0; match && j < len; j++)
			match = (data[i + j] == c[j]) && match;
		if (match)
			return i;
	}
	return SIZE_T_MAX;
}

size_t String::lastIndexOf(char c) {
	return lastIndexOf(c, length());
}

size_t String::lastIndexOf(char c, size_t fromIndex) {
	for (size_t i = fromIndex - 1; i > 0; i--)
		if (data[i] == c)
			return i;
	return SIZE_T_MAX;
}

size_t String::lastIndexOf(String& str) {
	return this->lastIndexOf(str, length());
}

size_t String::lastIndexOf(String& str, size_t fromIndex) {
	for (size_t i = Math::min(fromIndex, length() - str.length()); i > 0; i--) {
		bool match = true;
		for (size_t j = 0; match && j < str.length(); j++)
			match = (data[i + j] == str.data[j]) && match;
		if (match)
			return i;
	}
	return SIZE_T_MAX;
}

size_t String::length() const {
	return size;
}

bool String::regionMatches(bool ignoreCase, size_t toffset, String& other, size_t oofset, size_t len) {
	if (ignoreCase)
		return substring(toffset, toffset + len).equalsIgnoreCase(other.substring(oofset, oofset + len));
	else
		return substring(toffset, toffset + len).equals(other.substring(oofset, oofset + len));
}

String& String::replace(char oldChar, char newChar) {
	String * result = new String(length());
	for (size_t i = 0; i < length(); i++)
		result->data[i] = (data[i] == oldChar ? newChar : data[i]);
	return result;
}

String& String::replace(const char* target, const char* replacement) {
	String * result = this;
	const size_t targlen = strlen(target);
	const size_t repllen = strlen(replacement);
	int index;
	while ((index = result->indexOf(target)) > -1) {
		String*tmp = result;
		result = tmp->substring(0, index) + target + tmp->substring(index + repllen, tmp->length());
		delete tmp;
	}
	if (result == this)
		result = new String(this);
	return result;
}

String& String::replace(String& target, String& replacement) {
	String * result = this;
	const size_t targlen = target.length();
	const size_t repllen = replacement.length();
	int index;
	while ((index = result->indexOf(target)) > -1) {
		String*tmp = result;
		result = tmp->substring(0, index) + target + tmp->substring(index + repllen, tmp->length());
		delete tmp;
	}
	if (result == this)
		result = new String(this);
	return result;
}

bool String::startsWith(String& prefix) {
	return indexOf(prefix) == 0;
}

bool String::startsWith(String& prefix, size_t toffset) {
	return this->regionMatches(false, toffset, prefix, 0, prefix.length());
}

char* String::subSequence(size_t beginIndex, size_t endIndex) {
	return substring(beginIndex, endIndex).toCharArray();
}

String& String::substring(size_t beginIndex) {
	return substring(beginIndex, length());
}

String& String::substring(size_t beginIndex, size_t endIndex) {
	String* result = new String(endIndex - beginIndex);
	for (size_t i = beginIndex; i < endIndex; i++)
		result->data[i - beginIndex] = data[i];
	return result;
}

char* String::toCharArray() const {
	char* result = new char[length()];
	Array::copy(this->data, result, 0, 0, length());
	return result;
}

String& String::toLowerCase() const {
	String* result = new String(length());
	for (size_t i = 0; i < length(); i++)
		result->data[i] = std::toLower(data[i]);
	return result;
}

String& String::toString() const {
	return &*this;
}

String& String::toUpperCase() const {
	String* result = new String(length());
	for (size_t i = 0; i < length(); i++)
		result->data[i] = std::toUpper(data[i]);
	return result;
}

String& String::trim() const {
	size_t b = 0, e = 0;
	for (b = 0; b < length() && (data[b] == ' ' || data[b] == '\t' || data[b] == '\n'); b++) {
	}
	for (e = length() - 1; e > b && (data[e] == ' ' || data[e] == '\t' || data[e] == '\n'); e--) {
	}
	String* result = new String(e - b);
	Array::copy(data, result->data, b, 0, e - b);
	return result;
}

String& String::valueOf(bool b) {
	return new String(b ? "true" : "false");
}

String& String::valueOf(char c) {
	return new String(c, 1);
}

String& String::valueOf(const char* c) {
	return new String(c);
}

String& String::valueOf(char* data, size_t offset, size_t count) {
	return new String(data, offset, count);
}

String& String::valueOf(double d, size_t radix) {
	return Double::stringFor(d, radix);
}
String& String::valueOf(double d) {
	return Double::stringFor(d);
}

String& String::valueOf(float f) {
	return &nullptr; //TODO finish
}

String& String::valueOf(int i, size_t radix) {
	return Integer::stringFor(i, radix);
}

String& String::valueOf(long l, size_t radix) {
	return Long::stringFor(l, radix);
}
String& String::valueOfPtr(const void* ptr) {
	return valueOf((int) ptr, 16);
}

String& String::valueOf(Object& obj) {
	return obj.toString();
}

void String::expand() {
	data = new char[size];
}
