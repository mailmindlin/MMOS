/*
 * postman.h
 *
 *  Created on: Mar 3, 2015
 *      Author: wfeehery17
 */

#ifndef IO_DISPLAY_POSTMAN_H_
#define IO_DISPLAY_POSTMAN_H_

#include "../../std/x-stdint.h"
#include "../MemAddr.hpp"

using namespace MemoryMap;
namespace Display {
namespace GPU {
bool writeMailbox(uint8_t channel, uint32_t value);
uint32_t readMailbox(uint8_t channel);
uint32_t readMailbox();
bool canWrite();
bool canRead();
bool writeMailbox(uint8_t channel, uint32_t value) {
	if (channel > 15 || (value & 0xF) == 0)
		return false;
	while (!canWrite())
		;
	*GPU_WRITE = channel | value;
	return true;
}
uint32_t readMailbox(uint8_t channel) {
	if (channel > 15)
		return false;
	uint32_t mail;
	do {
		mail=readMailbox();
	} while ((mail&0xF) != channel);
	return mail & 0xfffffff0;
}
bool canWrite() {
	return ((*GPU_STATUS) & 0x80000000) == 0;
}
bool canRead() {
	return ((*GPU_STATUS) & 0x40000000) == 0;
}
uint32_t readMailbox() {
	while (!canRead());
	return *GPU_READ;
}
}
}

#endif /* IO_DISPLAY_POSTMAN_H_ */
