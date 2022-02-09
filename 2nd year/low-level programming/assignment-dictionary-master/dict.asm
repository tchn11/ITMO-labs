section .text
%include "lib.inc"
global find_word

find_word:
	.loop:
		push rdi
		push rsi
		add rsi, 8
		call string_equals
		pop rsi
		pop rdi
		test rax, rax
		jne .find_end
		mov rsi, [rsi]
		test rsi, rsi
		jz .not_find_end
		jmp .loop
	.find_end:
		mov rax, rsi
		ret
	.not_find_end:
		xor rax, rax
		ret
		

