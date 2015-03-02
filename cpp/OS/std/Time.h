/*
 * Time.h
 *
 *  Created on: Feb 18, 2015
 *      Author: wfeehery17
 */

#ifndef STD_TIME_H_
#define STD_TIME_H_

#include <x-stdint.h>
#if (!defined(EXC)) || (!defined(EXT))
#define EXC
#define EXT
#endif
EXT typedef uint64_t time_t;
EXT typedef uint64_t clock_t;
namespace Time {
EXT uint32_t getSystemTimerBase();
EXC clock_t clock();
EXC clock_t clock(clock_t* clocker);
EXC time_t time();
EXC time_t time(time_t* timer);
EXT time_t mktime(int secconds);
EXT void nanosleep(unsigned int howLong);
EXT void delayMicroseconds(unsigned int howLong);
EXT void delay(unsigned int howLong);
};
#endif /* STD_TIME_H_ */
