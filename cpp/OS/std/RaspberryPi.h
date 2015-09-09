/*
 * RaspberryPi.h
 *
 *  Created on: Feb 18, 2015
 *      Author: mailmindlin
 */

#ifndef IO_RASPBERRYPI_H_
#define IO_RASPBERRYPI_H_
#ifndef EXC
#define EXC
#endif
#ifndef __RPI_MODEL
#define __RPI_MODEL MODEL_B
#if __REALCOMP__
#warning "No raspberry pi model specified, defaulting to 'model B'."
#endif
#endif
EXC class RaspberryPi {
public:
	static int getRevision() {
		return __RPI_MODEL;
	}
	static const int MODEL_A		= 1;
	static const int MODEL_B		= 2;
	static const int MODEL_A_PLUS	= 3;
	static const int MODEL_B_PLUS	= 4;
	static const int MODEL_B_2		= 3;
	static const int MODEL_CM		= 4;
};

#endif /* IO_RASPBERRYPI_H_ */
