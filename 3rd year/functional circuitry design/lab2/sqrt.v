`timescale 1ns / 1ps

module sqrt(
        input clk_i,
        input rst_i,
        input [7:0] x_bi,
        input start_i,
        output busy_o,
        output reg [7:0] y_bo
    );
    
    localparam IDLE = 1'b0;
    localparam WORK = 1'b1;

    reg [7:0] x;
    reg [7:0] m;
    reg [7:0] y;
    wire [7:0] y_shifted;
    reg [7:0] b;
    reg state;
    wire end_step;
    wire [7:0] b_tmp;
    
    assign end_step = (m == 0);
    assign busy_o = state;
    assign y_shifted = y >> 1;
    assign b_tmp = y | m;
    
    always @(posedge clk_i)
        if (rst_i) begin
            y <= 0;
            y_bo <= 0;
            state <= IDLE;
        end 
        else begin
            case (state)
            IDLE:
                if (start_i) begin
                    state <= WORK;
                    x <= x_bi;
                    m <= 8'b01000000;
                    b <= 0;
                    y_bo <= 0;
                    y <= 0;
                end
            WORK:
                if (end_step) begin
                    state <= IDLE;
                    y_bo <= y;
                end else begin
                    b <= b_tmp;
                    if (x >= b_tmp) begin
                        x <= x - b_tmp;
                        y <= y_shifted | m;
                    end 
                    else begin
                        y <= y_shifted;
                    end
                    m <= m >> 2;
                end
            endcase
        end
        
endmodule
