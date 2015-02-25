/*
 * I2CDevice.cpp
 *
 *  Created on: Feb 25, 2015
 *      Author: wfeehery17
 */

#include "I2CDevice.h"

I2CDevice::I2CDevice(uint8_t addr, BSC* controller):address(addr),controller(controller) {

}

I2CDevice::~I2CDevice() {
	// TODO Auto-generated destructor stub
}

