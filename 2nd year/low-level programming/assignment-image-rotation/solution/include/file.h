#ifndef FILE_H
#define FILE_H

#include <stdbool.h>
#include <stdio.h>

bool open_file(FILE **file, const char *name, const char *mode);
bool close_file(FILE **file);

#endif
