package weblab4.tchn11.data;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserData {
    @NonNull
    private String username;

    @NonNull
    private String password;
}
