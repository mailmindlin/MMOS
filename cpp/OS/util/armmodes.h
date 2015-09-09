/*
 * armmodes.h
 *
 *  Created on: Apr 16, 2015
 *      Author: wfeehery17
 */

#ifndef UTIL_ARMMODES_H_
#define UTIL_ARMMODES_H_
#define MODE_USR_NUM 0b10000
#define MODE_FIQ_NUM 0b10001
#define MODE_IRQ_NUM 0b10010
#define MODE_SVC_NUM 0b10011
#define MODE_ABT_NUM 0b10111
#define MODE_UND_NUM 0b11011
#define MODE_SYS_NUM 0b11111
#define MODE_MON_NUM 0b10110//monitor mode, probably not here

#define MODE_USR 1
#define MODE_FIQ 2
#define MODE_IRQ 3
#define MODE_SVC 4
#define MODE_ABT 5
#define MODE_UND 6
#define MODE_SYS 7
#define MODE_MON 8

extern uint8_t getCPSRNum();
extern void setCPSRNum(uint8_t mode);
uint8_t getMode() {
	switch(getCPSRNum()) {
	case MODE_USR_NUM:
		return MODE_USR;
	case MODE_FIQ_NUM:
		return MODE_FIQ;
	case MODE_IRQ_NUM:
		return MODE_IRQ;
	case MODE_SVC_NUM:
		return MODE_SVC;
	case MODE_ABT_NUM:
		return MODE_ABT;
	case MODE_UND_NUM:
		return MODE_UND;
	case MODE_SYS_NUM:
		return MODE_SYS;
	case MODE_MON_NUM:
		return MODE_MON;
	default:
		return -1;
	}
}
void setMode(uint8_t mode) {
	uint8_t num;
	switch(getCPSRNum()) {
	case MODE_USR:
		num = MODE_USR_NUM;
		break;
	case MODE_FIQ:
		num = MODE_FIQ_NUM;
		break;
	case MODE_IRQ:
		num = MODE_IRQ_NUM;
		break;
	case MODE_SVC:
		num = MODE_SVC_NUM;
		break;
	case MODE_ABT:
		num = MODE_ABT_NUM;
		break;
	case MODE_UND:
		num = MODE_UND_NUM;
		break;
	case MODE_SYS:
		num = MODE_SYS_NUM;
		break;
	case MODE_MON:
		num = MODE_MON_NUM;
		break;
	default:
		return;
	}
	setCPSRNum(num);
}
#endif /* UTIL_ARMMODES_H_ */
