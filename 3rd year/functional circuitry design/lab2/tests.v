`timescale 1ns / 1ps

module tests(

    );
    
    reg [7:0] a [9:0];
    reg [7:0] b [9:0];
    integer res [9:0];

    reg clk;
    reg rst;
    reg start;
    
    wire [7:0] a_w;
    wire [7:0] b_w;
    wire clk_w;
    wire rst_w;
    wire start_w;
    
    wire busy_w;
    wire [24:0] out_w;
    
    integer i = 0;
    
    func fn(.clk_i(clk_w), .rst_i(rst_w), .a_bi(a_w), .b_bi(b_w), .start_i(start_w), .busy_o(busy_w), .y_bo(out_w));
    
    assign a_w = a[i];
    assign b_w = b[i];
    assign clk_w = clk;
    assign rst_w = rst;
    assign start_w = start;
    integer c;
    initial begin
        rst <= 1;
        clk <= 1;
        #1
        clk <= 0;
        rst <= 0;

        //test 1
        a[0] = 2;
        b[0] = 10;
        res[0] = 11;
        //test 2
        a[1] = 255;
        b[1] = 255;
        res[1] = 16581390;
        //test 3
        a[2] = 16;
        b[2] = 143;
        res[2] = 4107;
        //test 4
        a[3] = 43;
        b[3] = 11;
        res[3] = 79510;
        //test 5
        a[4] = 54;
        b[4] = 11;
        res[4] = 157467;
        //test 6
        a[5] = 0;
        b[5] = 0;
        res[5] = 0;
        //test 7
        a[6] = 1;
        b[6] = 1;
        res[6] = 2;
        //test 8
        a[7] = 2;
        b[7] = 2;
        res[7] = 9;
        //test 9
        a[8] = 100;
        b[8] = 100;
        res[8] = 1000010;
        //test 10
        a[9] = 200;
        b[9] = 200;
        res[9] = 8000014;
        
        for (i = 0; i < 10; i = i + 1) begin
            #1
            start <= 1;
            #1
            clk <= 1;
            #5 
            clk <= 0;
            start <= 0;
            #1
            c = 0;
            while (busy_w) begin
                c = c + 1;
                #1
                clk <= 1;
                #1
                clk <= 0;
            end
            #1
            if (out_w != res[i]) begin
                $display("Error on test %d", i);
            end
        end
        #1

        $stop;
    end
endmodule
