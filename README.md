# MMOS
JVM OS for Raspberry Pi &amp; similar embedded systems
### To set up:
	First, download yagarto to ./yagarto. You can download it from:

	OS X:
		[http://iweb.dl.sourceforge.net/project/yagarto/YAGARTO%20for%20Mac%20OS%20X/20121222/yagarto-bu-2.23.1_gcc-4.7.2-c-c%2B%2B_nl-1.20.0_gdb-7.5.1_eabi_intelmac_20121222.dmg]
		(put the DMG in /yagarto and install it)
	
	Windows:
		(to be added)
	
	Build ./bin/maker.jar from ./java/Make/ (I'll put someting in the Makefile for this soon)
	Then run navigate to this folder in terminal/command prompt, and run:
	For Raspberry Pi 1:
		'make all'
	For Raspberry Pi 2:
		'make all TARGET=rpi2'
###To Do List:
	- Implement more high level display stuff (color format support, OpenGL, transparencies)
	- Started to add stuff to build java projects to Makefile
	- Maybe switch to gradle because it can generate an eclipse workspace (I think) and it might be easier to program for
	- Fix string/object problem
	- Switch to assembly for some high-usage functions, such as Display::GPU::writeMailbox, because they can be about 50% of the size that they are right now, by my estimates.
	- Add multithread/timeslicing support, as soon as I figure out how to do that.
	- Start writing the kernel/JRE/File system stuff to be able to really have some fun
		(maybe start this before some of the other features, so I can check if each build works)
	- Add drivers for more peripherals (GPIO is started, but I should start USB/Ethernet/PiCam thing/gamepad drivers)