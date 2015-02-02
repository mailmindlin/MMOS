# Makefile for building on Windows
# Written by Mailmindlin
TARCH?=armv6
OUT?=kern
BIN?=bin
LDFLAGS?=-I $(realpath $(BIN)) -I $(realpath $(BIN))/asm -I $(realpath $(BIN))/cpp
CPPFILES=$(realpath $(BIN)/cpp/kernel.o)
ASMFILES=$(realpath $(BIN)/asm/loader.o)
all:
	@echo Using mkv: OSX
init:
	mkdir -p $(BIN)
	@make -C ASM init BIN=$(realpath $(BIN)/asm)
	@make -C CPP init BIN=$(realpath $(BIN)/cpp)
	@make -C JAVA init BIN=$(realpath $(BIN)/java)
clean:
	@echo Cleaning...
	rm $(BIN)/*.o
	@make -C ASM clean BIN=$(realpath $(BIN)/asm)
	@make -C CPP clean BIN=$(realpath $(BIN)/cpp)
	@make -C JAVA clean BIN=$(realpath $(BIN)/java)
	@echo Done.
build: compile link
compile: init
	@echo Compiling...
	@make -C ASM compile BIN=$(realpath $(BIN)/asm)
	@make -C CPP compile BIN=$(realpath $(BIN)/cpp)
	@make -C JAVA compile BIN=$(realpath $(BIN)/java)
	@echo Done compiling.
link:
	@echo Linking...
	@make -C ASM link BIN=$(realpath $(BIN)/asm)
	@make -C CPP link BIN=$(realpath $(BIN)/cpp)
	@make -C JAVA link BIN=$(realpath $(BIN)/java)
	LDFLAGS+=-arch $(TARCH) -o $(OUT)
	LDFLAGS+=-nostdlib -nodefaultlibs -lgcc
#	LDFLAGS+=-T linker/linker.ld						#for linux (std)
	LDFLAGS+=-exported_symbols_list linker/linker.ld	#for osx
	gcc $(CPPFILES) $(ASMFILES) $(LDFLAGS)
help:
	@echo "Usage: make <command> [arg0 arg1...] [var=value var=value...]"
	@echo "\n"
	@echo "Command       Description"
	@echo "build         Compiles, links, and tests the file"
	@echo "clean         Removes the precompiled binaries and intermediate files (i.e., non-source files)"
	@echo "compile       Compiles the code to the output folder"
	@echo "help          Displays this menu"
	@echo "link          Links precompiled code"
	@echo "test          Tests the output (not yet implemented)"
	@echo "version       Echo the version of this build"
	@echo "\n"
	@echo "Variable     Default Value	Description"
	@echo "TARCH        armv6           Target architecture to compile for"
	@echo "BIN          bin/            Where to put the compiled binaries/intermediate files"
	@echo "OUT          kern            Output file (compiled operating system)"