/*
 * PinMap.hpp
 * Both intended for modification (for different mapping systems) and a default implementation
 * for mapping pin numbers to and from their actual addresses (the addresses that a pin store would
 * associate with a pin).
 * In this implementation, each pin maps to an address of the same number, and vice versa
 *  Created on: Mar 6, 2015
 *      Author: mailmindlin
 */

#ifndef IO_GPIO_PINMAP_HPP_
#define IO_GPIO_PINMAP_HPP_

#include "../../std/x-stdint.h"
#include "GPIO.h"

namespace Peripherals {
class GPIO::PinMap {
	public:
		PinMap() {}
		virtual uint32_t unmap(uint32_t num) const {
			return num;
		}
		virtual uint32_t map(uint32_t num) const {
			return num;
		}
		virtual ~PinMap() {}
	};
}




#endif /* IO_GPIO_PINMAP_HPP_ */
