/*
 * Kernel.cpp
 *
 *  Created on: Jan 30, 2015
 *      Author: mailmindlin
 */

#include "Kernel.h"

#include "IO/Display/framebuffer.hpp"
#include "IO/GPIO/GPIO.cpp"
#include "IO/GPIO/GPIO.h"
#include "IO/Terminal.cpp"
#include "std/stdasm.h"
#include "std/x-stdint.h"

#define swapuint(a,b) {uint64_t tmp=a;a=b;b=tmp;}
unsigned long waitTime=0x3F0000;
#define A_WHILE 0x3F000
#define width 1024
#define height 768
extern "C"
void kernel_main(uint32_t r0, uint32_t r1, uint32_t atags) {
	(void) r0;
	(void) r1;
	(void) atags;
	
	
	//random example code. I'm still focused on the framework like OpenGL, filesystems, etc.
	blink(3,0x7E000);
	Peripherals::GPIO::setup();
	blink(1,0x7E0000);
	
	//get framebuffer
	FrameBufferDescription* result = Displays::GPU::initFrameBuffer(width, height, 16);
	//check for failed framebuffer
	if(!result) {
		blink(0xFFFFFFF,0x1);
		return;
	} else {
		blink(1,0x7E0000);
	}
	
	//main render loop
	uint32_t color = 0;
	while(true) {
		blink(1,0x7300);
		uint32_t ptr = (uint32_t)result->pointer;
		for (int y = height; y > 0; y--, color++)
			for (int x = width; x > 0; x--) {
				ASM::half_write(ptr,color);
				//asm volatile("strh %[color] [%[fbAddr]]" : : [fbAddr]"r"(ptr), [color]"r"(color));
				ptr+=2;
			}
	}
	
	Terminal* terminal = new Terminal(width/5, height/5);
	terminal->initialize();
	terminal->writeString(0, 3, "ABC\0");
	terminal->draw(width, height, result);
	blink(7,0x7E000);
	terminal->writeString(0, 7, "\nABCDEF");
	while(1);
}

extern "C"
void kernel_shutdown() {
	//turn off led
	ASM::mmio_write(0x20200028, 1<<16);//turn on led
	blink(1,0x7E0000);
	Peripherals::GPIO::shutdown();
	blink(50,0x7E000);
}

extern "C++"
void waitABit(uint32_t i) {
	while(i-->0);
}

extern "C++"
void blink(uint32_t amt, uint32_t speed) {
	//let's try to set a pixel grey
//	uint16_t* pixel = (uint16_t*) 0xB8000;
//	*pixel = 0x7020;
	//the base memory address for the GPIO
	unsigned int gpio_base=0x20200000;
	//a value pointing to pin16
	unsigned int pin16 = 1<<16;
	//pointer to GPIO function select
	unsigned int* fsel=(unsigned int*) (gpio_base+4);//FSEL 1
	//pointer to GPIO pin off
	volatile unsigned int* pinOff  = (unsigned int*) (gpio_base+40);
	volatile unsigned int* pinOn = (unsigned int*) (gpio_base+28);
	*fsel=1<<18;
	while(amt--) {
		*pinOff=pin16;
		waitABit(speed);
		*pinOn=pin16;
		waitABit(speed);
	}
}
//}
/* *
extern "C++"
void blink(uint32_t amt) {
	Peripherals::GPIO& gpio = *new Peripherals::GPIO();
	Peripherals::GPIO::Pin& pin16 = gpio.getPin(16);
//	setup
	pin16.setOutput();
	while(amt-->0) {
		pin16.digitalWrite(false);
		waitABit();
		pin16.digitalWrite(true);
		waitABit();
	}
} */
/*
extern "C++"
void render(FrameBufferDescription* fb) {
	uint16_t color = 0;
	uint16_t* fbAddr;
	while(true) {
		fbAddr=reinterpret_cast<uint16_t*>(fb+32);
		for(uint32_t y=768;y>0;y--,color++) {
			for(uint32_t x=1024;x>0;x--) {
				*(fbAddr=fbAddr+2)=color;
				fbAddr+=2;
			}
		}
	}
}*/

