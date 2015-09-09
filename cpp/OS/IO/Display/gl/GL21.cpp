/*
 * GL21.cpp
 *
 *  Created on: Mar 4, 2015
 *      Author: mailmindlin
 */

#include "GL21.h"


namespace Displays {
namespace OpenGL {
static const MAJORminorVersion* version;
static GLColor* glColorptr;
GL21::GL21() {
	version = new MAJORminorVersion(2,1);
	glColorptr=new GLColor;
}
GL21::~GL21() {
	delete version;
}

MAJORminorVersion& GL21::getVersion() {
	return *version;
}

void GL21::begin(GLMode mode) {
}

void GL21::vertex2s(GLshort x, GLshort y) {
}

void GL21::vertex2i(GLint x, GLint y) {
}

void GL21::vertex2f(GLfloat x, GLfloat y) {
}

void GL21::vertex2d(GLdouble x, GLdouble y) {
}

void GL21::vertex3s(GLshort x, GLshort y, GLshort z) {
}

void GL21::vertex3i(GLint x, GLint y, GLint z) {
}

void GL21::vertex3f(GLfloat x, GLfloat y, GLfloat z) {
}

void GL21::vertex3d(GLdouble x, GLdouble y, GLdouble z) {
}

void GL21::vertex4s(GLshort x, GLshort y, GLshort z, GLshort w) {
}

void GL21::vertex4i(GLint x, GLint y, GLint z, GLint w) {
}

void GL21::vertex4f(GLfloat x, GLfloat y, GLfloat z, GLfloat w) {
}

void GL21::vertex4d(GLdouble x, GLdouble y, GLdouble z, GLdouble w) {
}

void GL21::vertex2sv(const GLshort* v) {
}

void GL21::vertex2iv(const GLint* v) {
}

void GL21::vertex2fv(const GLfloat* v) {
}

void GL21::vertex2dv(const GLdouble* v) {
}

void GL21::vertex3sv(const GLshort* v) {
}

void GL21::vertex3iv(const GLint* v) {
}

void GL21::vertex3fv(const GLfloat* v) {
}

void GL21::vertex3dv(const GLdouble* v) {
}

void GL21::vertex4sv(const GLshort* v) {
}

void GL21::vertex4iv(const GLint* v) {
}

void GL21::vertex4fv(const GLfloat* v) {
}

void GL21::vertex4dv(const GLdouble* v) {
}

void GL21::color3b(GLbyte red, GLbyte green, GLbyte blue) {
}

void GL21::color3s(GLshort red, GLshort green, GLshort blue) {
}

void GL21::color3i(GLint red, GLint green, GLint blue) {
}

void GL21::color3f(GLfloat red, GLfloat green, GLfloat blue) {
}

void GL21::color3d(GLdouble red, GLdouble green, GLdouble blue) {
}

void GL21::color3ub(GLubyte red, GLubyte green, GLubyte blue) {
}

void GL21::color3us(GLushort red, GLushort green, GLushort blue) {
}

void GL21::color3ui(GLuint red, GLuint green, GLuint blue) {
}

void GL21::color4b(GLbyte red, GLbyte green, GLbyte blue, GLbyte alpha) {
}

void GL21::color4s(GLshort red, GLshort green, GLshort blue, GLshort alpha) {
}

void GL21::color4i(GLint red, GLint green, GLint blue, GLint alpha) {
}

void GL21::color4f(GLfloat red, GLfloat green, GLfloat blue, GLfloat alpha) {
}

void GL21::color4d(GLdouble red, GLdouble green, GLdouble blue, GLdouble alpha) {
}

void GL21::color4ub(GLubyte red, GLubyte green, GLubyte blue, GLubyte alpha) {
}

void GL21::color4us(GLushort red, GLushort green, GLushort blue, GLushort alpha) {
}

void GL21::color4ui(GLuint red, GLuint green, GLuint blue, GLuint alpha) {
}

void GL21::color3bv(const GLbyte* v) {
}

void GL21::color3sv(const GLshort* v) {
}

void GL21::color3iv(const GLint* v) {
}

void GL21::color3fv(const GLfloat* v) {
}

void GL21::color3dv(const GLdouble* v) {
}

void GL21::color3ubv(const GLubyte* v) {
}

void GL21::color3usv(const GLushort* v) {
}

void GL21::color3uiv(const GLuint* v) {
}

void GL21::color4bv(const GLbyte* v) {
}

void GL21::color4sv(const GLshort* v) {
}

void GL21::color4iv(const GLint* v) {
}

void GL21::color4fv(const GLfloat* v) {
}

void GL21::color4dv(const GLdouble* v) {
}

void GL21::color4ubv(const GLubyte* v) {
}

void GL21::color4usv(const GLushort* v) {
}

void GL21::color4uiv(const GLuint* v) {
}

void GL21::end() {
}
}}
