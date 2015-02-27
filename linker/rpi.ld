ENTRY(_start)
/*MEMORY {
	ram : ORIGIN = 0x80000000, LENGTH = 0x10000
}*/
SECTIONS
{
    /* Starts at LOADER_ADDR. */
    . = 0x8000;
    __start = .;
    __text_start = .;
    .text :
    {
        KEEP(*(.text.boot))
        *(.text)
    }
    . = ALIGN(4096); /* align to page size */
    __text_end = .;
 
    __rodata_start = .;
    .rodata :
    {
        *(.rodata)
    }
    . = ALIGN(4096); /* align to page size */
    __rodata_end = .;
	.ARM.extab   : { *(.ARM.extab* .gnu.linkonce.armextab.*) }
	PROVIDE_HIDDEN (__exidx_start = .);
	.ARM.exidx   : { *(.ARM.exidx* .gnu.linkonce.armexidx.*) }
	PROVIDE_HIDDEN (__exidx_end = .);
	.preinit_array     :
	{
		PROVIDE_HIDDEN (__preinit_array_start = .);
		KEEP (*(.preinit_array))
		PROVIDE_HIDDEN (__preinit_array_end = .);
	}
	.init_array     :
	{
		PROVIDE_HIDDEN (__init_array_start = .);
		KEEP (*(SORT(.init_array.*)))
		KEEP (*(.init_array))
		PROVIDE_HIDDEN (__init_array_end = .);
	}
	.fini_array     :
	{
	PROVIDE_HIDDEN (__fini_array_start = .);
	KEEP (*(SORT(.fini_array.*)))
	KEEP (*(.fini_array))
	PROVIDE_HIDDEN (__fini_array_end = .);
	}
	__data_start = .;
    .data :
    {
		*(.data .data.* .gnu.linkonce.d.*)
		SORT(CONSTRUCTORS)
	}
	. = ALIGN(4096); /* align to page size */
	__data_end = .;
	__bss_start = .;
	.bss :
	{
		bss = .;
		*(.bss)
	}
	. = ALIGN(4096); /* align to page size */
	_bss_end__ = . ; __bss_end__ = . ;
	__bss_end = . ;
	PROVIDE(bss_end = .);
	__end__ = . ;
	_end = .; PROVIDE (end = .);
	.stack         0x80000 :
	{
		_stack = .;
		*(.stack)
	}
	.ARM.attributes 0 : { KEEP (*(.ARM.attributes)) KEEP (*(.gnu.attributes)) }
	.note.gnu.arm.ident 0 : { KEEP (*(.note.gnu.arm.ident)) }
	/DISCARD/ : { *(.note.GNU-stack) *(.gnu_debuglink) *(.gnu.lto_*) }
}