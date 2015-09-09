/*
 * scheduler.h
 *
 *  Created on: Mar 30, 2015
 *      Author: wfeehery17
 */

#ifndef NEWLIB_SCHEDULER_H_
#define NEWLIB_SCHEDULER_H_
#include "../std/x-stdint.h"
#include "../std/x-vector.h"
#include "armutil.h"
#ifndef _UTIL_PROCESS_C
#include "process.hpp"
#endif
extern "C" unsigned int scheduler_getPID();
namespace Scheduler {
const uint8_t PRIORITY_REALTIME = 0xFF;
const uint8_t PRIORITY_HIGH = 0xFE;
const uint8_t PRIORITY_PREFERED = 0xCC;
const uint8_t PRIORITY_NORMAL = 0x99;
const uint8_t PRIORITY_DISLIKED = 0x66;
const uint8_t PRIORITY_LOW = 0x33;
const uint8_t PRIORITY_DISABLED = 0x00;
unsigned int getPID();
void begin();
void run();
void stop();
}
#endif /* NEWLIB_SCHEDULER_H_ */
