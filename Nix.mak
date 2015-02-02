# Common functions for OSX.mak and Linux.mak
TARCH?=armv6
OUT?=kern
BIN?=bin
LDFLAGS?=-I $(realpath $(BIN)) -I $(realpath $(BIN))/asm -I $(realpath $(BIN))/cpp
LDFLAGS+=-arch $(TARCH) -o $(OUT)
LDFLAGS+=-nostdlib -nodefaultlibs -lgcc
CPPFILES=$(realpath $(BIN)/cpp/kernel.o)
ASMFILES=$(realpath $(BIN)/asm/loader.o)
ASMBIN=$(realpath $(BIN))/asm
CPPBIN=$(realpath $(BIN))/cpp
JAVABIN=$(realpath $(BIN))/java
init:
	@echo Initializing...
	mkdir -p $(BIN)
	@make -C ASM init BIN=$(ASMBIN)
	make -C CPP init BIN=$(CPPBIN)
	make -C JAVA init BIN=$(JAVABIN)
clean:
	@echo Cleaning...
	rm $(BIN)/*.o
	@make -C ASM clean BIN=$(realpath $(BIN)/asm)
	@make -C CPP clean BIN=$(realpath $(BIN)/cpp)
	@make -C JAVA clean BIN=$(realpath $(BIN)/java)
	@echo Done.
compile: init
	@echo Compiling...
	make -C ASM compile BIN=$(realpath $(BIN)/asm)
	@make -C CPP compile BIN=$(realpath $(BIN)/cpp)
	@make -C JAVA compile BIN=$(realpath $(BIN)/java)
	@echo Done compiling.
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