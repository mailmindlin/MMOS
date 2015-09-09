/*
 * MAJORminorVersion.h
 *
 *  Created on: Mar 4, 2015
 *      Author: mailmindlin
 */

#ifndef STD_LANG_MAJORMINORVERSION_H_
#define STD_LANG_MAJORMINORVERSION_H_

#include "Object.hpp"

class MAJORminorVersion : public Object{
public:
	MAJORminorVersion(uint32_t MAJOR,uint32_t minor);
	virtual ~MAJORminorVersion();
	String& toString();
	bool operator==(MAJORminorVersion& other);
protected:
	const uint32_t MAJOR,minor;
};

#endif /* STD_LANG_MAJORMINORVERSION_H_ */
