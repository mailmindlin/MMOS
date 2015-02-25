/*
 * Kernel.cpp
 *
 *  Created on: Jan 30, 2015
 *      Author: wfeehery17
 */

#include "Kernel.h"

#include "IO/GPIO/GPIO.cpp"
#include "IO/GPIO/Pin.hpp"
#include <stdint.h>

#if defined(__cplusplus)
extern "C" /* Use C linkage for kernel_main. */
#endif
void kernel_main(uint32_t r0, uint32_t r1, uint32_t atags) {
	(void) r0;
	(void) r1;
	(void) atags;
	GPIO gpio = GPIO::instance();
	GPIO::Pin* led = gpio.getPin(19);
	led->digitalWrite(DigitalPinState::HIGH);
	delete led;
	GPIO::destroy();
}
void render() {

}
void shutdown() {

}

