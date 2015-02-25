/*
 * GPIO.h
 *
 *  Created on: Feb 18, 2015
 *      Author: wfeehery17
 */

#ifndef IO_GPIO_GPIO_HPP_
#define IO_GPIO_GPIO_HPP_

#include <stdint.h>
#include "../../std/util/LinkedList.cpp"
#include "../MemAddr.hpp"

class MappedGPIO;

class GPIO {
public:
	class Pin;
	class PinStore;
	//BCM magic
	static const uint64_t BCM_PASSWORD = 0x5A000000;
protected:
	static LinkedList<PinStore>* externPins;
};
#endif /* IO_GPIO_GPIO_HPP_ */
