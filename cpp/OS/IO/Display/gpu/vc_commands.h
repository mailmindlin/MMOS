/*
 * vc_commands.h
 *
 *  Created on: Apr 20, 2015
 *      Author: wfeehery17
 */

#ifndef IO_DISPLAY_GPU_VC_COMMANDS_H_
#define IO_DISPLAY_GPU_VC_COMMANDS_H_
typedef enum  {
   // IMPORTANT - DO NOT ALTER THE ORDER OF COMMANDS IN THIS ENUMERATION
   // NEW FUNCTIONS SHOULD BE ADDED TO THE END, AND MUST ALSO BE ADDED TO
   // THE HOST SIDE FUNCTION TABLE IN display_server.c.

   // No function configured - do not use
   EDispmanNoFunction = 0,

   // Dispman pre-configure functions
   EDispmanGetDevices,
   EDispmanGetModes,

   // Dispman resource-related functions
   EDispmanResourceCreate,
   EDispmanResourceCreateFromImage,
   EDispmanResourceDelete,
   EDispmanResourceGetData,
   EDispmanResourceGetImage,

   // Dispman display-related functions
   EDispmanDisplayOpen,
   EDispmanDisplayOpenMode,
   EDispmanDisplayOpenOffscreen,
   EDispmanDisplayReconfigure,
   EDispmanDisplaySetDestination,
   EDispmanDisplaySetBackground,
   EDispmanDisplayGetInfo,
   EDispmanDisplayClose,

   // Dispman update-related functions
   EDispmanUpdateStart,
   EDispmanUpdateSubmit,
   EDispmanUpdateSubmitSync,

   // Dispman element-related functions
   EDispmanElementAdd,
   EDispmanElementModified,
   EDispmanElementRemove,
   EDispmanElementChangeSource,
   EDispmanElementChangeLayer,
   EDispmanElementChangeAttributes,

   //More commands go here...
   EDispmanResourceFill,    //Comes from uideck
   EDispmanQueryImageFormats,
   EDispmanBulkWrite,
   EDispmanBulkRead,
   EDispmanDisplayOrientation,
   EDispmanSnapshot,
   EDispmanSetPalette,
   EDispmanVsyncCallback,

   EDispmanMaxFunction
} DISPMANX_COMMAND_T;
#endif /* IO_DISPLAY_GPU_VC_COMMANDS_H_ */
