/*
 * SDDriver.hpp
 * Nice wrapper class with some functions for controlling the EMMC (basically the SD card) on the
 * Raspberry Pi. This file just contains the really low level stuff, and most of it will probably be
 * ported to assembly in the future (because it has to do with bit manipulation, it's used a lot, and
 * speed really matters)
 *  Created on: Mar 9, 2015
 *      Author: mailmindlin
 */

#ifndef IO_SD_SDDRIVER_HPP_
#define IO_SD_SDDRIVER_HPP_

#include "../../std/x-stddef.h"
#include "../../std/x-stdint.h"
#include "../MemAddr.hpp"

namespace Peripherals {
namespace EMMC {
#define CTRLBITS(ptr,bits,flag) if(flag){*((volatile uint32_t*)ptr)|=bits;}else{*((volatile uint32_t*)ptr)&=~bits;}
#define BITSON(ptr,bits) *((volatile uint32_t*)ptr)|=bits;
#define BITSOFF(ptr,bits) *((volatile uint32_t*)ptr)&=~bits;
#define READBIT(ptr,num) (((*((volatile uint32_t*)ptr))>>num)&1)
//MemoryMap::memptr_t
//	ARG2			= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x00),
//	BLKSIZECNT		= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x04),
//	ARG1			= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x08),
//	CMDTM			= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x0C),
//	RESP0			= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x10),
//	RESP1			= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x14),
//	RESP2			= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x18),
//	RESP3			= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x1C),
//	DATA			= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x20),
//	STATUS			= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x24),
//	CONTROL0		= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x28),
//	CONTROL1		= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x2C),
//	INTERRUPT		= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x30),
//	IRPT_MASK		= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x34),
//	IRPT_EN			= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x38),
//	CONTROL2		= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x3C),
//	FORCE_IRPT		= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x50),
//	BOOT_TIMEOUT	= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x70),
//	DBG_SEL			= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x74),
//	EXRDFIFO_CFG	= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x80),
//	EXRDFIFO_EN		= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x84),
//	TUNE_STEP		= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x88),
//	TUNE_STEPS_STD	= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x8C),
//	TUNE_STEPS_DDR	= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0x90),
//	SPI_INT_SPT		= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0xF0),
//	SLOTISR_VER		= (MemoryMap::memptr_t)(MemoryMap::EMMC_BASE + 0xFC);
typedef volatile uint32_t		REG32RW;
#define FIELDRW(name,length) volatile uint32_t name : length;
#define FIELDRO(name,length) const volatile uint32_t name : length;
#define RESERVE(name,length) volatile void* name : length;
typedef volatile const uint32_t	REG32RO;
typedef volatile uint32_t		REG32WO;
typedef volatile void*			RESRV32;
typedef struct BLKSIZECNT_T {
	FIELDRW(BLKCNT, 16)
	RESERVE(RESERVED,6)
	FIELDRW(BLKSIZE,10)
};
typedef struct CMDTM_T {
	RESERVE(RESERVED0,2)
	FIELDRW(CMD_INDEX,6)
	FIELDRW(CMD_TYPE,2)
	FIELDRW(CMD_ISDATA,1)
	FIELDRW(CMD_IXCHK_EN,1)
	FIELDRW(CMD_CRCCHK_EN, 1)
	RESERVE(REDERVED1,1)
	FIELDRW(CMD_RSPNS_TYPE, 2)
	RESERVE(RESERVED2,10)//679012345
	FIELDRW(TM_MULTI_BLOCK, 1)//5
	FIELDRW(TM_DAT_DIR,1)//4
	FIELDRW(TM_AUTO_CMD_EN,2)//3:2
	FIELDRW(TM_BLKCNT_EN,1)//1
	RESERVE(RESERVED3,1)//0
};
typedef struct STATUS_T {
	RESERVE(RESERVED0,3)
	FIELDRW(DAT_LEVEL1,4)
	FIELDRW(CMD_LEVEL,1)
	FIELDRW(DAT_LEVEL0,4)
	RESERVE(RESERVED1,10)
	FIELDRW(READ_TRANSFER,1)
	FIELDRW(WRITE_TRANSFER,1)
	RESERVE(RESERVED2,5)
	FIELDRW(DAT_ACTIVE,1)
	FIELDRW(DAT_INHIBIT,1)
	FIELDRW(CMD_INHIBIT,1)
};
typedef struct CONTROL0_T {
	RESERVE(RESERVED0,9)
	FIELDRW(ALT_BOOT_EN,1)
	FIELDRW(BOOT_EN,1)
	FIELDRW(SPI_MODE,1)
	FIELDRW(GAP_IEN,1)
	FIELDRW(READWAIT_EN,1)
	FIELDRW(GAP_RESTART,1)
	FIELDRW(GAP_STOP,1)
	RESERVE(RESERVED1,10)
	FIELDRW(HCTL_8BIT,1)
	RESERVE(RESERVED2,2)
	FIELDRW(HCTL_HS_EN,1)
	FIELDRW(HCTL_DWIDTH,1)
	RESERVE(RESERVED3,1)
};
typedef struct CONTROL1_T {
	RESERVE(RESERVED0,5)
	FIELDRW(SRST_DATA,1)
	FIELDRW(SRST_CMD,1)
	FIELDRW(SRST_HC,1)
	RESERVE(RESERVED1,4)
	FIELDRW(DATA_TOUNIT,4)
	FIELDRW(CLK_FREQ8,8)
	FIELDRW(CLK_FREQ_MS2,2)
	FIELDRW(CLK_GENSEL,1)
	RESERVE(RESERVED2,2)
	FIELDRW(CLK_EN,1)
	FIELDRO(CLK_STABLE,1)
	FIELDRW(CLK_INTLEN,1)
};

#if sizeof(CONTROL0_T) !=32
#warning invalid register size
#endif

typedef struct SDCARD_T{
	REG32RW ARG2 : 32;
	BLKSIZECNT_T BLKSIZECNT : 32;
	REG32RW ARG1 : 32;
	CMDTM_T CMDTM : 32;
	REG32RW RESP0 : 32;
	REG32RW RESP1 : 32;
	REG32RW RESP2 : 32;
	REG32RW RESP3 : 32;
	REG32RW DATA : 32;
	STATUS_T STATUS : 32;
	CONTROL0_T CONTROL0 : 32;
	CONTROL1_T CONTROL1 : 32;
	//TODO finish these
	REG32RW INTERRUPT : 32;
	REG32RW IRPT_MASK : 32;
	REG32RW IRPT_EN : 32;
	REG32RW IRPT_EN : 32;
	REG32RW CONTROL2 : 32;
	REG32RW FORCE_IRPT : 32;
	REG32RW BOOT_TIMEOUT : 32;
	REG32RW DBG_SEL : 32;
	REG32RW EXRDFIFO_CFG : 32;
	REG32RW EXRDFIFO_EN : 32;
	REG32RW TUNE_STEP : 32;
	REG32RW TUNE_STEPS_STD : 32;
	REG32RW TUNE_STEPS_DDR : 32;
	REG32RW SPI_INT_SPT : 32;
	REG32RW SLOTISR_VER : 32;
};
const SDCARD_T *SDCARD = (MemoryMap::EMMC_BASE + 0x00);
/**
 * Select high speed mode (i.e. DAT and CMD lines change on the rising CLK edge):
 * @param aflag whether to use high speed mode
 */
void useHighSpeed(bool aflag) {
	SDCARD->CONTROL0.HCTL_DWIDTH=aflag;
}
/**
 * Enable the block counter for multiple block transfers
 * @param aflag whether to enable or disable it
 */
void enableMultiblockTransfer(bool aflag) {
	SDCARD->CMDTM.TM_MULTI_BLOCK=aflag;
}
/**
 * Enable alternate boot mode access
 * @param aflag whether to do so
 */
void enableAlternateBoot(bool aflag) {
	SDCARD->CONTROL0.ALT_BOOT_EN=aflag;//bit 22
}
/**
 * Boot mode access.
 * 	False: stop boot mode access
 * 	True: start boot mode access
 * @param aflag
 */
void enableBootAccess(bool aflag) {
	SDCARD->CONTROL0.BOOT_EN=aflag;//bit 21
}
/**
 * SPI mode enable
 * True: SPI mode
 * False: normal mode
 * @param aflag
 */
void enableSPI(bool aflag) {
	SDCARD->CONTROL0.SPI_MODE=aflag;//bit 20
}
/**
 * Enable SDIO interrupt at block gap (only valid if the HCTL_DWIDTH bit is set)
 * @param aflag
 */
void enableBlockGapInterrupt(bool aflag) {
	SDCARD->CONTROL0.GAP_IEN=aflag;//bit 19

}
/**
 * Use DAT2 read-wait protocol for SDIO cards supporting this:
 * @param aflag
 */
void useReadWait(bool aflag) {
	SDCARD->CONTROL0.READWAIT_EN=aflag;
}
/**
 * Restart a transaction which was stopped using the GAP_STOP bit
 * @param aflag
 */
void gapRestart(bool aflag) {
	SDCARD->CONTROL0.GAP_RESTART=aflag;
}
/**
 * Stop the current transaction at the next block gap
 * @param aflag
 */
void gapStop(bool aflag) {
	SDCARD->CONTROL0.GAP_STOP=aflag;
}
/**
 * use [num] datalines (num can be 2, 4, or 8, or it defaults to 2)
 * @param num how many data lines to use
 */
void useDataLines(uint8_t num) {
	if(num==4) {
		SDCARD->CONTROL0.HCTL_8BIT=false;
		SDCARD->CONTROL0.HCTL_DWIDTH=true;
	}else if(num==8) {
		SDCARD->CONTROL0.HCTL_DWIDTH=true;
		SDCARD->CONTROL0.HCTL_8BIT=true;
	}else {
		//default to 2
		SDCARD->CONTROL0.HCTL_8BIT=false;
		SDCARD->CONTROL0.HCTL_DWIDTH=false;
	}
}
/**
 * Reset the data handling circuit
 */
void softResetData() {
	SDCARD->CONTROL1.SRST_DATA=true;
}
/**
 * Reset the command handling circuit
 */
void softResetCmd() {
	SDCARD->CONTROL1.SRST_CMD=true;
}
/**
 * Reset the host circuit
 */
void softResetHost() {
	SDCARD->CONTROL1.SRST_HC=true;
}
/**
 * SD clock enable
 * @param aflag
 */
void enableSDClock(bool aflag) {
	SDCARD->CONTROL1.CLK_EN=aflag;
}
/**
 * SD clock stable
 * @return stablility
 */
bool isSDClockStable() {
	return SDCARD->CONTROL1.CLK_STABLE;
}
/**
 * Whether data can be read from the register
 * @return if it is ready
 */
bool canRead() {
	return READBIT(SDCARD->INTERRUPT,5)==1;
}
/**
 * Whether data can be written to the register.
 * @return Whether data can be written
 */
bool canWrite() {
	return READBIT(SDCARD->INTERRUPT,4)==1;
}
/**
 * Attempts to read from the DATA register. If #canRead() is false, then return false, otherwise
 * store 32 bits of data read into buffer. This method will instantly fail, without blocking.
 * @param buffer place to write to
 * @return success
 */
bool softRead32(uint32_t *buffer) {
	bool ok=canRead();
	if(ok)
		*buffer = *SDCARD->DATA;
	return ok;
}
/**
 * Waits (blocking) until #canRead() is true, then read from DATA
 * @return data read
 * @deprecated b/c it blocks, so you should probably use #softRead32(uint32_t* buffer);
 */
//__declspec(deprecated("This function can result in a deadlock."))
uint32_t blockRead32() {
	while(!canRead());
	uint32_t tmp = *SDCARD->DATA;
	return tmp;
}
/**
 * Attempts to write to the DATA register. If #canWrite() is false, then return false, otherwise
 * store 32 bits of data from the buffer. This method will instantly fail, without blocking.
 * @param buffer to write to DATA
 * @return success
 */
bool softWrite32(uint32_t buffer) {
	bool ok=canWrite();
	if(ok)
		*SDCARD->DATA = buffer;
	return ok;
}
/**
 * Waits (blocking) until #canWrite() is true, then write to DATA
 * @param buffer data to write
 * @deprecated b/c it blocks, so you should probably use #softWrite32(uint32_t buffer);
 */
//__declspec(deprecated("This function can result in a deadlock."))
void blockWrite32(uint32_t buffer) {
	while(!canRead());
	*SDCARD->DATA=buffer;
}
/**
 * Write up to len bytes from buffer starting at pos while canWrite() == true.
 * Returns number of blocks (4 bytes) written.
 * @param pos position of buffer to start at
 * @param len length of buffer
 * @param buffer buffer of data to write
 * @return actual number of blocks written
 */
size_t writeBuffer(size_t pos, size_t len, uint32_t* buffer) {
	size_t index=0;
	while(index<len && softWrite32(buffer[index+pos]))
		index++;
	return index;
}
/**
 * Read up to len bytes from buffer starting at pos while canRead() == true.
 * Returns number of blocks (4 bytes) written.
 * @param pos offset of buffer to start at
 * @param len length of buffer
 * @param buffer buffer of data to read to
 * @return actual number of blocks read (4 * bytes read)
 */
size_t readBuffer(size_t pos, size_t len, uint32_t* buffer) {
	size_t index=0;
	while(index<len && softRead32(buffer+index+pos))
		index++;
	return index;
}

}
}

#endif /* IO_SD_SDDRIVER_HPP_ */
