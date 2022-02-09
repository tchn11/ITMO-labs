#ifndef TESTS_H
#define TESTS_H
#include "mem_internals.h"
#include "mem.h"

void* test_heap_init();
void test1(struct block_header* heap);
void test2(struct block_header* heap);
void test3(struct block_header* heap);
void test4(struct block_header* heap);
void test5(struct block_header* heap);
#endif
