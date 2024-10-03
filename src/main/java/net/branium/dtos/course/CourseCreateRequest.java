package net.branium.dtos.course;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateRequest {
    @NotNull(message = "course's title must not be null")
    private String title;

    @NotNull(message = "course's short description must not be null")
    private String shortDescription;

    @NotNull(message = "course's full description must not be null")
    private String fullDescription;

    @NotNull(message = "course's price must not be null")
    @Positive(message = "course's price must not be negative number")
    private BigDecimal price;

    @NotNull(message = "course's discount price must not be null")
    @Positive(message = "course's discount price must not be negative number")
    private BigDecimal discountPrice;
}
