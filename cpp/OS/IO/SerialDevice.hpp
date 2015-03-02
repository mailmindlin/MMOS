/*
 * SerialDevice.h
 *
 *  Created on: Feb 27, 2015
 *      Author: wfeehery17
 */

#ifndef IO_SERIALDEVICE_HPP_
#define IO_SERIALDEVICE_HPP_

#include <string.h>

#include <x-stdarg.h>
#include <x-stddef.h>
#include <x-stdint.h>

class SerialDevice {
public:
	virtual ~SerialDevice();
	virtual void close();
	virtual void flush();
	virtual void print(const unsigned char c);
	virtual void print(const unsigned char* c);
	virtual void printf(const char *message, ...) {
		va_list argp;
		unsigned char buffer[1024];

		va_start(argp, message);
		vsnprintf(buffer, 1023L, message, argp);
		va_end(argp);
		print(buffer);
	}
	virtual void println(const unsigned char* c) {
		print(c);
		print('\n');
	}
	virtual SerialDevice& operator<<(const unsigned char c) {
		print(c);
		return this;
	}
	virtual SerialDevice& operator<<(const unsigned char* c) {
		print(c);
		return this;
	}
	virtual uint32_t available();
	virtual int16_t read();
	virtual uint32_t read(uint8_t* buffer) {
		return read(buffer, 0, available());
	}
	virtual uint32_t read(uint8_t* buffer, size_t offset, size_t pos) {
		size_t i;
		for (i = 0; i < offset; i++) {
			int16_t chr = read();
			if (chr == -1)
				return i;
			buffer[pos + i] = (uint8_t) chr;
		}
		return i;
	}

};
#endif /* IO_SERIALDEVICE_HPP_ */
