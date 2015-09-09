/*
 * BSC.cpp
 *
 *  Created on: Feb 25, 2015
 *      Author: mailmindlin
 */

#include "BSC.h"
namespace Peripherals {
void BSC::flush() {
	//set both clear bits to 1 & set start bit to 1.
	//clears FIFO before the next frame is started
	*C|=(0b1011<<4);
}
void BSC::clear() {
	//set start bit in control register to 1. Sends data.
	*C|=(3<<4);
}

I2CDevice* BSC::getDevice(uint8_t address) {
	return new I2CDevice(address,this);
}

void BSC::write(uint8_t addr, uint16_t len, uint8_t* data) {
	*C|=0x8000;//1<<15, to enable this controller
	*C&=~1;//set READ to false
	//set data length
	*DLEN = len;
	//set slave address
	*A = addr;
	//clear FIFO, in case it's filled with something
	*C|=0x30;//0b11 << 4, to set both clear bits to 1
	//store data in FIFO
	for(uint16_t i = 0;i<len;i++)
		*FIFO=data;
	//set START bit to 1, begin transfer
	*C|=0x80;
	//clear DONE bit, just in case
	*S|=2;
	//wait for finish
	while(((*S)&3)==1);
	//reset DONE bit by setting it to 1
	*S|=2;
}

void BSC::read(uint8_t addr, uint16_t len, uint8_t* buffer) {
	*C|=0x8001;//1<<15, to enable this controller & set READ to true
	//set data length
	*DLEN = len;
	//set slave address
	*A = addr;
	//clear DONE bit, just in case
	*S|=2;
	//clear FIFO, in case it's filled with something & begin transfer
	*C|=0xB0;//0b11 << 4 | 1<<7
	//wait for finish
	while(((*S)&3)==1);
	//read from FIFO
	for(uint16_t i=0;i<len;i++)
		buffer[i]=*FIFO;
	//reset DONE bit by setting it to 1
	*S|=2;
}

void BSC::write(uint8_t addr, uint16_t len, uint8_t* data) {
}

void BSC::read(uint8_t addr, uint16_t len, uint8_t* buffer) {
}
#ifdef __REALCOMP__
std::list<uint8_t>* BSC::search() {
	return NULL;
}
#endif

BSC::~BSC() {
	setEnabled(false);
}

void BSC::setEnabled(bool aflag) {
	if(aflag)
		//set bit 15 to 1
		*C|=(1<<15);
	else
		//set bit 15 to 0
		*C&=~(1<<15);
}

bool BSC::isEnabled() {
	return (((*C)>>15)&1)==1;
}

void BSC::setInterruptRXR(bool aflag) {
	if(aflag)
		//set bit 10 to 1
		*C|=(1<<10);
	else
		//set bit 15 to 0
		*C&=~(1<<10);
}

void BSC::setInterruptTXW(bool aflag) {
	if(aflag)
		//set bit 9 to 1
		*C|=(1<<9);
	else
		//set bit 9 to 0
		*C&=~(1<<9);
}

void BSC::setReading(bool aflag) {
	if(aflag)
		//set bit 9 to 1
		*C|=1;
	else
		//set bit 9 to 0
		*C&=~1;
}

void BSC::setInterruptDONE(bool aflag) {
	if(aflag)
		//set bit 8 to 1
		*C|=(1<<8);
	else
		//set bit 8 to 0
		*C&=~(1<<8);
}

void BSC::send() {
	//set start bit in control register to 1. Sends data.
	*C|=(1<<7);
}

BSC::BSC(MemoryMap::memconst_t base) :
		base(base),
		C(base + 0x00),
		S(base + 0x04),
		DLEN(base+0x08),
		A(base+0x0C),
		FIFO(base+0x10),
		DIV(base+0x14),
		DEL(base+0x18),
		CLKT(base+0x1C) {

}
}
