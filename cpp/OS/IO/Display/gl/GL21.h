/*
 * GL21.h
 *
 *  Created on: Mar 4, 2015
 *      Author: mailmindlin
 */

#ifndef IO_DISPLAY_GL_GL21_H_
#define IO_DISPLAY_GL_GL21_H_

#include "GLMode.h"
#include "GLTypes.h"

#include "../../../std/lang/MAJORminorVersion.h"

namespace Displays {
namespace OpenGL {
extern "C++"
class GL21 {
public:
	GL21();
	MAJORminorVersion& getVersion();
	void begin(GLMode mode);
	/**
	 * Specify vertex of 2 coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	void vertex2s(GLshort	x, GLshort	y);
	/**
	 * Specify vertex of 2 coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	void vertex2i(GLint		x, GLint		y);
	/**
	 * Specify vertex of 2 coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	void vertex2f(GLfloat	x, GLfloat	y);
	/**
	 * Specify vertex of 2 coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	void vertex2d(GLdouble	x, GLdouble	y);
	/**
	 * Specify vertex of 3 coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	void vertex3s(GLshort	x, GLshort	y, GLshort	z);
	/**
	 * Specify vertex of 3 coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	void vertex3i(GLint		x, GLint		y, GLint		z);
	/**
	 * Specify vertex of 3 coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	void vertex3f(GLfloat	x, GLfloat	y, GLfloat	z);
	/**
	 * Specify vertex of 3 coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	void vertex3d(GLdouble	x, GLdouble	y, GLdouble	z);
	/**
	 * Specify vertex of 4 coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 * @param w scalar
	 */
	void vertex4s(GLshort	x, GLshort	y, GLshort	z, GLshort	w);
	/**
	 * Specify vertex of 4 coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 * @param w scalar
	 */
	void vertex4i(GLint		x, GLint		y, GLint		z, GLint		w);
	/**
	 * Specify vertex of 4 coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 * @param w scalar
	 */
	void vertex4f(GLfloat	x, GLfloat	y, GLfloat	z, GLfloat	w);
	/**
	 * Specify vertex of 4 coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 * @param w scalar
	 */
	void vertex4d(GLdouble	x, GLdouble	y, GLdouble	z, GLdouble	w);
	/**
	 * Specify vertex of 2 coordinates
	 * @param v pointer to array of 2 coordinates
	 */
	void vertex2sv(const GLshort *	v);
	/**
	 * Specify vertex of 2 coordinates
	 * @param v pointer to array of 2 coordinates.
	 * v[0]=x
	 * v[1]=y
	 */
	void vertex2iv(const GLint *	v);
	/**
	 * Specify vertex of 2 coordinates
	 * @param v pointer to array of 2 coordinates.
	 * v[0]=x
	 * v[1]=y
	 */
	void vertex2fv(const GLfloat *	v);
	/**
	 * Specify vertex of 2 coordinates
	 * @param v pointer to array of 2 coordinates.
	 * v[0]=x
	 * v[1]=y
	 */
	void vertex2dv(const GLdouble *	v);
	/**
	 * Specify vertex of 3 coordinates
	 * @param v pointer to array of 3 coordinates.
	 * v[0]=x
	 * v[1]=y
	 * v[2]=z
	 */
	void vertex3sv(const GLshort *	v);
	/**
	 * Specify vertex of 3 coordinates
	 * @param v pointer to array of 3 coordinates.
	 * v[0]=x
	 * v[1]=y
	 * v[2]=z
	 */
	void vertex3iv(const GLint *	v);
	/**
	 * Specify vertex of 3 coordinates
	 * @param v pointer to array of 3 coordinates.
	 * v[0]=x
	 * v[1]=y
	 * v[2]=z
	 */
	void vertex3fv(const GLfloat *	v);
	/**
	 * Specify vertex of 3 coordinates
	 * @param v pointer to array of 3 coordinates.
	 * v[0]=x
	 * v[1]=y
	 * v[2]=z
	 */
	void vertex3dv(const GLdouble *	v);
	/**
	 * Specify vertex of 4 coordinates
	 * @param v pointer to array of 4 coordinates.
	 * v[0]=x
	 * v[1]=y
	 * v[2]=z
	 * v[3]=w
	 */
	void vertex4sv(const GLshort *	v);
	/**
	 * Specify vertex of 4 coordinates
	 * @param v pointer to array of 4 coordinates.
	 * v[0]=x
	 * v[1]=y
	 * v[2]=z
	 * v[3]=w
	 */
	void vertex4iv(const GLint *	v);
	/**
	 * Specify vertex of 4 coordinates
	 * @param v pointer to array of 4 coordinates.
	 * v[0]=x
	 * v[1]=y
	 * v[2]=z
	 * v[3]=w
	 */
	void vertex4fv(const GLfloat *	v);
	/**
	 * Specify vertex of 4 coordinates
	 * @param v pointer to array of 4 coordinates.
	 * v[0]=x
	 * v[1]=y
	 * v[2]=z
	 * v[3]=w
	 */
	void vertex4dv(const GLdouble *	v);
	/**
	 * Specify new RGB values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 */
	void color3b(GLbyte		red, GLbyte		green, GLbyte	blue);
	/**
	 * Specify new RGB values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 */
	void color3s(GLshort	red, GLshort	green, GLshort	blue);
	/**
	 * Specify new RGB values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 */
	void color3i(GLint		red, GLint		green, GLint	blue);
	/**
	 * Specify new RGB values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 */
	void color3f(GLfloat	red, GLfloat	green, GLfloat	blue);
	/**
	 * Specify new RGB values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 */
	void color3d(GLdouble	red, GLdouble	green, GLdouble	blue);
	/**
	 * Specify new RGB values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 */
	void color3ub(GLubyte	red, GLubyte	green, GLubyte	blue);
	/**
	 * Specify new RGB values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 */
	void color3us(GLushort	red, GLushort	green, GLushort	blue);
	/**
	 * Specify new RGB values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 */
	void color3ui(GLuint	red, GLuint		green, GLuint	blue);
	/**
	 * Specify new RGBA values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 * @param alpha new alpha value
	 */
	void color4b(GLbyte		red, GLbyte		green, GLbyte	blue, GLbyte	alpha);
	/**
	 * Specify new RGBA values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 * @param alpha new alpha value
	 */
	void color4s(GLshort	red, GLshort	green, GLshort	blue, GLshort	alpha);
	/**
	 * Specify new RGBA values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 * @param alpha new alpha value
	 */
	void color4i(GLint		red, GLint		green, GLint	blue, GLint		alpha);
	/**
	 * Specify new RGBA values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 * @param alpha new alpha value
	 */
	void color4f(GLfloat	red, GLfloat	green, GLfloat	blue, GLfloat	alpha);
	/**
	 * Specify new RGBA values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 * @param alpha new alpha value
	 */
	void color4d(GLdouble	red, GLdouble  	green, GLdouble	blue, GLdouble	alpha);
	/**
	 * Specify new RGBA values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 * @param alpha new alpha value
	 */
	void color4ub(GLubyte	red, GLubyte 	green, GLubyte	blue, GLubyte	alpha);
	/**
	 * Specify new RGBA values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 * @param alpha new alpha value
	 */
	void color4us(GLushort	red, GLushort  	green, GLushort	blue, GLushort	alpha);
	/**
	 * Specify new RGBA values for the current color.
	 * @param red new red value
	 * @param green new green value
	 * @param blue new blue value
	 * @param alpha new alpha value
	 */
	void color4ui(GLuint	red, GLuint		green, GLuint	blue, GLuint	alpha);
	/**
	 * Specify new RGB values for the current color.
	 * @param v pointer to array of RGB values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 */
	void color3bv(const GLbyte *	v);
	/**
	 * Specify new RGB values for the current color.
	 * @param v pointer to array of RGB values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 */
	void color3sv(const GLshort *	v);
	/**
	 * Specify new RGB values for the current color.
	 * @param v pointer to array of RGB values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 */
	void color3iv(const GLint *		v);
	/**
	 * Specify new RGB values for the current color.
	 * @param v pointer to array of RGB values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 */
	void color3fv(const GLfloat *	v);
	/**
	 * Specify new RGB values for the current color.
	 * @param v pointer to array of RGB values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 */
	void color3dv(const GLdouble *	v);
	/**
	 * Specify new RGB values for the current color.
	 * @param v pointer to array of RGB values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 */
	void color3ubv(const GLubyte *	v);
	/**
	 * Specify new RGB values for the current color.
	 * @param v pointer to array of RGB values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 */
	void color3usv(const GLushort *	v);
	/**
	 * Specify new RGB values for the current color.
	 * @param v pointer to array of RGB values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 */
	void color3uiv(const GLuint *	v);
	/**
	 * Specify new RGBA values for the current color.
	 * @param v pointer to array of RGBA values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 * v[3]=a
	 */
	void color4bv(const GLbyte *	v);
	/**
	 * Specify new RGBA values for the current color.
	 * @param v pointer to array of RGBA values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 * v[3]=a
	 */
	void color4sv(const GLshort *	v);
	/**
	 * Specify new RGBA values for the current color.
	 * @param v pointer to array of RGBA values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 * v[3]=a
	 */
	void color4iv(const GLint *		v);
	/**
	 * Specify new RGBA values for the current color.
	 * @param v pointer to array of RGBA values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 * v[3]=a
	 */
	void color4fv(const GLfloat *	v);
	/**
	 * Specify new RGBA values for the current color.
	 * @param v pointer to array of RGBA values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 * v[3]=a
	 */
	void color4dv(const GLdouble *	v);
	/**
	 * Specify new RGBA values for the current color.
	 * @param v pointer to array of RGBA values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 * v[3]=a
	 */
	void color4ubv(const GLubyte *	v);
	/**
	 * Specify new RGBA values for the current color.
	 * @param v pointer to array of RGBA values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 * v[3]=a
	 */
	void color4usv(const GLushort *	v);
	/**
	 * Specify new RGBA values for the current color.
	 * @param v pointer to array of RGBA values
	 * v[0]=r
	 * v[1]=g
	 * v[2]=b
	 * v[3]=a
	 */
	void color4uiv(const GLuint *	v);
	void secondaryColor3b(GLbyte	red,GLbyte		green,GLbyte	blue);
	void secondaryColor3s(GLshort	red,GLshort		green,GLshort	blue);
	void secondaryColor3i(GLint		red,GLint		green,GLint		blue);
	void secondaryColor3f(GLfloat	red,GLfloat		green,GLfloat	blue);
	void secondaryColor3d(GLdouble	red,GLdouble	green,GLdouble	blue);
	void secondaryColor3ub(GLubyte	red,GLubyte		green,GLubyte	blue);
	void secondaryColor3us(GLushort	red,GLushort	green,GLushort	blue);
	void secondaryColor3ui(GLuint	red,GLuint		green,GLuint	blue);
	void glSecondaryColor3bv(const GLbyte *		v);
	void glSecondaryColor3sv(const GLshort *	v);
	void glSecondaryColor3iv(const GLint *		v);
	void glSecondaryColor3fv(const GLfloat *	v);
	void glSecondaryColor3dv(const GLdouble *	v);
	void glSecondaryColor3ubv(const GLubyte *	v);
	void glSecondaryColor3usv(const GLushort *	v);
	void glSecondaryColor3uiv(const GLuint *	v);

	/**
	 * Set the current color index
	 * @param c new color index
	 */
	void glIndexs(GLshort	c);
	/**
	 * Set the current color index
	 * @param c new color index
	 */
	void glIndexi(GLint		c);
	/**
	 * Set the current color index
	 * @param c new color index
	 */
	void glIndexf(GLfloat	c);
	/**
	 * Set the current color index
	 * @param c new color index
	 */
	void glIndexd(GLdouble	c);
	/**
	 * Set the current color index
	 * @param c new color index
	 */
	void glIndexub(GLubyte	c);
	/**
	 * Set the current color index
	 * @param c pointer to array containing new color index at c[0]
	 */
	void glIndexsv(const GLshort *	c);
	/**
	 * Set the current color index
	 * @param c pointer to array containing new color index at c[0]
	 */
	void glIndexiv(const GLint *	c);
	/**
	 * Set the current color index
	 * @param c pointer to array containing new color index at c[0]
	 */
	void glIndexfv(const GLfloat *	c);
	/**
	 * Set the current color index
	 * @param c pointer to array containing new color index at c[0]
	 */
	void glIndexdv(const GLdouble *	c);
	/**
	 * Set the current color index
	 * @param c pointer to array containing new color index at c[0]
	 */
	void glIndexubv(const GLubyte *	c);
	void glFogCoordd(GLdouble		coord);
	void glFogCoordf(GLfloat		coord);
	void glFogCoorddv(GLdouble *	coord);
	void glFogCoordfv(GLfloat *		coord);

	void end();
};
}}
#endif /* IO_DISPLAY_GL_GL21_H_ */
