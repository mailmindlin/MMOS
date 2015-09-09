/*
 * GLColor.h
 *
 *  Created on: Mar 4, 2015
 *      Author: mailmindlin
 */

#ifndef IO_DISPLAY_GL_GLCOLOR_H_
#define IO_DISPLAY_GL_GLCOLOR_H_
namespace Displays {
namespace OpenGL {
static typedef float glColorType;
struct GLColor {
	glColorType R = 1.0;
	glColorType G = 1.0;
	glColorType B = 1.0;
	glColorType A = 1.0;
	GLColor(glColorType r,glColorType g,glColorType b, glColorType a):R(r),G(g),B(b),A(a)
	{}
	GLColor(glColorType r,glColorType g,glColorType b):R(r),G(g),B(b),A(1.0)
		{}
	GLColor():R(1.0),G(1.0),B(1.0),A(1.0){}
};
}}
#endif /* IO_DISPLAY_GL_GLCOLOR_H_ */
