`timescale 1ns / 1ps

module pow3(
        input clk_i,
        input rst_i,
        input [7:0] x_bi,
        input start_i,
        output busy_o,
        output reg [23:0] y_bo
    );

    localparam IDLE = 3'b000;
    localparam WORK = 3'b001;
    localparam WAIT_MUL = 3'b010;
    localparam WORK2 = 3'b011;
    localparam RETURN_RES = 3'b100;

    reg [23:0] tmp_res;
    reg [2:0] state;
    
    wire [31:0] mul_a;
    wire [31:0] mul_b;
    reg mul_start;

    wire mul_busy;
    wire [23:0] mul_result;
    
    assign mul_a = x_bi | 32'b0;
    assign mul_b = tmp_res | 32'b0;
    assign busy_o = (state != IDLE);
    
    mult m(.clk_i(clk_i), .rst_i(rst_i), .a_bi(mul_a), .b_bi(mul_b), .start_i(mul_start), .busy_o(mul_busy), .y_bo(mul_result));
    
    always @(posedge clk_i)
        if (rst_i) begin
            y_bo <= 0;
            state <= IDLE;
            mul_start <= 0;
        end else begin
            case (state)
            IDLE:
                if (start_i) begin
                    state <= WORK;
                    tmp_res <= x_bi;
                    mul_start <= 1'b1;
                    state <= WORK;
                end
            WORK:
                begin
                    mul_start <= 0;
                    
                    state <=WAIT_MUL;
                end
            WAIT_MUL:  
                begin
                    if (~mul_busy) begin
                        tmp_res <= mul_result;
                        mul_start <= 1'b1;
                        state <= WORK2;
                    end
                end
            WORK2:
                begin
                    mul_start <= 0;
                    
                    state <= RETURN_RES;
                end
            RETURN_RES:
                begin
                    if (~mul_busy) begin
                        tmp_res <= mul_result;
                        state <= IDLE;
                        y_bo <= mul_result;
                    end
                end
            endcase
        end
endmodule
