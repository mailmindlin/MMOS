/*
 * Kernel.h
 *
 *  Created on: Jan 30, 2015
 *      Author: mailmindlin
 */
#ifndef KERNEL_H_
#define KERNEL_H_

#include "std/x-stdint.h"
#include "newlib.h"
//#include "util/scheduler.cpp"
//#include "IO/SD/SDDriver.hpp"
extern "C" {
/**
 * Entry point to c++ code
 * @param r0 register 0
 * @param r1 register 1
 * @param atags (not really sure about this one)
 */
extern void kernel_main(uint32_t r0, uint32_t r1, uint32_t atags);
/**
 * Called to shutdown the kernel. Basically, just shutdown all of the peripherals, and release all the
 * memory possible.
 * TODO: add some way for things to add shutdown hooks, to avoid hardcoding every shutdown action into
 * here.
 */
extern void kernel_shutdown();
}
extern "C++" {
extern void blink(uint32_t amt,uint32_t speed);
extern void render(struct FrameBufferDescription* fb);
extern void waitABit(uint32_t i);
}
#endif /* KERNEL_H_ */
