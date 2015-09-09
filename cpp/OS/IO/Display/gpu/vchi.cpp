/*
 * vchi.cpp
 *
 *  Created on: Apr 20, 2015
 *      Author: wfeehery17
 */

#include "vchi.h"

#include "../../../std/x-vector.h"

namespace Displays {
namespace GPU {
typedef struct MESSAGE {
uint32_t command;
void* buffer;
uint32_t length;
};
void runMesage(MESSAGE* msg) {

}
}
}
