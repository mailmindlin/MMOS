/*
 * USBctrl.h
 *
 *  Created on: Mar 6, 2015
 *      Author: mailmindlin
 */

#ifndef IO_USB_USBCTRL_H_
#define IO_USB_USBCTRL_H_

#include "../../std/x-stdint.h"

namespace IO {
namespace USB {
//from platform/arm/broadcom2835.c
bool powerOnUSB() {
	volatile uint32_t* mailbox;
	uint32_t result;

	mailbox = (uint32_t*)0x2000B880;
	while (mailbox[6] & 0x80000000);
	mailbox[8] = 0x80;
	do {
		while (mailbox[6] & 0x40000000);
	} while (((result = mailbox[0]) & 0xf) != 0);
	return result == 0x80;
}

}
}




#endif /* IO_USB_USBCTRL_H_ */
