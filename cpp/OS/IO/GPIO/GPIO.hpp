/*
 * GPIO.h
 *
 *  Created on: Feb 18, 2015
 *      Author: wfeehery17
 */

#ifndef IO_GPIO_GPIO_HPP_
#define IO_GPIO_GPIO_HPP_

#include <x-vector.h>
#include <x-stdint.h>
namespace Peripherals {
class GPIO {
public:
	class Fsel {
	public:
		const static uint8_t
			DIGITAL_INPUT	= 0b000,
			DIGITAL_OUTPUT	= 0b001,
			ALT_0			= 0b100,
			ALT_1			= 0b101,
			ALT_2			= 0b110,
			ALT_3			= 0b111,
			ALT_4			= 0b011,
			ALT_5			= 0b010;
	};
	class PinMap;
	class Pin {
	public:
		virtual void setFunction(uint8_t fn);
		virtual void setOutput() {
			setFunction(GPIO::Fsel::DIGITAL_OUTPUT);
		}
		virtual void setInput() {
				setFunction(GPIO::Fsel::DIGITAL_INPUT);
		}
		Pin();
		virtual ~Pin();
	};
	class PinStore;
	GPIO();
	GPIO(PinMap& pm);
	~GPIO();
	Pin& getPin(uint32_t num);
	static void addPinStore(PinStore& pm);
protected:
	class InternalPins;
	const PinMap& pm;
	Pin& getPinReal(uint32_t address);
	static const std::vector<PinStore>* pinstores = new std::vector<PinStore>();
};
}
#include "PinStore.h"
namespace Peripherals {
class GPIO::InternalPins : PinStore {
public:
	class InternalPin;
	InternalPins() {
		addPinStore(*this);
	}
	Pin& getPin(uint32_t addr) {

	}
};
class GPIO::InternalPins::InternalPin : GPIO::Pin {
public:
	InternalPin(uint32_t pin);
	~InternalPin();
	void setFunction(uint8_t fn);
	void setOutput();
	void setInput();
protected:
	const uint32_t pin;
};
//initialize it
static GPIO::InternalPins* ipx = new GPIO::InternalPins();
}
/*
 * GPIO memory map: (from datasheet @ http://www.cl.cam.ac.uk/projects/raspberrypi/tutorials/os/downloads/SoC-Peripherals.pdf)
 * (start at 0x20200000)
 * Register:		Address:	Relative:  Size:	Read/Write:		Description:
 * GPFSEL0			0x20200000		 0		32			R/W			GPIO Function Select 0
 * GPFSEL1			0x20200004		 4		32			R/W			GPIO Function Select 1
 * GPFSEL2			0x20200008		 8		32			R/W			GPIO Function Select 2
 * GPFSEL3			0x2020000C		 C		32			R/W			GPIO Function Select 3
 * GPFSEL4			0x20200010		10		32			R/W			GPIO Function Select 4
 * GPFSEL5			0x20200014		14		32			R/W			GPIO Function Select 5
 * RESERVED			0x20200018		18		32			---			RESERVED
 * GPSET0			0x2020001C		1C		32			 W			GPIO Pin Output Set 0
 * GPSET1			0x20200020		20		32			 W			GPIO Pin Output Set 1
 * RESERVED			0x20200024		24		32			---			RESERVED
 * GPCLR0			0x20200028		28		32			 W			GPIO Pin Output Clear 0
 * GPCLR1			0x2020002C		2C		32			 W			GPIO Pin Output Clear 1
 * RESERVED			0x20200030		30		32			---			RESERVED
 * GPLEV0			0x20200034		34		32			 R			GPIO Pin Level 0
 * GPLEV1			0x20200038		38		32			 R			GPIO Pin Level 1
 * RESERVED			0x2020003C		3C		32			---			RESERVED
 * GPEDS0			0x20200040		40		32			R/W			GPIO Pin Event Detect Status 0
 * GPEDS1			0x20200044		44		32			R/W			GPIO Pin Event Detect Status 1
 * RESERVED			0x20200048		48		32			---			RESERVED
 * GPREN0			0x2020004C		4C		32			R/W			GPIO Pin Rising Edge Detect Enable 0
 * GPREN1			0x20200050		50		32			R/W			GPIO Rising Edge Detect Enable 1
 * RESERVED			0x20200054		54		32			---			RESERVED
 * GPFEN0			0x20200058		58		32			R/W			GPIO Pin Falling Edge Detect Enable 0
 * GPFEN1			0x2020005C		5C		32			R/W			GPIO Pin Falling Edge Detect Enable 1
 * RESERVED			0x20200060		60		32			---			RESERVED
 * GPHEN0			0x20200064		64		32			R/W			GPIO Pin High Detect Enable 0
 * GPHEN1			0x20200068		68		32			R/W			GPIO Pin High Detect Enable 1
 * RESERVED			0x2020006C		6C		32			---			RESERVED
 * GPLEN0			0x20200070		70		32			R/W			GPIO Pin Low Detect Enable 0
 * GPLEN1			0x20200074		74		32			R/W			GPIO Pin Low Detect Enable 1
 * RESERVED			0x20200078		78		32			---			RESERVED
 * GPAREN0			0x2020007C		7C		32			R/W			GPIO Pin Asynchronous Rising Edge Detect 0
 * GPAREN1			0x20200080		80		32			R/W			GPIO Pin Asynchronous Rising Edge Detect 1
 * RESERVED			0x20200084		84		32			---			RESERVED
 * GPAFEN0			0x20200088		88		32			R/W			GPIO Pin Asynchronous Falling Edge Detect 0
 * GPAFEN1			0x2020008C		8C		32			R/W			GPIO Pin Asynchronous Falling Edge Detect 1
 * RESERVED			0x20200090		90		32			---			RESERVED
 * GPPUD			0x20200094		94		32			R/W			GPIO Pin Pull-up/down Enable
 * GPPUDCLK0		0x20200099		98		32			R/W			GPIO Pin Pull-up/down Enable Clock 0
 * GPPUDCLK1		0x2020009C		9C		32			R/W			GPIO Pin Pull-up/down Enable Clock 1
 * RESERVED			0x202000A0		A0		32			---			RESERVED
 * TEST				0x202000B0		B0		04			R/W			Test
 */
#endif /* IO_GPIO_GPIO_HPP_ */
