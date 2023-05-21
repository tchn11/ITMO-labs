`timescale 1ns / 1ps

module or4 (
        input v1,
        input v2,
        input v3,
        input v4,
        output out
    );

    wire nand_not_v1, nand_not_v2, nand_not_v3, nand_not_v4, nand_v12, nand_v34, nand_not_v12, nand_not_v34;
    
    nand(nand_not_v1, v1, v1);
    nand(nand_not_v2, v2, v2);
    nand(nand_not_v3, v3, v3);
    nand(nand_not_v4, v4, v4);
    
    nand(nand_v12, nand_not_v1, nand_not_v2);
    nand(nand_v34, nand_not_v3, nand_not_v4);

    nand(nand_not_v12, nand_v12, nand_v12);
    nand(nand_not_v34, nand_v34, nand_v34);

    nand(out, nand_not_v12, nand_not_v34);

endmodule
