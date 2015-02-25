/*
 * algorithm
 *
 *  Created on: Feb 19, 2015
 *      Author: wfeehery17
 */

#ifndef STD_ALGORITHM_H_
#define STD_ALGORITHM_H_
namespace std {
EXT char toLower(char input) {
	if (input >= 'A' && input <= 'Z')
		return input + 32;
	return input;
}
EXT char toUpper(char input) {
	if (input >= 'a' && input <= 'z')
		return input - 32;
	return input;
}
}
#endif /* STD_ALGORITHM_H_ */
