import os

def readDataset(filename, collum_with_key=0, code="utf-8", line_break_symb="\n", element_break_symb=",",
                num_of_output_rows=None):
    if num_of_output_rows is None:
        num_of_output_rows = [4, 7, 10, 17]     #[5, 8, 17, 19]
    keys = []
    attributes = [[] for i in range(len(num_of_output_rows))]
    file = os.open(filename, os.O_RDONLY)
    let = ""
    symbs_in_row = 0
    while let != line_break_symb:
        let = os.read(file, 1).decode(code)
        symbs_in_row += 1
    os.lseek(file, 0, os.SEEK_SET)
    while True:
        row = os.read(file, symbs_in_row).decode(code)
        if row == "":
            break
        splited_row = row.split(element_break_symb)
        collumn = 0
        for i in range(len(splited_row)):
            if i == collum_with_key:
                keys.append(splited_row[i].strip())
            else:
                if i in num_of_output_rows:
                    attributes[collumn].append(splited_row[i].strip())
                    collumn += 1

    return keys, attributes
