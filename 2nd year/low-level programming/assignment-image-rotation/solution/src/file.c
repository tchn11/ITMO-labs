#include "file.h"
#include <stdbool.h>
#include <stdio.h>

bool open_file(FILE **file, const char *name, const char *mode){
	if (!name)
		return false;
	*file = fopen(name, mode);
	if (*file == NULL)
		return false;
	else
		return true;
}

bool close_file(FILE **file){
	if (*file)
		return false;
	if (fclose(*file))
		return true;
	else
		return false;
}


