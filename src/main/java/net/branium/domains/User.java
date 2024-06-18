package net.branium.domains;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private String displayName;
    private Boolean gender;
    private LocalDate birthDate;
    private String avatar;
    private Integer vipLevel;
    private Role role;
    private String phoneNumber;
    private LocalDateTime createdTime;
}
