ROOT?=$(shell pwd)/
BIN?=$(ROOT)bin/
JBIN=$(BIN)java/
ABIN=$(BIN)asm/
CBIN=$(BIN)cpp/
JSRC=$(ROOT)java/
ASRC=$(ROOT)asm/rpi
CSRC=$(ROOT)cpp/OS/
LSRC=$(ROOT)linker/
OUT=$(BIN)Kernel.img
MAKER?=@java -jar $(JBIN)maker.jar $(JFLAGS)
JFLAGS?=-arch armv6 -targ rpi1 --src-asm $(ASRC) --src-java $(JSRC) --src-c++ $(CSRC) --src-ld $(LSRC) --bin-asm $(ABIN) --bin $(BIN) --bin-java $(JBIN) --bin-asm $(ABIN) -o $(OUT)
all:
	$(MAKER) build check
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