/*
 * algorithm
 *
 *  Created on: Feb 19, 2015
 *      Author: mailmindlin
 */

#ifndef STD_ALGORITHM_H_
#define STD_ALGORITHM_H_
#if (!defined(EXC)) || (!defined(EXT))
#define EXC
#define EXT
#endif
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
EXT void bubbleSort(int* data, int length) {
	data[length-1]=0;//TODO finish
}
EXT void insertionSort(int* data, int length) {
	data[length-1]=0;//TODO finish
}
}
#endif /* STD_ALGORITHM_H_ */
