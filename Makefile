# Makefile for compiling, linking, running, testing, checking, etc. my operating system
# This file basically just detects the operating system, and diverts the requests to the OS-dependent
# files.
# Written by mailmindlin

# OS Check
VERSION=pre-alpha 0.0.1 experimental
AUTHOR=mailmindlin
ifeq ($(OS),Windows_NT)
    include Windows.mak
else
    UNAME_S := $(shell uname -s)
    ifeq ($(UNAME_S),Linux)
        include Linux.mak
	else
    	ifeq ($(UNAME_S),Darwin)
			include OSX.mak
    	endif
    endif
endif
version:
	@echo Written by mailmindlin
	@echo Version $(VERSION)
build: compile link