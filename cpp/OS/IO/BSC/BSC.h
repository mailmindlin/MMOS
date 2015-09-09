/*
 * BSC.h
 *
 *  Created on: Feb 25, 2015
 *      Author: mailmindlin
 */

#ifndef IO_BSC_BSC_H_
#define IO_BSC_BSC_H_

#include "../../std/x-stdint.h"
#include "../MemAddr.hpp"
#ifndef IO_BSC_I2CDEVICE_H_
#include "I2CDevice.cpp"
#endif

#ifdef __REALCOMP__
#include <list>
#else
namespace std {
extern class list;
}
#endif
namespace Peripherals {
class BSC {
public:
	static const BSC* BSC0 = new BSC(MemoryMap::BSC0_ADDRESS);
	static const BSC* BSC1 = new BSC(MemoryMap::BSC1_ADDRESS);
	static const BSC* BSC2 = new BSC(MemoryMap::BSC2_ADDRESS);
	I2CDevice* getDevice(uint8_t address);
	void write(uint8_t addr, uint16_t len, uint8_t* data);
	void read(uint8_t addr, uint16_t len, uint8_t* buffer);
	void setEnabled(bool aflag);
	bool isEnabled();
	std::list<uint8_t>* search();
protected:
	static const uint32_t
		C_I2CEN	= (1 << 15),
		C_INTR	= (1 << 10),
		C_INTT	= (1 << 9),
		C_INTD	= (1 << 8),
		C_ST	= (1 << 7),
		C_CLEAR	= (1 << 4),
		C_READ	= 1,
		S_CLKT	= (1 << 9),
		S_ERR	= (1 << 8),
		S_RXF	= (1 << 7),
		S_TXE	= (1 << 6),
		S_RXD	= (1 << 5),
		S_TXD	= (1 << 4),
		S_RXR	= (1 << 3),
		S_TXW	= (1 << 2),
		S_DONE	= (1 << 1),
		S_TA	= 1,
		START_READ	= C_I2CEN|C_ST|C_CLEAR|C_READ,
		START_WRITE	= C_I2CEN|C_ST,
		CLEAR_STATUS= S_CLKT|S_ERR|S_DONE;
	MemoryMap::memptr_t base, C, S, DLEN, A, FIFO, DIV, DEL, CLKT;
	void setInterruptTXW(bool aflag);
	void setInterruptRXR(bool aflag);
	void setInterruptDONE(bool aflag);
	void flush();
	void clear();
	void send();
	void setReading(bool aflag);
	BSC(MemoryMap::memconst_t addr);
	virtual ~BSC();
};
}
#endif /* IO_BSC_BSC_H_ */
