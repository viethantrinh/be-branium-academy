package net.branium.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.branium.domains.Role;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "gender")
    private Boolean gender;

    @JsonProperty(value = "birth_date")
    private LocalDate birthDate;

    @JsonProperty(value = "vip_level")
    private Integer vipLevel;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "roles")
    private Set<Role> roles = new HashSet<>();
}
