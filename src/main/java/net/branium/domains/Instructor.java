package net.branium.domains;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Instructor {
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    private String detail;
}
