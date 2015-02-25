/*
 * MemAddr.h
 *
 *  Created on: Feb 25, 2015
 *      Author: wfeehery17
 */

#ifndef IO_MEMADDR_HPP_
#define IO_MEMADDR_HPP_
namespace MemoryMap {
typedef const unsigned long memconst_t;
typedef const unsigned long* memptr_t;

memconst_t	BCM2708_PERI_BASE	= 0x20000000;
memconst_t	GPIO_ADDRESS		= (BCM2708_PERI_BASE + 0x200000);
size_t		BLOCK_SIZE			= (4*1024);
memconst_t	BSC0_ADDRESS		= (BCM2708_PERI_BASE + 0x205000);
memptr_t	BSC0_C				= *(BSC0_ADDRESS + 0x00);
memptr_t	BSC0_S				= *(BSC0_ADDRESS + 0x01);
memptr_t	BSC0_DLEN			= *(BSC0_ADDRESS + 0x02);
memptr_t	BSC0_A				= *(BSC0_ADDRESS + 0x03);
memptr_t	BSC0_FIFO			= *(BSC0_ADDRESS + 0x04);
}
#endif /* IO_MEMADDR_HPP_ */
