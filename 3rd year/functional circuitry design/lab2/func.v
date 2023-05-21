`timescale 1ns / 1ps

module func(
        input clk_i,
        input rst_i,
        input [7:0] a_bi,
        input [7:0] b_bi,
        input start_i,
        output busy_o,
        output reg [24:0] y_bo
    );
    
    localparam IDLE = 1'b0;
    localparam WORK = 1'b1;
    
    reg state;

    wire busy_sqrt;
    wire busy_pow;
    wire [7:0] sqrt_res;
    wire [23:0] pow_res;
    
    sqrt sq(.clk_i(clk_i), .rst_i(rst_i), .x_bi(b_bi), .start_i(start_i), .busy_o(busy_sqrt), .y_bo(sqrt_res));
    pow3 pow(.clk_i(clk_i), .rst_i(rst_i), .x_bi(a_bi), .start_i(start_i), .busy_o(busy_pow), .y_bo(pow_res));

    assign busy_o = state;

    always @(posedge clk_i)
        if (rst_i) begin
            y_bo <= 0;
            state <= IDLE;
        end 
        else begin
            case (state)
            IDLE:
                if (start_i) begin
                    state <= WORK;
                    y_bo <= 0;
                end
            WORK:
                begin
                    if ((~busy_sqrt) & (~busy_pow)) begin
                        state <= IDLE;
                        y_bo <= sqrt_res + pow_res;
                    end
                end
            endcase
        end
endmodule
