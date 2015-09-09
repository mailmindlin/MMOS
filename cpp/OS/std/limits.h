/*
 * limits.h
 *
 *  Created on: Feb 19, 2015
 *      Author: mailmindlin
 */

#ifndef STD_LIMITS_H_
#define STD_LIMITS_H_
/**
 * Number of bits in a char object (byte)
 */
#define CHAR_BITS	8
/**
 * Minimum value for an object of type signed char
 */
#define SCHAR_MIN	-127
/**
 * Maximum value for an object of type signed char
 */
#define SCHAR_MAX	127
/**
 * Maximum value for an object of type unsigned char
 */
#define UCHAR_MAX	255
/**
 * Minimum value for an object of type char
 */
#define CHAR_MIN	SCHAR_MIN
/**
 * Maximum value for an object of type char
 */
#define CHAR_MAX	SCHAR_MAX
/**
 * Maximum number of bytes in a multibyte character, for any locale
 */
#define	MB_LEN_MAX	1
/**
 * Minimum value for an object of type short int
 */
#define SHRT_MIN	-32767
/**
 * Maximum value for an object of type short int
 */
#define SHRT_MAX	32767
/**
 * Maximum value for an object of type unsigned short int
 */
#define USHRT_MAX	65535
/**
 * Minimum value for an object of type int
 */
#define INT_MIN		-32767
/**
 * Maximum value for an object of type int
 */
#define INT_MAX		32767
/**
 * Maximum value for an object of type unsigned int
 */
#define UINT_MAX	65535
/**
 * Minimum value for an object of type long int
 */
#define LONG_MIN	-2147483647
/**
 * Maximum value for an object of type long int
 */
#define LONG_MAX	2147483647
/**
 * Maximum value for an object of type unsigned long int
 */
#define ULONG_MAX	4294967295
/**
 * Minimum value for an object of type long long int
 */
#define LLONG_MIN	-9223372036854775807
/**
 * Maximum value for an object of type long long int
 */
#define LLONG_MAX	9223372036854775807
/**
 * Maximum value for an object of type unsigned long long int
 */
#define ULLONG_MAX	18446744073709551615

#define SIZE_T_MAX ULONG_MAX
#endif /* STD_LIMITS_H_ */
