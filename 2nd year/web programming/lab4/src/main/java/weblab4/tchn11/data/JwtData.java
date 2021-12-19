package weblab4.tchn11.data;

import lombok.*;

@Data
public class JwtData {
    @NonNull
    private String username;

    @NonNull
    private String jwt;
}
