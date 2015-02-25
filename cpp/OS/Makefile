ROOT?=/Users/wfeehery17/Dropbox/dev/MMOS/
MAKER?=$(ROOT)maker.jar
BIN?=$(ROOT)bin/
JBIN=$(BIN)java
ABIN=$(BIN)asm
CBIN=$(BIN)cpp
JSRC=$(ROOT)java
ASRC=$(ROOT)asm
CSRC=$(ROOT)cpp
LSRC=$(ROOT)linker
OUT=$(BIN)Kernel.img
JFLAGS?=-arch armv6 -targ rpi --src-asm $(ASRC) --src-java $(JSRC) --src-c++ $(CSRC) --src-ld $(LSRC) --bin-asm $(ABIN) --bin $(BIN) --bin-java $(JBIN) --bin-asm $(ABIN) -o $(OUT)
all:
	java -jar $(MAKER) build check $(JFLAGS)

clean:
	-java -jar $(MAKER) clean $(JFLAGS)