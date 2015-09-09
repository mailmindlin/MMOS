/*
 * InternalPin.cpp
 * Implementation of InternalPin class.
 * Provides low-level access to the GPIO pins currently on the raspberry pi.
 *  Created on: Mar 6, 2015
 *      Author: mailmindlin
 */

#include "../../std/x-stdint.h"
#include "GPIO.h"
#include "GPIOREG.h"
#include "InternalPins.hpp"

namespace Peripherals {
GPIO::InternalPins::InternalPin::InternalPin(uint32_t pin) : pin(pin) {
}

void GPIO::InternalPins::InternalPin::setFunction(uint8_t fn) {
	//make pointer to function select register for this pin (register # is floor(pin/10), addr is GPIO_BASE + 4*# )
	REG32RW* fselptr = ((GPREGS)+(4*(pin/10)));
	//calculate the offset of the bits.
	uint16_t shift = (pin%10)*3;
	//set the three consecuitive bits to 0 by [fsel value] & [all the bits except the three]
	//then insert the new value with (fn<<shift)
	*fselptr=((*fselptr) & (0xFFFFFFFF-(0x7<<shift)))|(fn<<shift);
}

void GPIO::InternalPins::InternalPin::setOutput() {
	setFunction(GPIO::Fsel::DIGITAL_OUTPUT);
}

void GPIO::InternalPins::InternalPin::setInput() {
	setFunction(GPIO::Fsel::DIGITAL_INPUT);
}

void GPIO::InternalPins::InternalPin::digitalWrite(bool on) {
	REG32WO ptr;
	if(pin<=32)
		if(on)
			ptr=GPREGS->GPSET0;
		else
			ptr=GPREGS->GPCLR0;
	else
		if(on)
			ptr=GPREGS->GPSET1;
		else
			ptr=GPREGS->GPCLR1;
	ptr=1<<(pin%32);
}

void GPIO::InternalPins::InternalPin::analogWrite(double value) {
	//if value>0, then write true
	digitalWrite(value>0.0);
}

void GPIO::InternalPins::InternalPin::pwmWrite(uint32_t data) {
	//TODO implement
}

bool GPIO::InternalPins::InternalPin::digitalRead() {
	REG32RO ptr;
	if(pin<=32)
		ptr = GPREGS->GPLEV0;
	else
		ptr = GPREGS->GPLEV1;
	return ((ptr>>(pin%32))&1)==1;
}

double GPIO::InternalPins::InternalPin::analogRead() {
	//return 2.1 for digital on (GPIO trigger volatage), 0.0 for digital off
	return digitalRead()?2.1:0.0;
}

void GPIO::InternalPins::InternalPin::setPUDMode(uint8_t mode) {
	//TODO implement
}
GPIO::InternalPins::InternalPin::~InternalPin() {
	//do nothing
}

bool GPIO::InternalPins::InternalPin::isReal() {
	return pin<64;
}

} /* namespace Peripherals */
