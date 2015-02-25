/*
 * BSC.cpp
 *
 *  Created on: Feb 25, 2015
 *      Author: wfeehery17
 */

#include "BSC.h"

void BSC::begin() {
}

void BSC::waitFor() {
}

void BSC::getDevice(uint8_t address) {
}

void BSC::write(uint8_t addr, size_t len, uint8_t data) {
}

BSC::BSC(MemoryMap::memconst_t addr) {
	setup(addr);
}

void BSC::setup(MemoryMap::memconst_t addr) :
		C(*(addr)), S(*(addr + 0x01)), DLEN(*(addr + 0x02)), A(*(addr + 0x03)), FIFO(*(addr + 0x04)) {
}

BSC::~BSC() {

}

