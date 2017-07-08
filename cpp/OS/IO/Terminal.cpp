/*
 * Terminal.cpp
 *
 *  Created on: Jan 30, 2015
 *      Author: mailmindlin
 */

#include "Terminal.h"

#include "../std/strlen.h"

struct FrameBufferDescription;

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
	for(int i = 0; i < 8; i++)
		chars[0][i]=0;
	//'A'
	chars[65][0] =	0b00000000;
	chars[65][1] =	0b00111000;
	chars[65][2] =	0b01101100;
	chars[65][3] =	0b11000110;
	chars[65][4] =	0b11000110;
	chars[65][5] =	0b11111110;
	chars[65][6] =	0b11000110;
	chars[65][7] =	0b11000110;
	//'B'
	chars[66][0] =	0b00000000;
	chars[66][1] =	0b11111100;
	chars[66][2] =	0b11000110;
	chars[66][3] =	0b11000110;
	chars[66][4] =	0b11111100;
	chars[66][5] =	0b11000110;
	chars[66][6] =	0b11000110;
	chars[66][7] =	0b11111110;
	//'C'
	chars[67][0] =	0b00000000;
	chars[67][1] =	0b00111100;
	chars[67][2] =	0b01100110;
	chars[67][3] =	0b11000000;
	chars[67][4] =	0b11000000;
	chars[67][5] =	0b11000000;
	chars[67][6] =	0b01100110;
	chars[67][7] =	0b00111100;
	//'D'
	chars[68][0] =	0b00000000;
	chars[68][1] =	0b11111000;
	chars[68][2] =	0b11001100;
	chars[68][3] =	0b11000110;
	chars[68][4] =	0b11000110;
	chars[68][5] =	0b11000110;
	chars[68][6] =	0b11001100;
	chars[68][7] =	0b11111000;
	//'E'
	chars[69][0] =	0b00000000;
	chars[69][1] =	0b11111110;
	chars[69][2] =	0b11000000;
	chars[69][3] =	0b11000000;
	chars[69][4] =	0b11111000;
	chars[69][5] =	0b11000000;
	chars[69][6] =	0b11000000;
	chars[69][7] =	0b11111110;
	//'F'
	chars[70][0] =	0b00000000;
	chars[70][1] =	0b11111110;
	chars[70][2] =	0b11000000;
	chars[70][3] =	0b11000000;
	chars[70][4] =	0b11111100;
	chars[70][5] =	0b11000000;
	chars[70][6] =	0b11000000;
	chars[70][7] =	0b11000000;
	//'G'
	chars[71][0] =	0b00000000;
	chars[71][1] =	0b00111100;
	chars[71][2] =	0b01100110;
	chars[71][3] =	0b11000000;
	chars[71][4] =	0b11001110;
	chars[71][5] =	0b11000110;
	chars[71][6] =	0b01100110;
	chars[71][7] =	0b00111100;
	//'H'
	chars[72][0] =	0b00000000;
	chars[72][1] =	0b11000110;
	chars[72][2] =	0b11000110;
	chars[72][3] =	0b11000110;
	chars[72][4] =	0b11111110;
	chars[72][5] =	0b11000110;
	chars[72][6] =	0b11000110;
	chars[72][7] =	0b11000110;
	//'I'
	chars[73][0] =	0b00000000;
	chars[73][1] =	0b00011000;
	chars[73][2] =	0b00011000;
	chars[73][3] =	0b00011000;
	chars[73][4] =	0b00011000;
	chars[73][5] =	0b00011000;
	chars[73][6] =	0b00011000;
	chars[73][7] =	0b00011000;
	
	//'J'
	/*
	chars[7][0] =	0b;
	chars[7][1] =	0b;
	chars[7][2] =	0b;
	chars[7][3] =	0b;
	chars[7][4] =	0b;
	chars[7][5] =	0b;
	chars[7][6] =	0b;
	chars[7][7] =	0b;
	*/
}

void drawCharAt(uint32_t x, uint32_t y, FrameBufferDescription* buffer, uint8_t* data, uint16_t color) {
	for(int i = 0; i < 8; i++)
		for(int j = 0; j < 8; j++)
			if((data[i] >> j) & 1 == 0)
				Displays::GPU::setPixel(buffer, x + i, y + j, color);
}

void Terminal::draw() {
	int x = 10, y = 10;
	for(int i = 0; i < VGA_WIDTH; i++)
		for(int j = 0; j < VGA_HEIGHT; j++) {
			char c = buffer[i * j];
			uint8_t* g = chars[c];
			drawCharAt(x, y, fb, g, colors[i * j]);
			if((x += 8) > fb->vWidth) {
				x = 0;
				y += 8;
			}
		}
}

void Terminal::setColor(uint8_t color) {
	this->color = color;
}

void Terminal::putEntryAt(char c, uint16_t color, size_t x, size_t y) {
	const size_t index = y * VGA_WIDTH + x;
	Terminal::buffer[index] = c;
	Terminal::colors[index] = color;
}

void Terminal::putChar(char c) {
	if(c == '\n') {
		row++;
		column=0;
		//clear row
		for(int i = 0; i < VGA_WIDTH; i++)
			colors[i*row]=buffer[i*row]='\0';
	} else if(c=='\t') {
		column+=4;
	} else if(c<32) {

	}else if(c==32) {
		column++;
	}else if(c==127) {
		column--;
		buffer[row*column]=0;
	} else {
		putEntryAt(c, color, column, row);
		column++;
	}
	if (column == VGA_WIDTH) {
		column = 0;
		if (++row == VGA_HEIGHT) {
			row = 0;
		}
	}
}

void Terminal::writeString(const char* data) {
	char c;
	while (c = *data++)
		putChar(c);
}

void Terminal::writeString(size_t off, size_t len, const char* data) {
	for (size_t i = 0; i < len; i++)
		putChar(data[i + off]);
}

Terminal::~Terminal() {
	//do nothing
}
