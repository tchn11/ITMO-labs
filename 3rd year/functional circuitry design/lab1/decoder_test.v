`timescale 1ns / 1ps

module decoder_test(

    );
    
    integer i;
    
    reg [7:0] test_val;
    wire [2:0] result;
    
    decoder decoder_unit(
        .v(test_val),
        .out(result)
    );
    
    initial begin
        for (i = 0; i < 8; i = i + 1) begin
            test_val = 0;
            test_val[i] = 1;
            
            #10;

            
        end

        #10;
        $stop;
    end
endmodule
