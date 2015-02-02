# Makefile for building on OS X
# Written by Mailmindlin
include Nix.mak
all:
	@echo Using mkv: OSX
link:
	@echo Linking...
	@make -C ASM link BIN=$(realpath $(BIN)/asm)
	@make -C CPP link BIN=$(realpath $(BIN)/cpp)
	@make -C JAVA link BIN=$(realpath $(BIN)/java)
	LDFLAGS+=-exported_symbols_list linker/linker.ld	#for osx
	gcc $(CPPFILES) $(ASMFILES) $(LDFLAGS)