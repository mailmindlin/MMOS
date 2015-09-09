/*
 * GLES3.h
 *
 *  Created on: Mar 30, 2015
 *      Author: wfeehery17
 */

#ifndef IO_DISPLAY_GL_GLES3_H_
#define IO_DISPLAY_GL_GLES3_H_

namespace Displays {
namespace OpenGL {

class GLES3 {
public:
	GLES3();
	virtual ~GLES3();
	/**
	 * bind a renderbuffer to a renderbuffer target
	 *
	 * glBindRenderbuffer binds the renderbuffer object with name renderbuffer to the renderbuffer target
	 * specified by target. target must be GL_RENDERBUFFER. Calling glBindRenderbuffer with renderbuffer
	 * set to a value of zero breaks the existing binding of a renderbuffer object to target.
	 *
	 * {@link #glGenRenderbuffers} may be used to generate a set of unused renderbuffer object names.
	 * @param target Specifies the renderbuffer target of the binding operation. target must be GL_RENDERBUFFER.
	 * @param renderbuffer Specifies the name of the renderbuffer object to bind.
	 * @error GL_INVALID_ENUM is generated if target is not GL_RENDERBUFFER.
	 * @see glGenRenderbuffers
	 * @see glDeleteRenderbuffers
	 * @see glRenderbufferStorage
	 * @see glRenderbufferStorageMultisample
	 * @see glIsRenderbuffer
	 */
	void glBindRenderbuffer(GLenum target, GLuint renderbuffer);
	/**
	 * Generate renderbuffer object names
	 *
	 * glGenRenderbuffers returns n renderbuffer object names in renderbuffers. There is no guarantee that
	 * the names form a contiguous set of integers; however, it is guaranteed that none of the returned
	 * names was in use immediately before the call to glGenRenderbuffers.
	 *
	 * Renderbuffer object names returned by a call to glGenRenderbuffers are not returned by subsequent
	 * calls, unless they are first deleted with glDeleteRenderbuffers.
	 * The names returned in renderbuffers are marked as used, for the purposes of glGenRenderbuffers
	 * only, but they acquire state and type only when they are first bound.
	 *
	 * @param n Specifies the number of renderbuffer object names to generate.
	 * @param renderbuffers Specifies an array in which the generated renderbuffer object names are stored.
	 * @error GL_INVALID_VALUE is generated if n is negative
	 * @see glIsRenderbuffer
	 * @see glGetRenderbufferParameteriv
	 * @see glBindRenderbuffer
	 * @see glFramebufferRenderbuffer
	 * @see glDeleteRenderbuffers
	 */
	void glGenRenderbuffers(GLsizei n, GLuint *renderbuffers);
	/**
	 * attach a renderbuffer as a logical buffer to the currently bound framebuffer object
	 *
	 *
	 * @param target Specifies the framebuffer target. target must be GL_DRAW_FRAMEBUFFER,
	 * 		GL_READ_FRAMEBUFFER, or GL_FRAMEBUFFER. GL_FRAMEBUFFER is equivalent to GL_DRAW_FRAMEBUFFER.
	 * @param attachment Specifies the attachment point of the framebuffer.
	 * @param renderbuffertarget Specifies the renderbuffer target and must be GL_RENDERBUFFER.
	 * @param renderbuffer Specifies the name of an existing renderbuffer object of type renderbuffertarget to attach.
	 * @return
	 */
	GLsync glFramebufferRenderbuffer(GLenum target, GLenum attachment, GLenum renderbuffertarget,
			GLuint renderbuffer);
};

}
}

#endif /* IO_DISPLAY_GL_GLES3_H_ */
