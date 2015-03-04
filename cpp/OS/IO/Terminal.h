#ifndef TERMINAL_H_
#define TERMINAL_H_
/*
 * Terminal.h
 *
 *  Created on: Jan 30, 2015
 *      Author: wfeehery17
 */

#include "../std/x-stddef.h"
#include "../std/x-stdint.h"

class Terminal {
public:
	Terminal();
	Terminal(size_t width, size_t height);
	~Terminal();
	void initialize();
	void putChar(char c);
	void writeString(const char* data);
	void putEntryAt(char c, uint8_t color, size_t x, size_t y);
	void setColor(uint8_t color);
	static uint8_t makeColor(enum VGAColor fg, enum VGAColor bg);
	static uint16_t makeVGAEntry(char c, uint8_t color);
protected:
	size_t row;
	size_t column;
	uint8_t color;
	uint16_t* buffer;
	const size_t width;
	const size_t height;
};

#endif /* TERMINAL_H_ */
