/*
 * vcasm.h
 *
 *  Created on: Mar 30, 2015
 *      Author: wfeehery17
 */

#ifndef IO_DISPLAY_GPU_VCASM_H_
#define IO_DISPLAY_GPU_VCASM_H_
#ifdef __VIDEOCORE__
#include "vc/intrinsics.h"
#undef asm
#define asm(x) _ASM(x)

#undef min
#define min(x,y) _min(x,y)

#undef max
#define max(x,y) _max(x,y)

#ifndef abs
#define abs(x) _abs(x)
#endif
#else
#define _vasm asm
#define _bkpt() do {asm(" bkpt");}while(0)
#define _di() do{asm(" di");}while(0)
#define _ei() do{asm(" ei");}while(0)
#define _nop() do{asm(" nop");}while(0)
#define _sleep() do{asm(" sleep");}while(0)

#undef min
#define min(x,y) ((x)<(y) ? (x):(y))

#undef max
#define max(x,y) ((x)>(y) ? (x):(y))

#ifndef abs
#define abs(x) ((x)>=0 ? (x):-(x))
#endif
#endif



#endif /* IO_DISPLAY_GPU_VCASM_H_ */
