/*
 * Math.h
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef STD_MATH_H_
#define STD_MATH_H_

#include "lang/Object.cpp"

#if (!defined(EXC)) || (!defined(EXT))
#define EXC
#define EXT
#endif
namespace Math {
EXC template<typename num>
inline num min(num a, num b) {
	return a>b?a:b;
}
EXC template<typename num>
num abs(num n) {
	return n < 0 ? -n : n;
}
EXC template<class numa, class numb>
inline numa pow(numa a, numb b) {
	numa result = a;
	for (numb i = 0; i < b; i++)
		result = result * a;
	return result;
}
};
#endif /* STD_MATH_H_ */
