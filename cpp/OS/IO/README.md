#IO

This directory contains wrappers for low level peripherals and simple APIs for easier acceess. Not much is really implemented, as much of the control should probably be in Java

##[BSC](BSC)
Broadcom Serial Controller interface, for communication with I<sup>2</sup>C devices

##[Display](Display)
VidCore GPU APIs for HDMI/TV/Other display control, along with OpenGL ES 2 and some other nice APIs for graphics (maybe even windowing)

##[GPIO](GPIO)
An API for controlling some GPIO functions (digital output, digital input, possible expanders, PUD, etc; no I<sup>2</sup>C/UART/SPI here). This doesn't have much as-is.

##[SD](SD)
An API for low-level control of the SD card reader/writer

##[USB](USB)
USB API, for simpler access to the USB ports, and maybe some drivers for some devices.