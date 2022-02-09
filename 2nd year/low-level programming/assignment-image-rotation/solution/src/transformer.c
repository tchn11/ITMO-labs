#include "image.h"
#include "transformer.h"
#include <stdint.h>

struct image rotate(struct image const img) {
	struct image rotated;
	rotated.width = img.height;
	rotated.height = img.width;
	if(!image_malloc(&rotated))
		return rotated;
	for(size_t i = 0; i < img.height; i++)
		for(size_t j = 0; j < img.width; j++) {
			image_set_pixel(&rotated, image_get_pixel(&img, j, img.height - i - 1), i, j);
		}
	return rotated;
}


