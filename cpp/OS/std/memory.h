/*
 * memory.h
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef STD_MEMORY_H_
#define STD_MEMORY_H_

#include "../std/memcpy.h"
#include "../std/stddef.h"

//void memcpy(void* src, size_t ooffset, void* dest, size_t toffset, size_t length) {
//
//}
namespace Array {
EXT void copy(void* src, void* dest, size_t ooffset, size_t toffset, size_t elements, size_t elementSizeBytes) {
	void* srcPtr = reinterpret_cast<void*>(src+ooffset);
	void* dstPtr = reinterpret_cast<void*>(dest+toffset);
	//length in bytes
	size_t len = elements*elementSizeBytes;
	memcpy(dstPtr, srcPtr, len);
}
EXC template<class X>
void copy(X* src, X* dest, size_t ooffset, size_t toffset, size_t elements) {
	for (size_t i = 0; i < elements; i++)
		dest[i + toffset] = src[i + ooffset];
}
EXC template<class X>
X* grow(X* source, size_t olen, size_t tlen) {
	X* target = new X[tlen];
	arrcpy(source, target, 0, 0, olen);
	X* tmp = source;
	delete tmp;
	return source = target;
}
}

#endif /* STD_MEMORY_H_ */
