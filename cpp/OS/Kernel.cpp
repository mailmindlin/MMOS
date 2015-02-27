/*
 * Kernel.cpp
 *
 *  Created on: Jan 30, 2015
 *      Author: wfeehery17
 */

#include "Kernel.h"

#include "std/stddef.h"
#include "std/stdint.h"

#define swapuint(a,b) {uint64_t tmp=a;a=b;b=tmp;}
#if defined(__cplusplus)
extern "C" /* Use C linkage for kernel_main. */
#endif
void kernel_main(uint32_t r0, uint32_t r1, uint32_t atags) {
	(void) r0;
	(void) r1;
	(void) atags;
	const int len=5;
	uint64_t list[]={0,5,3,2,8};
	for(int i=0;i<len;i++)
		list[i]=list[i]+1;
	render();
}
void render() {
	while(true) {
		renderFrame();
	}
}
void renderFrame() {
	//draw grey on screen
	*((uint16_t*) 0xB8000) = 0x7020;
}

