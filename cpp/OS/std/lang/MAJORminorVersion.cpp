/*
 * MAJORminorVersion.cpp
 *
 *  Created on: Mar 4, 2015
 *      Author: mailmindlin
 */

#include "MAJORminorVersion.h"

MAJORminorVersion::MAJORminorVersion(uint32_t MAJOR,uint32_t minor): MAJOR(MAJOR),minor(minor) {

}

MAJORminorVersion::~MAJORminorVersion() {
}

String& MAJORminorVersion::toString() {
	StringBuffer* sb = new StringBuffer();
	sb<<MAJOR<<'.'<<minor;
	String& s = sb->toString();
	delete sb;
	return s;
}

bool MAJORminorVersion::operator ==(MAJORminorVersion& other) {
	return MAJOR==other.MAJOR && minor==other.minor;
}
