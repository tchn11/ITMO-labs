#include "bmp.h"
#include "error_printer.h"
#include "headers.h"
#include "image.h"
#include "padding.h"
#include <malloc.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>


static bool read_pixels(FILE *file, struct bmp_header const * headers, struct image * img){
	struct pixel pix = {0};
	img->width = headers->biWidth;
	img->height = headers->biHeight;
	if (!image_malloc(img))
		return false;
	for (size_t i = 0; i < img->height; i++){
		for (size_t j = 0; j < img->width; j++){
			if (fread(&pix, sizeof(struct pixel), 1, file) != 1){
				image_free(img);
				return false;
			}
			image_set_pixel(img, pix, j, i);
		}
		fseek(file, calculate_padding(img->width), SEEK_CUR);
	} 
	return true;
}

static bool write_pixels (FILE* file, struct image const* img) {
    struct pixel pixel;
    for (size_t i = 0; i < img->height; i++){
        for (size_t j = 0; j < img->width; j++) {
            pixel = image_get_pixel(img, j, i);
            if (fwrite(&pixel, sizeof (struct pixel), 1, file) != 1)
                return false;
        }
        if(!file_write_padding(file, img->width))
            return false;
    }
    return true;
}

bool from_bmp(FILE* file, struct image* img) {
    struct bmp_header header = {0};
    if(!file || !img)
        return false;
    if (fread(&header, sizeof(struct bmp_header), 1, file) != 1)
        return false;
    if (!header_validation(&header))
        return false;
    return read_pixels(file, &header, img);
}

bool to_bmp(FILE* file, struct image const* img) {
    struct bmp_header header;
    if(!file || !img)
        return false;
    header = create_headers(img);
    if (fwrite(&header, sizeof (struct bmp_header), 1, file) != 1)
        return false;
    return write_pixels(file, img);
}


