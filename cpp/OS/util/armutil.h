/*
 * armutil.h
 *
 *  Created on: Apr 1, 2015
 *      Author: wfeehery17
 */

#ifndef UTIL_ARMUTIL_H_
#define UTIL_ARMUTIL_H_
typedef void(*SYSHANDLER)(void);
/**
 * Default system handler, does nothing
 */
extern "C"
void hang(void){asm("b .");asm("b .");}
//assembly-defined default handlers
extern void _reset_handler(void);
extern void _enable_irq(void);
extern void _initVectors(void);
SYSHANDLER undefined_handler= hang;
SYSHANDLER swi_handler		= hang;
SYSHANDLER prefetch_handler	= hang;
SYSHANDLER data_handler		= hang;
SYSHANDLER unused_handler	= hang;
SYSHANDLER irq_handler		= hang;
SYSHANDLER fiq_handler		= hang;
extern "C"
void IRQHandler(void) {
	volatile unsigned int *base = (unsigned int *) 0x80000000;
	if (*base == 1){          // which interrupt was it?
	        irq_handler();     // process the interrupt
	}
	*(base+1) = 0;           // clear the interrupt
}
extern "C"
inline void cfptr(SYSHANDLER s) {
	s();
}
extern "C"
void abcd(){
	cfptr(undefined_handler);
}
#endif /* UTIL_ARMUTIL_H_ */
