/*
 * InternalPins.hpp
 * Storage for all the 'real' raspberry pi pins.
 *  Created on: Mar 6, 2015
 *      Author: mailmindlin
 */

#ifndef IO_GPIO_INTERNALPINS_HPP_
#define IO_GPIO_INTERNALPINS_HPP_

#include "../../std/x-stdint.h"

#ifndef IO_GPIO_GPIO_H_
#include "GPIO.h"
#endif

namespace Peripherals {
class GPIO::InternalPins : public GPIO::PinStore {
public:
	class InternalPin : public GPIO::Pin {
	public:
		InternalPin(uint32_t pin);
		~InternalPin();
		void setFunction(uint8_t fn);
		void setOutput();
		void setInput();
		void digitalWrite(bool on);
		void analogWrite(double value);
		void pwmWrite(uint32_t data);
		bool digitalRead();
		double analogRead();
		void setPUDMode(uint8_t mode);
		bool isReal();
	protected:
		const uint32_t pin;
	};
	InternalPins() {
		//fill array with empty pins
		for(uint32_t i=0;i<64;i++)
			pins[i]=nullptr;
	}
	bool hasPin(uint32_t addr) {
		return addr<64;
	}
	GPIO::Pin& getPin(uint32_t addr) {
		if(nullptr==pins[addr])
			pins[addr]=new InternalPin(addr);
		return (Pin&)*pins[addr];
	}
	~InternalPins() {
		//delete all initialized pins
		for(uint32_t i=0;i<64;i++)
			if(nullptr!=pins[i])
				delete pins[i];
		//delete array
		delete pins;
	}
protected:
	InternalPin**pins = new InternalPin*[64];
};
}

#endif /* IO_GPIO_INTERNALPINS_HPP_ */
