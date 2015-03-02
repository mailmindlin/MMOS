/*
 * PinStore.h
 *
 *  Created on: Feb 27, 2015
 *      Author: wfeehery17
 */

#ifndef IO_GPIO_PINSTORE_H_
#define IO_GPIO_PINSTORE_H_

#include <x-stdint.h>
#include "GPIO.hpp"

class Peripherals::GPIO::PinStore {
	virtual ~PinStore();
	virtual Pin& getPin(uint32_t addr);
};


#endif /* IO_GPIO_PINSTORE_H_ */
