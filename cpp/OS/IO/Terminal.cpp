/*
 * Terminal.cpp
 *
 *  Created on: Jan 30, 2015
 *      Author: wfeehery17
 */

#include "Terminal.h"

#include "../stdlib/strlen.h"
#include "VGAColor.h"

static const size_t VGA_WIDTH = 80;
static const size_t VGA_HEIGHT = 24;

Terminal::Terminal() :
		width(VGA_WIDTH), height(VGA_HEIGHT) {
	initialize();
}
Terminal::Terminal(size_t twidth, size_t theight) :
		width(twidth), height(theight) {
	initialize();
}
uint8_t Terminal::makeColor(enum VGAColor fg, enum VGAColor bg) {
	return fg | bg << 4;
}

uint16_t Terminal::makeVGAEntry(char c, uint8_t color) {
	uint16_t c16 = c;
	uint16_t color16 = color;
	return c16 | color16 << 8;
}

void Terminal::initialize() {
	row = 0;
	column = 0;
	color = makeColor(COLOR_LIGHT_GREY, COLOR_BLACK);
	buffer = (uint16_t*) 0xB8000;
	for (size_t y = 0; y < VGA_HEIGHT; y++) {
		for (size_t x = 0; x < VGA_WIDTH; x++) {
			const size_t index = y * VGA_WIDTH + x;
			Terminal::buffer[index] = makeVGAEntry(' ', color);
		}
	}
}

void Terminal::setColor(uint8_t color) {
	this->color = color;
}

void Terminal::putEntryAt(char c, uint8_t color, size_t x, size_t y) {
	const size_t index = y * VGA_WIDTH + x;
	Terminal::buffer[index] = makeVGAEntry(c, color);
}

void Terminal::putChar(char c) {
	putEntryAt(c, color, column, row);
	if (++column == VGA_WIDTH) {
		column = 0;
		if (++row == VGA_HEIGHT) {
			row = 0;
		}
	}
}

void Terminal::writeString(const char* data) {
	size_t datalen = strlen(data);
	for (size_t i = 0; i < datalen; i++)
		putChar(data[i]);
}
Terminal::~Terminal() {
	//do nothing
}
