package net.branium.domains;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
    @EqualsAndHashCode.Include
    private Integer id;         // id
    private String name;        // name
    private Boolean enabled = true;    // default: true
}
