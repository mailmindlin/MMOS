/*
 * PinMap.h
 *
 *  Created on: Feb 27, 2015
 *      Author: wfeehery17
 */

#ifndef IO_GPIO_PINMAP_H_
#define IO_GPIO_PINMAP_H_

#include <x-stdint.h>
#include "GPIO.hpp"

class Peripherals::GPIO::PinMap {
public:
	virtual uint32_t getRealPin(uint32_t num);
	PinMap();
	virtual ~PinMap();
};
#endif /* IO_GPIO_PINMAP_H_ */
