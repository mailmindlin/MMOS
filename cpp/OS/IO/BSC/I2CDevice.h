/*
 * I2CDevice.h
 *
 *  Created on: Feb 25, 2015
 *      Author: mailmindlin
 */

#ifndef IO_BSC_I2CDEVICE_H_
#define IO_BSC_I2CDEVICE_H_

#include "../../std/x-stdint.h"
#ifndef IO_BSC_BSC_H_
#include "BSC.h"
#endif

namespace Peripherals {
class I2CDevice {
public:
	void writeBytes(uint16_t len, uint8_t* data);
	void readBytes(uint16_t len, uint8_t* buffer);
	void xreadMem(uint8_t pos, uint16_t len, uint8_t* buffer);
	void xwriteMem(uint8_t pos, uint16_t len, uint8_t* data);
	const uint8_t getAddress() const;
	const BSC* getController() const;
	~I2CDevice();
protected:
	I2CDevice(uint8_t addr, BSC* controller);
	Peripherals::BSC* controller;
	const uint8_t addr;
};
}
#endif /* IO_BSC_I2CDEVICE_H_ */
