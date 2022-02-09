section .data
%define buffer_size 256
%include "word.inc"

error_msg: db 'Ошибка ввода.', 10, 0
found_msg: db 'Результат:', 10, 0
not_found_msg: db 'Элемент не найден.', 10, 0
start_msg: db 'Введите слово:', 10, 0

section .text

extern find_word

%include "lib.inc"

global _start

_start:
	sub rsp, buffer_size
	mov rdi, start_msg
	mov rdx, 1
	call print_string
	mov rdi, rsp
	mov rsi, buffer_size
	push rdi
	call read_word
	test rax, rax
	jz .error
	pop rdi
	
	mov rsi, end
	call find_word
	test rax, rax
	jz .not_find
	add rax, 8
	push rax
	mov rsi, rax
	call string_length
	pop rsi
	add rax, rsi
	inc rax
	push rax
	
	mov rdi, found_msg
	mov rdx, 1
	call print_string
	
	pop rdi
	mov rdx, 1
	call print_string
	call print_newline
	add rsp, buffer_size
	xor rdi, rdi
	call exit
.error:
	mov rdi, error_msg
	mov rdx, 2
	call print_string
	add rsp, buffer_size
	mov rdi, 1
	call exit
.not_find:
	mov rdi, not_found_msg
	mov rdx, 2
	call print_string
	add rsp, buffer_size
	xor rdi, rdi
	call exit
	
