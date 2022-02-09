#include "error_printer.h"
#include "main_logic.h"

int main(int argc, char **argv) {	
	if (argc != 3){
		print_error("Неверные аргументы");
		return 1;
	}
	
	return work(argv);
}
