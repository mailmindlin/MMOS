/*
 * vcutil.cpp
 *
 *  Created on: Apr 20, 2015
 *      Author: wfeehery17
 */

#include "vcutil.hpp"

#include "../../../std/x-stddef.h"
#include "../../../std/x-stdint.h"
#include "display_types.hpp"
#include "gputypes.h"
#include "vc_commands.h"

namespace Displays {
namespace GPU {
/***********************************************************
 * Name: dispmanx_wait_for_reply
 *
 * Arguments: response buffer, buffer length
 *
 * Description: blocked until something is in the buffer
 *
 * Returns error code of vchi
 *
 ***********************************************************/
int32_t wait_for_reply(void *response, uint32_t max_length) {
   int32_t success = 0;
   uint32_t length_read = 0;
   do {
      //TODO : we need to deal with messages coming through on more than one connections properly
      //At the moment it will always try to read the first connection if there is something there
      //Check if there is something in the queue, if so return immediately
      //otherwise wait for the semaphore and read again
      success = vchi_msg_dequeue( dispmanx_client.client_handle[0], response, max_length, &length_read, VCHI_FLAGS_NONE );
   } while( length_read == 0 && vcos_event_wait(&dispmanx_message_available_event) == VCOS_SUCCESS );

   return success;

}
/***********************************************************
 * Name: bsend_command_reply
 *
 * Arguments: command, parameter buffer, parameter legnth, reply buffer, buffer length
 * From userland-master/interface/vmcs_host/vc_vchi_dispmanx.c:1218
 * Description: send a command and wait for its reponse (in a buffer)
 *
 * Returns: error code
 *
 ***********************************************************/
int32_t send_command_reply( uint32_t command, void *buffer, uint32_t length, void *response, uint32_t max_length) {
   VCHI_MSG_VECTOR_T vector[] = { {&command, sizeof(command)}, {buffer, length} };

   int32_t success = 0;
//   lock_obtain();
   success = vchi_msg_queuev( dispmanx_client.client_handle[0],
                               vector, sizeof(vector)/sizeof(vector[0]),
                               VCHI_FLAGS_BLOCK_UNTIL_QUEUED, NULL );
   if(success == 0)
      success = wait_for_reply(response, max_length);

//   lock_release();

   return success;
}
/***********************************************************
 * Name: display_get_info
 *
 * Arguments:
 *       uint32_t display
 *       MODEINFO_T * pinfo
 * From userland-master/interface/vmcs_host/vc_vchi_dispmanx.c:633
 * Description:
 *
 * Returns: VCHI error
 *
 ***********************************************************/
int display_get_info (uint32_t display, MODEINFO_T *pinfo) {
	GET_INFO_DATA_T info;
	int32_t success = send_command_reply(EDispmanDisplayGetInfo, &display, sizeof(display), &info, sizeof(info));
	if (success == 0) {
		pinfo->width = info.width;
		pinfo->height = info.height;
		pinfo->transform = (TRANSFORM_T) info.transform;
		pinfo->input_format = (DISPLAY_INPUT_FORMAT_T) info.input_format;
	}
	return (int) success;
}
}
}

