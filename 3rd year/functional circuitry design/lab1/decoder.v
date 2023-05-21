`timescale 1ns / 1ps

module decoder(
        input [7:0] v,
        output [2:0] out
    );
    
    or4 or4_1(
        .v1(v[1]),
        .v2(v[3]),
        .v3(v[5]),
        .v4(v[7]),
        .out(out[0]));

    or4 or4_2(
        .v1(v[2]),
        .v2(v[3]),
        .v3(v[6]),
        .v4(v[7]),
        .out(out[1]));

    or4 or4_3(
        .v1(v[4]),
        .v2(v[5]),
        .v3(v[6]),
        .v4(v[7]),
        .out(out[2]));

endmodule
