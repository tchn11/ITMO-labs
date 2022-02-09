#include "padding.h"

uint8_t calculate_padding(size_t image_width) {
    return image_width % 4;
}

size_t padding_size_bytes(struct image const *img) {
    return img->width * img->height * calculate_padding(img->width);
}

bool file_write_padding(FILE* const out, size_t image_width) {
    size_t zero = 0;
    if(!fwrite(&zero, calculate_padding(image_width), 1, out))
        return false;
    return true;
}


