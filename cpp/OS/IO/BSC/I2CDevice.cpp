/*
 * I2CDevice.cpp
 *
 *  Created on: Feb 25, 2015
 *      Author: mailmindlin
 */

#include "I2CDevice.h"
namespace Peripherals {

void I2CDevice::writeBytes(uint16_t len, uint8_t* data) {
	this->controller->write(addr,len,data);
}

void I2CDevice::readBytes(uint16_t len, uint8_t* buffer) {
	this->controller->read(addr,len,buffer);
}

void I2CDevice::xreadMem(uint8_t pos, uint16_t len, uint8_t* buffer) {
	//broken
}

void I2CDevice::xwriteMem(uint8_t pos, uint16_t len, uint8_t* data) {
	//broken
}

const uint8_t I2CDevice::getAddress() const {
	return addr;
}

const BSC* I2CDevice::getController() const {
	return controller;
}

I2CDevice::~I2CDevice() {
}

I2CDevice::I2CDevice(uint8_t addr, BSC* controller):addr(addr),controller(controller) {
}
}
