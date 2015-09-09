/*
 * GLMode.h
 *
 *  Created on: Mar 4, 2015
 *      Author: mailmindlin
 */

#ifndef IO_DISPLAY_GL_GLMODE_H_
#define IO_DISPLAY_GL_GLMODE_H_

namespace Displays {
namespace OpenGL {
enum GLMode {
	/**
	 * Treats each vertex as a single point. Vertex n defines point n. N points are drawn.
	 */
	GL_POINTS,
	/**
	 * Treats each pair of vertices as an independent line segment.
	 * Vertices 2 ⁢ n - 1 and 2 ⁢ n define line n.
	 * N 2 lines are drawn.
	 */
	GL_LINES,
	/**
	 * Draws a connected group of line segments from the first vertex to the last.
	 * Vertices n and n + 1 define line n.
	 * N - 1 lines are drawn.
	 */
	GL_LINE_STRIP,
	/**
	 * Draws a connected group of line segments from the first vertex to the last,
	 * then back to the first. Vertices n and n + 1 define line n. The last line, however,
	 * is defined by vertices N and 1 . N lines are drawn.
	 */
	GL_LINE_LOOP,
	/**
	 * Treats each triplet of vertices as an independent triangle.
	 * Vertices 3 ⁢ n - 2 , 3 ⁢ n - 1 , and 3 ⁢ n define triangle n.
	 * N 3 triangles are drawn.
	 */
	GL_TRIANGLES,
	/**
	 * Draws a connected group of triangles.
	 * One triangle is defined for each vertex presented after the first two vertices.
	 * For odd n, vertices n, n + 1 , and n + 2 define triangle n.
	 * For even n, vertices n + 1 , n, and n + 2 define triangle n.
	 * N - 2 triangles are drawn.
	 */
	GL_TRIANGLE_STRIP,
	/**
	 * Draws a connected group of triangles.
	 * One triangle is defined for each vertex presented after the first two vertices.
	 * Vertices 1 , n + 1 , and n + 2 define triangle n.
	 * N - 2 triangles are drawn.
	 */
	GL_TRIANGLE_FAN,
	/**
	 * Treats each group of four vertices as an independent quadrilateral.
	 * Vertices 4 ⁢ n - 3 , 4 ⁢ n - 2 , 4 ⁢ n - 1 , and 4 ⁢ n define quadrilateral n.
	 * N 4 quadrilaterals are drawn.
	 */
	GL_QUADS,
	/**
	 * Draws a connected group of quadrilaterals.
	 * One quadrilateral is defined for each pair of vertices presented after the first pair.
	 * Vertices 2 ⁢ n - 1 , 2 ⁢ n , 2 ⁢ n + 2 , and 2 ⁢ n + 1 define quadrilateral n.
	 * N 2 - 1 quadrilaterals are drawn.
	 * Note that the order in which vertices are used to construct a quadrilateral from strip data is different from that used with independent data.
	 */
	GL_QUAD_STRIP,
	/**
	 * Draws a single, convex polygon. Vertices 1 through N define this polygon.
	 */
	GL_POLYGON
};
}
}



#endif /* IO_DISPLAY_GL_GLMODE_H_ */
