#ifndef BMP_H
#define BMP_H

#include "../include/headers.h"
#include "../include/image.h"
#include <stdbool.h>
#include <stdio.h>

bool from_bmp(FILE* file, struct image* img);
bool to_bmp(FILE* file, struct image const* img);

#endif

