/*
 * vcutil.h
 *
 *  Created on: Apr 20, 2015
 *      Author: wfeehery17
 */

#ifndef IO_DISPLAY_GPU_VCUTIL_HPP_
#define IO_DISPLAY_GPU_VCUTIL_HPP_

#include "../../../std/x-stdint.h"

namespace Displays {
namespace GPU {
	extern struct INFO_T;
	INFO_T* getDisplayInfo(uint16_t displayNum);

}
}

#endif /* IO_DISPLAY_GPU_VCUTIL_HPP_ */
