/*
 * GPIO.cpp
 *
 *  Created on: Feb 18, 2015
 *      Author: mailmindlin
 */

#include "GPIO.h"

#include "../../std/x-stddef.h"
#include "InternalPins.hpp"
#include "PinMap.hpp"

namespace Peripherals {
std::vector<GPIO::PinStore*>* GPIO::pinstores;
GPIO::GPIO() :
		pm(new PinMap) {
}

GPIO::GPIO(PinMap& pm) :
		pm(&pm) {
}

GPIO::~GPIO() {
	delete pm;
}

GPIO::Pin& GPIO::getPin(uint32_t num) {
	if (nullptr != pm)
		return getPinReal(pm->unmap(num));
	else
		return getPinReal(num);
}
void GPIO::setup() {
	GPIO::pinstores = new std::vector<PinStore*>();
	addPinStore(new InternalPins());
}
void GPIO::addPinStore(PinStore* ps) {
	pinstores->push_back(ps);
}

void GPIO::shutdown() {
	//release pin stores
	while (!pinstores->empty()) {
		delete (*pinstores)[pinstores->size() - 1];
		pinstores->pop_back();
	}
	delete pinstores;
}

GPIO::Pin& GPIO::getPinReal(uint32_t address) {
	for (size_t i = 0; i < pinstores->size(); i++) {
		PinStore& tmpstr = *(*pinstores)[(size_t) i];
		if (tmpstr.hasPin(address))
			return tmpstr.getPin(address);
	}
	return *(new Pin);
}
}
