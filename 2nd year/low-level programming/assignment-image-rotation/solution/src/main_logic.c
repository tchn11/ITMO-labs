#include "bmp.h"
#include "error_printer.h"
#include "file.h"
#include "image.h"
#include "main_logic.h"
#include "transformer.h"
#include <stdbool.h>
#include <stdio.h>

int work(char **argv) {
	FILE* in_file = NULL;
	FILE* out_file = NULL;
	struct image in_image = {0};
	struct image out_image = {0};
	
	if (!(open_file(&in_file, argv[1], "rb") && open_file(&out_file, argv[2], "wb"))){
		print_error("Ошибка открытия файла");
		return 2;
	}
	
	if (!(from_bmp(in_file, &in_image))){
		print_error("Файл не BMP");
		close_file(&in_file);
		close_file(&out_file);
		return 3;
	}
	
	out_image = rotate(in_image);
	
	if (!out_image.image)
	{
		print_error("Ошибка поворота изображения");
		close_file(&in_file);
		close_file(&out_file);
		image_free(&in_image);
		return 4;
	}
	
	image_free(&in_image);
	
	if (!to_bmp(out_file, &out_image)){
		print_error("Ошибка записи в файл");
		close_file(&in_file);
		close_file(&out_file);
		image_free(&out_image);
		return 5;
	}
	
	image_free(&out_image);
	close_file(&in_file);
	close_file(&out_file);
	return 0;
}
