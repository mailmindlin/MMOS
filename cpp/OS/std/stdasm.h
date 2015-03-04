#include "x-stdint.h"

#ifndef STDLIB_STDASM_H_
#define STDLIB_STDASM_H_

// The base address for UART.
#define UART0_BASE = 0x20201000

// The offsets for reach register for the UART.
#define UART0_DR     (UART0_BASE + 0x00)
#define UART0_RSRECR (UART0_BASE + 0x04)
#define UART0_FR     (UART0_BASE + 0x18)
#define UART0_ILPR   (UART0_BASE + 0x20)
#define UART0_IBRD   (UART0_BASE + 0x24)
#define UART0_FBRD   (UART0_BASE + 0x28)
#define UART0_LCRH   (UART0_BASE + 0x2C)
#define UART0_CR     (UART0_BASE + 0x30)
#define UART0_IFLS   (UART0_BASE + 0x34)
#define UART0_IMSC   (UART0_BASE + 0x38)
#define UART0_RIS    (UART0_BASE + 0x3C)
#define UART0_MIS    (UART0_BASE + 0x40)
#define UART0_ICR    (UART0_BASE + 0x44)
#define UART0_DMACR  (UART0_BASE + 0x48)
#define UART0_ITCR   (UART0_BASE + 0x80)
#define UART0_ITIP   (UART0_BASE + 0x84)
#define UART0_ITOP   (UART0_BASE + 0x88)
#define UART0_TDR    (UART0_BASE + 0x8C)
namespace ASM {
EXC inline void mmio_write(uint32_t addr, uint32_t data) {
	uint32_t *ptr = (uint32_t*) addr;
	asm volatile("str %[data], [%[reg]]" : : [reg]"r"(ptr), [data]"r"(data));
}

EXC inline uint32_t mmio_read(uint32_t addr) {
	uint32_t *ptr = (uint32_t*) addr;
	uint32_t data;
	asm volatile("ldr %[data], [%[reg]]" : [data]"=r"(data) : [reg]"r"(ptr));
	return data;
}
EXC inline uint64_t mmio_readd(uint32_t reg, uint32_t shift) {
	uint32_t *ptr = (uint32_t*) reg;
	uint32_t *sptr= (uint32_t*) shift;
	uint32_t data0, data1;
#ifdef PURE_LDRD
	asm volatile("ldrd %[data0], %[data1], [%[reg], %[shft]] (!)" : [data0]"=r"(data0), [data1]"=r"(data1) : [reg]"r"(ptr), [shft]"r"(sptr) : );
	return data0 | (((uint64_t) data1) << 32);
#else
	asm volatile("ldr %[data0], [%[reg], %[shft]]" : [data0]"=r"(data0) : [reg]"r"(ptr), [shft]"r"(sptr) : );
	asm volatile("ldr %[data1], [%[reg], %[shft]]" : [data1]"=r"(data1) : [reg]"r"(ptr), [shft]"r"(sptr) : );
	return data0 | (((uint64_t) data1) << 32);
#endif
}
/* Loop <delay> times in a way that the compiler won't optimize away. */
EXC inline void delay(int32_t count) {
	asm volatile("__delay_%=: subs %[count], %[count], #1; bne __delay_%=\n" : : [count]"r"(count) : "cc");
}
}
#endif
