# Current directory
ROOT?=$(shell pwd)/
# For the binary files
BIN?=$(ROOT)bin/
# Not really used
JBIN?=$(BIN)java/
# For the compiled assembly files
ABIN?=$(BIN)asm/
# For the compiled C++ libraries
CBIN?=$(BIN)cpp/
# Where the java source code is
JSRC?=$(ROOT)java/
# Assembly source code
ASRC?=$(ROOT)asm/rpi
# C++ source code
CSRC?=$(ROOT)cpp/OS/
# linker script source
LSRC?=$(ROOT)linker/
# Output binary
OUT?=$(BIN)Kernel.img
ARGS?=
# You can compile for the raspberry pi version 2 by specifying 'TARGET=rpi2' in the command line.
# Otherwise, it defaults to rpi
ARCH?=armv6
TARGET?=rpi1
MAKER?=java -jar $(JBIN)maker.jar $(JFLAGS)

JFLAGS=-arch $(ARCH) -targ $(TARGET) --src-asm $(ASRC) --src-java $(JSRC) --src-c++ $(CSRC) --src-ld $(LSRC) --bin-asm $(ABIN) --bin $(BIN) --bin-java $(JBIN) --bin-asm $(ABIN) -o $(OUT) $(ARGS)
all:
	$(MAKER) clean build check
about:
	$(MAKER) about

build:
	$(MAKER) build

check:
	$(MAKER) check

clean:
	$(MAKER) clean $(JFLAGS)

compile:
	$(MAKER) compile

link:
	$(MAKER) link

mount:
	rm /Volumes/BOOT/Kernel.img
	cp $(OUT) /Volumes/BOOT/Kernel.img
	diskutil eject /dev/disk2