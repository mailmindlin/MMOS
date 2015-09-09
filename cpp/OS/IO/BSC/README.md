#Broadcom Serial Controller
##Introduction
The raspberry pi has 3 BSCs &#40;BSC0-BSC2&#41; that each are I<sup>2</sup>C masters. I<sup>2</sup>C is a protocol for data exchange between smaller peripherals (i.e., gyroscopes) and microcontrolers (in this case, the Raspberry Pi).

##Usage/Examples
You first have to look at the connected I<sup>2</sup>C devices to your chosen [BSC.h](BSC.h): <code>BSC::BSC0->search()</code> will return a list of integers of the addresses of the addresses of I<sup>2</sup>C devices attached to the BSC.

Then, you can call <code>BSC::BSC0->getDevice(<i>address</i>);</code>, and you get a (wrapper object)[I2CDevice.h] that you can read/write raw data from/to.