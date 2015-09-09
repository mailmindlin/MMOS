/*
 * gputypes.h
 *
 *  Created on: Apr 20, 2015
 *      Author: wfeehery17
 */

#ifndef IO_DISPLAY_GPU_GPUTYPES_H_
#define IO_DISPLAY_GPU_GPUTYPES_H_

#include "../../../std/x-stdint.h"
#include "display_types.hpp"

namespace Displays {
namespace GPU {
typedef struct GET_INFO_DATA_T {
	int32_t response;
	uint32_t width;
	uint32_t height;
	uint32_t transform;
	uint32_t input_format;
};
typedef struct MODEINFO_T {
	int32_t width;
	int32_t height;
	TRANSFORM_T transform;
	DISPLAY_INPUT_FORMAT_T input_format;
};
typedef enum TRANSFORM_T {
	/* Bottom 2 bits sets the orientation */
	NO_ROTATE = 0,
	ROTATE_90 = 1,
	ROTATE_180 = 2,
	ROTATE_270 = 3,

	FLIP_HRIZ = 1 << 16,
	FLIP_VERT = 1 << 17,

	/* invert left/right images */
	STEREOSCOPIC_INVERT = 1 << 19,
	/* extra flags for controlling 3d duplication behaviour */
	STEREOSCOPIC_NONE = 0 << 20,
	STEREOSCOPIC_MONO = 1 << 20,
	STEREOSCOPIC_SBS = 2 << 20,
	STEREOSCOPIC_TB = 3 << 20,
	STEREOSCOPIC_MASK = 15 << 20,

	/* extra flags for controlling snapshot behaviour */
	SNAPSHOT_NO_YUV = 1 << 24,
	SNAPSHOT_NO_RGB = 1 << 25,
	SNAPSHOT_FILL = 1 << 26,
	SNAPSHOT_SWAP_RED_BLUE = 1 << 27,
	SNAPSHOT_PACK = 1 << 28
};
/*
 * Define vector struct for scatter-gather (vector) operations
 * Vectors can be nested - if a vector element has negative length, then
 * the data pointer is treated as pointing to another vector array, with
 * '-vec_len' elements. Thus to append a header onto an existing vector,
 * you can do this:
 *
 * void foo(const VCHI_MSG_VECTOR_T *v, int n)
 * {
 *    VCHI_MSG_VECTOR_T nv[2];
 *    nv[0].vec_base = my_header;
 *    nv[0].vec_len = sizeof my_header;
 *    nv[1].vec_base = v;
 *    nv[1].vec_len = -n;
 *    ...
 *
 */
typedef struct vchi_msg_vector {
	const void *vec_base;
	int32_t vec_len;
} VCHI_MSG_VECTOR_T;
}
}

#endif /* IO_DISPLAY_GPU_GPUTYPES_H_ */
