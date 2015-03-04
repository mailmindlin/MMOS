/*
 * MemAddr.h
 *
 *  Created on: Feb 25, 2015
 *      Author: wfeehery17
 */

#ifndef IO_MEMADDR_HPP_
#define IO_MEMADDR_HPP_

#include "../std/x-stddef.h"

namespace MemoryMap {
typedef const unsigned long memconst_t;
typedef unsigned long* memptr_t;

memconst_t	BCM2708_PERI_BASE	= 0x20000000;
memconst_t	GPIO_ADDRESS		= (BCM2708_PERI_BASE + 0x200000);

size_t		BLOCK_SIZE			= (4*1024);
memconst_t	BSC0_ADDRESS		= (BCM2708_PERI_BASE + 0x205000);
memptr_t	BSC0_C				= (memptr_t)(BSC0_ADDRESS + 0x00);
memptr_t	BSC0_S				= (memptr_t)(BSC0_ADDRESS + 0x01);
memptr_t	BSC0_DLEN			= (memptr_t)(BSC0_ADDRESS + 0x02);
memptr_t	BSC0_A				= (memptr_t)(BSC0_ADDRESS + 0x03);
memptr_t	BSC0_FIFO			= (memptr_t)(BSC0_ADDRESS + 0x04);

memconst_t	GPU_BASE			= (BCM2708_PERI_BASE + 0x0000B880);
memptr_t	GPU_READ			= (memptr_t)(GPU_BASE + 0x00);
memptr_t	GPU_POLL			= (memptr_t)(GPU_BASE + 0x10);
memptr_t	GPU_SENDER			= (memptr_t)(GPU_BASE + 0x14);
memptr_t	GPU_STATUS			= (memptr_t)(GPU_BASE + 0x18);
memptr_t	GPU_CONFIG			= (memptr_t)(GPU_BASE + 0x1C);
memptr_t	GPU_WRITE			= (memptr_t)(GPU_BASE + 0x20);
}
#endif /* IO_MEMADDR_HPP_ */
