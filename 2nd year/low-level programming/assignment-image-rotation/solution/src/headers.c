#include "headers.h"
#include "image.h"
#include "padding.h"
#include <stdbool.h>
#include <stdlib.h>


struct bmp_header create_headers(const struct image * img){
	struct bmp_header header = {0};
	header.bfType = 0x4D42;
        header.bfileSize = sizeof(struct bmp_header) + image_get_size_bytes(img) + padding_size_bytes(img);
        header.bfReserved = 0;
        header.bOffBits = 54;
        header.biSize = 40;
        header.biWidth = img->width;
        header.biHeight = img->height;
        header.biPlanes = 1;
        header.biBitCount = 24;
        header.biCompression = 0;
        header.biSizeImage = header.bfileSize - 54;
        header.biXPelsPerMeter = 2834;
        header.biYPelsPerMeter = 2834;
        header.biClrUsed = 0;
        header.biClrImportant = 0;
        return header;
}

bool header_validation(struct bmp_header const * header){
	return header->biHeight > 0 && header->biWidth > 0 && header->bfType == 0x4D42;
}
