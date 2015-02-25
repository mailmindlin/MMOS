/*
 * RaspberryPi.h
 *
 *  Created on: Feb 18, 2015
 *      Author: wfeehery17
 */

#ifndef IO_RASPBERRYPI_H_
#define IO_RASPBERRYPI_H_
#ifndef EXC
#define EXC
#endif

EXC class RaspberryPi {
public:
	static int getRevision() {
		return MODEL_B;
	}
	static const int MODEL_A = 1;
	static const int MODEL_B = 2;
	static const int MODEL_2 = 3;
	static const int MODEL_CM = 4;
};

#endif /* IO_RASPBERRYPI_H_ */
