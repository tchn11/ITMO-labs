#include "image.h"
#include <malloc.h>
#include <stdbool.h>
#include <stdlib.h>

bool image_malloc(struct image *img){
	img->image = malloc(sizeof(struct pixel) * img->width * img->height);
	if (img->image == NULL)
		return false;
	else
		return true;
}

void image_free(struct image *img){
	free(img->image);
}

void image_set_pixel(struct image *img, struct pixel const p, size_t x, size_t y) {
    *(img->image + y * img->width + x) = p;
}

size_t image_get_size_bytes(struct image const* img) {
    return img->width * img->height * sizeof(struct pixel);
}

struct pixel image_get_pixel(struct image const* img, size_t x, size_t y) {
    return *(img->image + y * img->width + x);
}





