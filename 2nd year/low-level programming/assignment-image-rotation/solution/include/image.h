#ifndef IMAGE_H
#define IMAGE_H

#include "../include/error_printer.h"
#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>


struct image {
	size_t width, height;
	struct pixel *image;
};

struct pixel {
	uint8_t r, g, b;
};

bool image_malloc(struct image *img);

void image_free(struct image *img);


void image_set_pixel(struct image *img, struct pixel const p, size_t x, size_t y);

struct pixel image_get_pixel(struct image const* img, size_t x, size_t y);

size_t image_get_size_bytes(struct image const* img);


#endif
