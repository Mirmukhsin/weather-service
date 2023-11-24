package pdp.domains;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthUser {
    private Integer id;
    @NotBlank(message = "username.null")
    private String username;
    @NotBlank(message = "password.null")
    private String password;
    @NotBlank(message = "role.null")
    private String role;
}
