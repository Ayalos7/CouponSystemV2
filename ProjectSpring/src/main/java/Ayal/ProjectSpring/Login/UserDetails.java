package Ayal.ProjectSpring.Login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetails {
    private String email;
    private String password;
    private String userType;
}
