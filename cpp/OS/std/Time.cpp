/*
 * Time.c
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#include "Time.h"

#include "stdasm.h"

#define CLOCKS_PER_SEC 700000000;//clock runs @ ~700MHz
namespace Time {
	static const uint32_t STB = 0x20003000;
	uint32_t getSystemTimerBase() {
		return STB;
	}
	clock_t clock() {
		uint64_t result = ASM::mmio_readd(getSystemTimerBase() + 4, 0);
		return result;
	}
	clock_t clock(clock_t* clocker) {
		uint64_t result = clock();
		if (clocker)
		*clocker = result;
		return result;
	}
	time_t time() {
		return clock() / CLOCKS_PER_SEC;
	}
	time_t time(time_t* timer) {
		time_t result = clock() / CLOCKS_PER_SEC
		;
		if (timer)
		*timer = result;
		return result;
	}
	time_t mktime(int secconds) {
		return ((uint64_t) secconds) * CLOCKS_PER_SEC;
	}
	void nanosleep(unsigned int howLong) {
		clock_t until = clock() + (howLong / .7) - 15; //current time + nanosecconds/.7 (.7ns per clock cycle) - 15 (it takes ~15 clock cycles to calculate)
		while (time() < until) {
		}
	}
	void delayMicroseconds(unsigned int howLong) {
		nanosleep(howLong * 1000);
	}
	/*
	 * delay:
	 *	Wait for some number of milliseconds
	 *********************************************************************************
	 */

	void delay(unsigned int howLong) {
		nanosleep(howLong * 1000000);
	}
}
