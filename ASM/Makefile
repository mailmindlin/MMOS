BIN?=$(realpath ../bin/asm)
clean:
	@echo Cleaning ASM...
	@rm $(BIN)/*.o
	@echo Done.
init:
	mkdir -p $(BIN)
compile:
	@echo Compiling bootloader...
	nasm -f elf boot.asm -o $(BIN)/boot.o
	@echo Done.
link:
	@echo Done linking ASM