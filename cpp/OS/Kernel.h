/*
 * Kernel.h
 *
 *  Created on: Jan 30, 2015
 *      Author: wfeehery17
 */
#ifndef KERNEL_H_
#define KERNEL_H_
#include <x-stddef.h>
#include <x-stdint.h>
#include <newlib.c>
extern "C" {
extern void kernel_main(uint32_t r0, uint32_t r1, uint32_t atags);
extern void render();
extern void renderFrame();
extern void waitABit();
}
#endif /* KERNEL_H_ */
