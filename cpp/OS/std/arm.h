/*
 * arm.h
 *
 *  Created on: Mar 30, 2015
 *      Author: wfeehery17
 */

#ifndef STD_ARM_H_
#define STD_ARM_H_
namespace ASM {
const uint32_t
		USR=0x11000,
		SVC=0x10800,
		UND=0x07c00,
		ABT=0x07800,
		FIQ=0x07400,
		IRQ=0x07000;
extern "C" void setProgramMode(uint32_t mode) {

}
}

#endif /* STD_ARM_H_ */
