/*
 * framebuffer.h
 *
 *  Created on: Mar 3, 2015
 *      Author: mailmindlin
 */

#ifndef IO_DISPLAY_FRAMEBUFFER_HPP_
#define IO_DISPLAY_FRAMEBUFFER_HPP_

#include "../../std/stdasm.h"
#include "../../std/x-stdint.h"
#include "../../std/x-stdlib.h"
#include "postman.hpp"

//assembly stuff
extern "C" struct FrameBufferDescription {
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
};
extern "C" FrameBufferDescription* InitialiseFrameBuffer(uint32_t w,uint32_t h, uint32_t bd);
namespace Displays {
namespace GPU {
/*
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
};*/
//extern "C++" FrameBuferDescription FrameBufferInfo = { 1024, 768, 1024, 768, 0, 16, 0, 0, 0, 0 };
FrameBufferDescription* initFrameBuffer(uint32_t w, uint32_t h, uint32_t bd) {
	return InitialiseFrameBuffer(w,h,bd);
}
FrameBufferDescription* cinitFrameBuffer(uint32_t w, uint32_t h, uint32_t vw, uint32_t vh, uint32_t bd) {
	FrameBufferDescription* fb = (FrameBufferDescription*)malloc(sizeof(FrameBufferDescription));
	fb->width=w;
	fb->height=h;
	fb->vWidth=vw;
	fb->vHeight=vh;
	fb->bitDepth=bd;
	writeMailbox(MAILBOX_CHANNEL_TYPE::CHANNEL_FRAMEBUFFER, (uint32_t) fb+0x40000000);
	uint32_t result = readMailbox(MAILBOX_CHANNEL_TYPE::CHANNEL_FRAMEBUFFER);
	if(result==0)
		return 0;
	return (FrameBufferDescription*)result;
}
FrameBufferDescription* cinitFrameBuffer(uint32_t w, uint32_t h, uint32_t bd) {
	return cinitFrameBuffer(w,h,w,h,bd);
}
void setPixel(FrameBufferDescription* fb, uint32_t x, uint32_t y, uint16_t c) {
	ASM::half_write((uint32_t)fb->pointer+x+(y*fb->vWidth),c);
}
}
}
#endif /* IO_DISPLAY_FRAMEBUFFER_HPP_ */
