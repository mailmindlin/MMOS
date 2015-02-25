/*
 * GPIO.cpp
 *
 *  Created on: Feb 18, 2015
 *      Author: wfeehery17
 */

#include "GPIO.hpp"

#include "../../std/stddef.h"
#include "../../stdlib/util/AbstractList.hpp"
#include "../../stdlib/util/Iterator.hpp"
#include "../../stdlib/util/ListIterator.hpp"
#include "MappedGPIO.h"
#include "Pin.hpp"
#include "StdPinStore.cpp"

GPIO& GPIO::instance() {
	if (_inst == NULL)
		_inst = new GPIO();
	return *_inst;
}

void GPIO::destroy() {
	delete _inst;
}

MappedGPIO* GPIO::GPIO::map(PinMap& map) {
	return new MappedGPIO(map);
}

void GPIO::registerPinStore(GPIO::PinStore& ps) {
	pins->addLast(ps);
}

GPIO::GPIO() :
		pins(new LinkedList<PinStore>()) {
	PinStore* ps = new StdPinStore();
	this->registerPinStore(ps);
}

GPIO::Pin* GPIO::getPin(int pinnum) const {
	ListIterator<PinStore>* iterator = pins->listiterator();
	while (iterator->hasNext()) {
		PinStore ps = iterator->next();
		if (ps.containsPin(pinnum))
			return ps.getPin(pinnum);
	}
	return nullptr;
}

GPIO::~GPIO() {
	delete pins;
}
