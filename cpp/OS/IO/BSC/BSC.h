/*
 * BSC.h
 *
 *  Created on: Feb 25, 2015
 *      Author: wfeehery17
 */

#ifndef IO_BSC_BSC_H_
#define IO_BSC_BSC_H_

#include "../../std/x-stddef.h"
#include "../../std/x-stdint.h"
#include "../MemAddr.hpp"

class BSC {
public:
	static void init();
	static const BSC* BSC1;
	static const BSC* BSC2;
	void begin();
	void waitFor();
	void getDevice(uint8_t address);
	void write(uint8_t addr, size_t len, uint8_t data);

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
	BSC(MemoryMap::memconst_t addr);
	void setup(MemoryMap::memconst_t addr);
	virtual ~BSC();
	MemoryMap::memptr_t base, C, S, DLEN, A, FIFO;
};

#endif /* IO_BSC_BSC_H_ */
