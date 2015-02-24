# Compiles ATM OS for RPI version B
TARGET?=rpi

#calculate bin and source folders if not given
CDX:= $(abspath $(shell pwd))
BIN?=$(CDX)/bin/
CBIN?=$(BIN)cpp/
ABIN?=$(BIN)asm/
JBIN?=$(BIN)java/
#source folders
ASRC?=$(CDX)/asm/$(TARGET)/
JSRC?=$(CDX)/java/
CSRC?=$(CDX)/cpp/
LSRC?=$(CDX)/linker/

#output folders
IMG?=$(BIN)Kernel.img
ELF?=$(BIN)Kernel.elf
LISTING?=$(BIN)Kernel.list

# compiler location (for yagarto)
ARMGNU?=$(CDX)/yagarto/bin/arm-none-eabi-
#various compilers
CC=$(ARMGNU)g++
AS=$(ARMGNU)gcc
LD=$(ARMGNU)gcc
AR=$(ARMGNU)ar

#compiler flags
AFLAGS?=-mcpu=arm1176jzf-s -fpic -ffreestanding -c
CFLAGS?=-Wall -ffreestanding -fno-exceptions -fno-rtti -mcpu=arm1176jzf-s -fpic -Wextra -nostartfiles -ffreestanding -std=gnu++11 -Wpointer-arith -w -fexceptions
LDFLAGS?=-O2 -nostdlib -T $(LSRC)$(TARGET).ld

#libraries/bootloaders to precompile
ALIBSRC=start memcpy memset
ALIBS:=$(patsubst $(ASRC)%.s, $(ABIN)%.o, $(patsubst %, $(ASRC)%.s, $(ALIBSRC)))
LIBS:=$(ALIBS)
CLIBSRC=std std/lang std/util
CLIBS:=$(patsubst %,$(CBIN)%.a,$(CLIBSRC))
LIBS+=CLIBS

KERNELSRC=$(CSRC)Kernel.cpp
KERNEL:=$(patsubst $(CSRC)%.cpp, $(CBIN)%.o, $(KERNELSRC))

#objects (for compiling/cleaning)
OBJECTS:=$(BOOTLOADER)
OBJECTS+=$(LIBS)
OBJECTS+=$(KERNEL)
OBJECTS+=$(ELF)
OBJECTS+=$(LISTING)
OBJECTS+=$(IMG)

eko:
	@echo $(LIBS)
	@echo $(CLIBS)
build: $(OBJECTS)
	@echo Done Building.
compile: $(BOOTLOADER) compileLibs
	@echo Done compiling.
link:
	@echo Done linking.
mkclib: $(CLIBS)
	

clean:
	$(shell rm $(foreach object,$(OBJECTS), $(object)))
	
# Rule to make the libraries
$(ABIN)%.o: $(ASRC)%.s $(ABIN)
	@echo Compiling $@
	$(AS) $(AFLAGS) $< -o $@

$(CBIN)%.o: $(CSRC)%.cpp $(CBIN) 
	@echo Compiling $@
	$(CC) $(CFLAGS) $< -o $@ $(LIBS)
%.elf: $(BOOTLOADER) $(KERNEL)
	$(LD) $(LDFLAGS) $(BOOTLOADER) $(KERNEL) -o $@
#compile library
$(CBIN)/%.a:
	LIB=std
	SRC=$(CSRC)stdlib/stdlib.cpp
	$(CC) -c -D $(patsubst %,__LIB_$(shell echo % | tr a-z A-Z), $(NAME)) $< -o $(patsubst $(CSRC)%.cpp,$(CBIN)%.o,$<) $(ALIBS)
	$(AR) rvs $@ $(patsubst $(CSRC)%.cpp,$(CBIN)%.o,$<)


