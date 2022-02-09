#ifndef PADDING_H
#define PADDING_H

#include "image.h"
#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>

uint8_t calculate_padding(size_t image_width);

size_t padding_size_bytes(struct image const *img);

bool file_write_padding(FILE* const out, size_t image_width);

#endif

