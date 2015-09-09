/*
 * armtimer.h
 *
 *  Created on: Apr 1, 2015
 *      Author: wfeehery17
 */

#ifndef UTIL_ARMTIMER_H_
#define UTIL_ARMTIMER_H_
/* http://infocenter.arm.com/help/index.jsp?topic=/com.arm.doc.ddi0271d/index.html */
#define TIMER0 ((volatile unsigned int*)0x101E2000)
#define TIMER_VALUE 0x1 /* 0x04 bytes */
#define TIMER_CONTROL 0x2 /* 0x08 bytes */
#define TIMER_INTCLR 0x3 /* 0x0C bytes */
#define TIMER_MIS 0x5 /* 0x14 bytes */
/* http://infocenter.arm.com/help/index.jsp?topic=/com.arm.doc.ddi0271d/Babgabfg.html */
#define TIMER_EN 0x80
#define TIMER_PERIODIC 0x40
#define TIMER_INTEN 0x20
#define TIMER_32BIT 0x02
#define TIMER_ONESHOT 0x01
void initTimer() {
	*(TIMER0 + TIMER_CONTROL) = TIMER_EN | TIMER_ONESHOT | TIMER_32BIT;
}
void setTimeout(unsigned int cycles) {
	*TIMER0=cycles;
}
#endif /* UTIL_ARMTIMER_H_ */
