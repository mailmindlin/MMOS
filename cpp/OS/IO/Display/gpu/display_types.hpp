/*
 * display_types.h
 * I got a lot from Raspberry Pi's userland repo (/interface/vctypes/vc_display_types.h)
 *  Created on: Apr 20, 2015
 *      Author: wfeehery17
 */

#ifndef IO_DISPLAY_GPU_DISPLAY_TYPES_HPP_
#define IO_DISPLAY_GPU_DISPLAY_TYPES_HPP_
namespace Displays {
namespace GPU {
	//enums of display input format
	typedef enum DISPLAY_INPUT_FORMAT_T {
		VCOS_DISPLAY_INPUT_FORMAT_INVALID = 0,
		VCOS_DISPLAY_INPUT_FORMAT_RGB888,
		VCOS_DISPLAY_INPUT_FORMAT_RGB565
	};
	// Enum determining how image data for 3D displays has to be supplied
	typedef enum FORMAT_3D_T {
		FORMAT_3D_UNSUPPORTED = 0,   // default
		FORMAT_3D_INTERLEAVED,       // For autosteroscopic displays
		FORMAT_3D_SBS_FULL_AUTO,     // Side-By-Side, Full Width (also used by some autostereoscopic displays)
		FORMAT_3D_SBS_HALF_HORIZ,    // Side-By-Side, Half Width, Horizontal Subsampling (see HDMI spec)
		FORMAT_3D_TB_HALF,           // Top-bottom 3D
		FORMAT_3D_FRAME_PACKING,     // Frame Packed 3D
		FORMAT_3D_FORMAT_MAX
	};

	//enums of display types
	typedef enum INTERFACE_T {
		INTERFACE_MIN,
		INTERFACE_SMI,
		INTERFACE_DPI,
		INTERFACE_DSI,
		INTERFACE_LVDS,
		INTERFACE_MAX
	};

	/* display dither setting, used on B0 */
	typedef enum DITHER_T {
	   DITHER_NONE   = 0,   /* default if not set */
	   DITHER_RGB666 = 1,
	   DITHER_RGB565 = 2,
	   DITHER_RGB555 = 3,
	   DITHER_MAX
	};

	//info struct
	typedef struct INFO_T {
	   //type
	   INTERFACE_T type;
	   //width / height
	   uint32_t width;
	   uint32_t height;
	   //output format
	   DISPLAY_INPUT_FORMAT_T input_format;
	   //interlaced?
	   uint32_t interlaced;
	   /* output dither setting (if required) */
	   DITHER_T output_dither;
	   /* Pixel frequency */
	   uint32_t pixel_freq;
	   /* Line rate in lines per second */
	   uint32_t line_rate;
	   // Format required for image data for 3D displays
	   FORMAT_3D_T format_3d;
	   // If display requires PV1 (e.g. DSI1), special config is required in HVS
	   uint32_t use_pixelvalve_1;
	   // Set for DSI displays which use video mode.
	   uint32_t dsi_video_mode;
	   // Select HVS channel (usually 0).
	   uint32_t hvs_channel;
	};
}
}
#endif /* IO_DISPLAY_GPU_DISPLAY_TYPES_HPP_ */
