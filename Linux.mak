# Makefile for building on Linux
# Written by Mailmindlin
include Nix.mak
all:
	@echo Using mkv: Linux
link:
	@echo Linking...
	@make -C ASM link BIN=$(realpath $(BIN)/asm)
	@make -C CPP link BIN=$(realpath $(BIN)/cpp)
	@make -C JAVA link BIN=$(realpath $(BIN)/java)
	LDFLAGS+=-T linker/linker.ld
	gcc $(CPPFILES) $(ASMFILES) $(LDFLAGS)