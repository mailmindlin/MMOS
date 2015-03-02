/*
 * I2CDevice.h
 *
 *  Created on: Feb 25, 2015
 *      Author: wfeehery17
 */

#ifndef IO_BSC_I2CDEVICE_H_
#define IO_BSC_I2CDEVICE_H_

#include <x-stddef.h>
#include <x-stdint.h>

class I2CDevice {
	friend class BSC;
public:
	void writeBytes(size_t len, uint8_t* data);
	virtual ~I2CDevice();
protected:
	I2CDevice(uint8_t address, BSC* controller);
	const BSC* controller;
	const uint8_t address;
};

#endif /* IO_BSC_I2CDEVICE_H_ */
