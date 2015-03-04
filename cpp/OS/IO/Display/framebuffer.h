/*
 * framebuffer.h
 *
 *  Created on: Mar 3, 2015
 *      Author: wfeehery17
 */

#ifndef IO_DISPLAY_FRAMEBUFFER_H_
#define IO_DISPLAY_FRAMEBUFFER_H_

#include "../../std/x-stdint.h"
#include "postman.h"
namespace Display {
namespace GPU {
struct FrameBuferDescription {
	uint32_t width		= 1024;
	uint32_t height		= 768;
	uint32_t vWidth		= 1024;
	uint32_t vHeight	= 768;
	uint32_t pitch		= 0;
	uint32_t bitDepth	= 16;
	uint32_t x			= 0;
	uint32_t y			= 0;
	void* pointer		= 0;
	uint32_t size		= 0;
	FrameBuferDescription(uint32_t width, uint32_t height,uint32_t vWidth,uint32_t vHeight,uint32_t pitch,uint32_t bitDepth,uint32_t x,uint32_t y,void* pointer,uint32_t size)
	  : width(width),height(height),vWidth(vWidth),vHeight(vHeight),pitch(pitch),bitDepth(bitDepth),x(x),y(y),pointer(pointer),size(size){}
};
FrameBuferDescription FrameBufferInfo = { 1024, 768, 1024, 768, 0, 16, 0, 0, 0, 0 };
bool initFrameBuffer(uint32_t width, uint32_t height, uint32_t bitDepth) {
	if(width>4096 || height>4096 || bitDepth>32)
		return false;
	FrameBufferInfo.width=width;
	FrameBufferInfo.height=height;
	FrameBufferInfo.vWidth=width;
	FrameBufferInfo.vHeight=height;
	FrameBufferInfo.bitDepth=bitDepth;
	uint32_t fbAddr = ((uint32_t)&FrameBufferInfo)+0x40000000;
	return (writeMailbox(1,fbAddr) && readMailbox(1)==0);
}
}
}

#endif /* IO_DISPLAY_FRAMEBUFFER_H_ */
