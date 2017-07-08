#ifndef TERMINAL_H_
#define TERMINAL_H_
/*
 * Terminal.h
 *
 *  Created on: Jan 30, 2015
 *      Author: mailmindlin
 */

#include "../std/x-stddef.h"
#include "../std/x-stdint.h"
#include "VGAColor.h"
#include "Display/framebuffer.hpp"
class Terminal {
public:
	Terminal();
	Terminal(size_t width, size_t height);
	~Terminal();
	void initialize();
	void putChar(char c);
	void writeString(const char* data);
	void writeString(size_t offset, size_t length, const char* data);
	void putEntryAt(char c, uint16_t color, size_t x, size_t y);
	void setColor(uint8_t color);
	static uint8_t makeColor(enum VGAColor fg, enum VGAColor bg);
	static uint16_t makeVGAEntry(char c, uint8_t color);
	void draw();
protected:
	size_t row;
	size_t column;
	uint8_t color;
	char* buffer;
	uint16_t* colors;
	const size_t width;
	const size_t height;
	uint8_t chars[255][8];
	FrameBufferDescription* fb;
};

#endif /* TERMINAL_H_ */
