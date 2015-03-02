/*
 * Kernel.cpp
 *
 *  Created on: Jan 30, 2015
 *      Author: wfeehery17
 */

#include "Kernel.h"


#define swapuint(a,b) {uint64_t tmp=a;a=b;b=tmp;}
unsigned long frameCnt=0;
void kernel_main(uint32_t r0, uint32_t r1, uint32_t atags) {
//void kernel_main() {
//	(void) r0;
//	(void) r1;
//	(void) atags;
	render();
}
extern "C++"
void render() {
	//let's try to set a pixel grey
	uint16_t* pixel = (uint16_t*) 0xB8000;
	*pixel = 0x7020;
	//the base memory address for the GPIO
	unsigned int gpio_base=0x20200000;
	//a value pointing to pin16
	unsigned int pin16 = 1<<16;
	//pointer to GPIO function select
	unsigned int* fsel=(unsigned int*) (gpio_base+4);
	//pointer to GPIO pin off
	unsigned int* pinOff  = (unsigned int*) (gpio_base+40);
	unsigned int* pinOn = (unsigned int*) (gpio_base+28);
	*fsel=1<<18;
	while(true) {
		*pinOff=pin16;
		waitABit();
		*pinOn=pin16;
		waitABit();
	}
}
extern "C++"
void waitABit() {
	unsigned int i=0x3F0000;
	while(i>0)i--;
}

