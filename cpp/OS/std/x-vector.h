/*
 * x-vector.h
 *
 *  Created on: Feb 27, 2015
 *      Author: wfeehery17
 */

#ifndef STD_X_VECTOR_H_
#define STD_X_VECTOR_H_
#ifdef __REALCOMP__
#include <vector>
#else
namespace std {
template<typename T>
class vector;
}
#endif
#endif /* STD_X_VECTOR_H_ */
