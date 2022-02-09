#include "tests.h"

void debug(const char* fmt, ...);

int main(){
	struct block_header *heap = (struct block_header*) test_heap_init();
	test1(heap);
	test2(heap);
	test3(heap);
	test4(heap);
	test5(heap);
	debug("Все тесты пройдены\n");
}

