section .text
 
 
; Принимает код возврата и завершает текущий процесс
exit: 
    mov rax, 60
    syscall 
    ret
    

; Принимает указатель на нуль-терминированную строку, возвращает её длину
string_length:
    xor rax, rax
    .loop: 
        cmp byte[rdi + rax], 0 ;try to find the end of the string
        je .exit
        inc rax
        jmp .loop
    .exit:
        ret

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    call string_length
    mov rdx, rax ;write string length to rax
    mov rsi, rdi ;put string sourse to rdi for syscall
    mov rax, 1 ;syscall write
    mov rdi, 1 ;descriptor
    syscall
    ret

; Принимает код символа и выводит его в stdout
; code lyes in rdi
print_char:
   push rdi 
   mov rax, 1 ;syscall write
   mov rdi, 1 ;descriptor
   mov rdx, 1 ;string length 1
   mov rsi, rsp ;write stack pointer to rsi to write the char
   syscall
   pop rdi 
   ret

; Переводит строку (выводит символ с кодом 0xA)
print_newline:
   mov rdi, 10
   jmp print_char

; Выводит беззнаковое 8-байтовое число в десятичном формате 
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.
print_uint:
    mov r10, rsp
    push 0 ;end of the string
    mov rax, rdi ;move rdi to rax for div
    mov r8, 10 ;const
    add rsp, 7
    .loop:
        xor rdx, rdx
        div r8 ;ratio into rax and the remainder into rdx
        add dl, '0' ;number to ASCII char
        mov dh, byte[rsp]
        add rsp, 1
        push dx ;put the char to stack
        cmp rax, 0 ;try to find the end of number
        jne .loop
    mov rdi, rsp ;give the sourse to print_string 
    call print_string
    mov rsp, r10
    ret
    
; Выводит знаковое 8-байтовое число в десятичном формате 
print_int:
    cmp rdi, 0 ;compare to 0
    jge .print
    push rdi ;save number before printing minus
    mov rdi, '-' ;minus
    call print_char ;print minus
    pop rdi 
    neg rdi ;change sign
    .print:
    call print_uint
    ret

; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе
; sourses are lying at rdi and rsi, result in rax
string_equals:
    mov r8, rdi
    call string_length
    mov rdi, rsi
    mov r9, rax
    call string_length
    cmp rax, r9
    jne .not_equals
    mov rdi, r8
    mov rcx, r9
    .loop: 
        cmp rcx, 0 ;check the end of the string
        jbe .stop
        mov dl, byte[rdi + rcx - 1] ;get the firts chars 
        cmp dl, byte[rsi + rcx - 1] 
        jne .not_equals ;end if not equals
        dec rcx
        jmp .loop
    .stop:
        mov rax, 1
        ret
    
    .not_equals:
        mov rax, 0
        ret


; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока
read_char:
    push 0 ;prepare buffer
    mov rdi, 0
    mov rax, 0
    mov rsi, rsp
    mov rdx, 1
    syscall ;read from stdin
    pop rax
    ret

; Принимает: адрес начала буфера, размер буфера
; Читает в буфер слово из stdin, пропуская пробельные символы в начале, .
; Пробельные символы это пробел 0x20, табуляция 0x9 и перевод строки 0xA.
; Останавливается и возвращает 0 если слово слишком большое для буфера
; При успехе возвращает адрес буфера в rax, длину слова в rdx.
; При неудаче возвращает 0 в rax
; Эта функция должна дописывать к слову нуль-терминатор
; rdi - adress, rsi - size of buf
read_word:
    mov r8, rdi
    mov r10, rdi ;save adress to remember the begining of string
    mov r9, rsi
    .loop: 
        call read_char
        cmp rax, 0x20 
        je .loop
        cmp rax, 0x9
        je .loop
        cmp rax, 0xA
        je .loop
    .word_reader:
        cmp rax, 0
        je .end
        cmp rax, 0x20
        je .end
        cmp rax, 0x9
        je .end
        cmp rax, 0xA
        je .end
        dec r9
        cmp r9, 0
        je .overflow
        mov byte[r8], al
        inc r8
        call read_char
        jmp .word_reader
    .overflow:
        xor rax, rax
        ret     
    .end:
        mov byte[r8], 0
        mov rdi, r10
        call string_length
        mov rdx, rax
        mov rax, r10
        ret

; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
parse_uint:
    xor rcx, rcx
    xor rax, rax
    xor r8, r8
    mov r10, 10
    .loop:
        mov al, byte[rdi+rcx] ;current char
        cmp al, "0"
        jb .fine
        cmp al, "9"
        ja .fine
        sub al, "0"
        push rax
 
        mov rax, r8
        mul r10
        mov r8, rax
        
        pop rax
        add r8, rax
        inc rcx
        jmp .loop
    .fine:
        mov rdx, rcx
        mov rax, r8
        ret

; Принимает указатель на строку, пытается
; прочитать из её начала знаковое число.
; Если есть знак, пробелы между ним и числом не разрешены.
; Возвращает в rax: число, rdx : его длину в символах (включая знак, если он был) 
; rdx = 0 если число прочитать не удалось
parse_int:
    mov al, byte[rdi]
    cmp al, 45
    jne parse_uint
    inc rdi
    call parse_uint
    cmp rdx, 0
    je .end
    neg rax
    inc rdx
   .end:
        ret
    

; Принимает указатель на строку, указатель на буфер и длину буфера
; Копирует строку в буфер
; Возвращает длину строки если она умещается в буфер, иначе 0
; rdi - sourse, rsi - buffer, rdx - buf length
string_copy:
    call string_length
    inc rax
    cmp rdx, rax
    jb .error
    xor rax, rax
    .loop:
        mov dl, byte[rdi+rax]
        mov byte[rsi+rax], dl
        inc rax
        cmp dl, 0
        jne .loop
    ret
    .error:
        xor rax, rax
        ret
