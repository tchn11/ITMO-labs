ASM=nasm -f elf64 -o
LD=ld -o

main: main.o lib.o dict.o
	$(LD) $@ $^
	
main.o: main.asm word.inc colon.inc lib.inc
	$(ASM) $@ main.asm
	
lib.o: lib.asm
	$(ASM) $@ $^

dict.o: dict.asm
	$(ASM) $@ $^
	
clean:
	rm -rf main *.o

	
