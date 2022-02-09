#include "error_printer.h"
#include "stdio.h"

void print_error(char * error){
	fprintf(stderr, "%s \n", error);
}

