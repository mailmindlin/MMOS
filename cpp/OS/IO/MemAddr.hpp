/*
 * MemAddr.h
 * Memory addresses for a bunch of peripherals (so if I get the addresses wrong, I can fix it without
 * changing every reference to these, like if they are different on the RPi2 for something.
 *  Created on: Feb 25, 2015
 *      Author: mailmindlin
 */

#ifndef IO_MEMADDR_HPP_
#define IO_MEMADDR_HPP_

#include "../std/x-stddef.h"

namespace MemoryMap {
typedef const uint32_t memconst_t;
typedef volatile uint32_t* memptr_t;

memconst_t	BCM2708_PERI_BASE	= 0x20000000;
memconst_t	GPIO_ADDRESS		= (BCM2708_PERI_BASE + 0x200000);

size_t		BLOCK_SIZE			= (4*1024);
memconst_t	BSC0_ADDRESS		= (BCM2708_PERI_BASE + 0x205000);
memconst_t	BSC1_ADDRESS		= (BCM2708_PERI_BASE + 0x804000);
memconst_t	BSC2_ADDRESS		= (BCM2708_PERI_BASE + 0x805000);

memconst_t	GPU_BASE			= (BCM2708_PERI_BASE + 0x0000B880);
memptr_t	GPU_READ			= (memptr_t)(GPU_BASE + 0x00);
memptr_t	GPU_POLL			= (memptr_t)(GPU_BASE + 0x10);
memptr_t	GPU_SENDER			= (memptr_t)(GPU_BASE + 0x14);
memptr_t	GPU_STATUS			= (memptr_t)(GPU_BASE + 0x18);
memptr_t	GPU_CONFIG			= (memptr_t)(GPU_BASE + 0x1C);
memptr_t	GPU_WRITE			= (memptr_t)(GPU_BASE + 0x20);

memconst_t	EMMC_BASE			= (BCM2708_PERI_BASE + 0x300000);
}
#endif /* IO_MEMADDR_HPP_ */
