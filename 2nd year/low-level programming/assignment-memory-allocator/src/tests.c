#define _DEFAULT_SOURCE
#include <unistd.h>
#include <stdio.h>
#include "mem_internals.h"
#include "mem.h"
#include "util.h"
#include "tests.h"

static const size_t INITIAL_HEAP_SIZE = 10000;


void debug(const char* fmt, ...);

void* test_heap_init(){
	debug("Создаю кучу\n");
	void *heap = heap_init(INITIAL_HEAP_SIZE);
	if (!heap)
		err("Ошибка создания кучи");
	debug("Куча создана\n\n");
	return heap;
}

static void map_new_pages(struct block_header* last_block){
	struct block_header *addr = last_block;
	void* test_addr = (uint8_t*) addr + size_from_capacity(addr->capacity).bytes;
	test_addr = mmap( (uint8_t*) (getpagesize() * ((size_t) test_addr / getpagesize() + (((size_t) test_addr % getpagesize()) > 0))), 1000, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS | MAP_FIXED_NOREPLACE,0, 0);
    debug(test_addr);
}

static struct block_header* get_block_from_contents(void* data){
	return (struct block_header*) ((uint8_t*) data - offsetof(struct block_header, contents));
}

void test1(struct block_header* heap){
	debug("Начат тест 1");
	void* data = _malloc(1000);
	if (data == NULL){
		err("Тест 1 провален, память не выделена");
	}
	debug_heap(stdout, heap);
	if(heap->is_free != false || heap->capacity.bytes != 1000)
		err("Тест 1 провален, память не отмечена как занятая или не совпадает по размеру");
	debug("Тест 1 пройден\n\n");
	_free(data);
}

void test2(struct block_header* heap){
	debug("Начат тест 2\n");
	void *data1 = _malloc(1000);
	void *data2 = _malloc(1000);
	if (data1 == NULL || data2 == NULL)
		err("Тест 2 провален, память не выделена");
	_free(data1);
	debug_heap(stdout, heap);
	struct block_header *data1_block = get_block_from_contents(data1);
	struct block_header *data2_block = get_block_from_contents(data2);
	if (data1_block->is_free == false)
		err("Тест 2 провален, блок не освобожден");
	if (data2_block->is_free == true)
		err("Тест 2 провален, освобожден не тот блок");
	debug("Tест 2 пройден\n\n");
	_free(data2);
}

void test3(struct block_header* heap){
	debug("Начат тест 3\n");
	void *data1 = _malloc(1000);
	void *data2 = _malloc(1000);
	void *data3 = _malloc(1000);
	if (data1 == NULL || data2 == NULL || data3 == NULL)
		err("Тест 3 провален, память не выделена");
	_free(data2);
	_free(data1);
	debug_heap(stdout, heap);
	struct block_header *data1_block = get_block_from_contents(data1);
	struct block_header *data3_block = get_block_from_contents(data3);
	if (data1_block->is_free == false)
		err("Тест 3 провален, блок не освобожден");
	if (data3_block->is_free == true)
		err("Тест 3 провален, освобожден не тот блок");
	if (data1_block->capacity.bytes != 2000 + offsetof(struct block_header, contents))
		err("Тест 3 провален, размер блока не верен");
	debug("Тест 3 пройден\n\n");
	_free(data3);
}

void test4(struct block_header* heap){
	debug("Начат тест 4\n");
	void *data1 = _malloc(10000);
	void *data2 = _malloc(10000);
	void *data3 = _malloc(5000);
	if (data1 == NULL || data2 == NULL || data3 == NULL)
		err("Тест 4 провален, память не выделенна");
	_free(data3);
	_free(data2);
	debug_heap(stdout, heap);
	struct block_header *data1_block = get_block_from_contents(data1);
	struct block_header *data2_block = get_block_from_contents(data2);
	if ((uint8_t *)data1_block->contents + data1_block->capacity.bytes != (uint8_t*) data2_block)
		err("Тест 4 провален, блок создан не после первого");
	debug("Тест 4 пройден\n\n");
	_free(data1);
}

void test5(struct block_header* heap){
	debug("Начат тест 5\n");
	void *data1 = _malloc(10000);
	if (data1 == NULL)
		err("Тест 5 провален, память не выделена");
	struct block_header *addr = heap;
	while (addr->next != NULL) addr = addr->next;
	map_new_pages(addr);
	void *data2 = _malloc(100000);
	debug_heap(stdout, heap);
	struct block_header *data2_block = get_block_from_contents(data2);
	if (data2_block == addr)
		err("Тест 5 провален, адресса совпадают");
	debug("Тест 5 пройден\n\n");
}
