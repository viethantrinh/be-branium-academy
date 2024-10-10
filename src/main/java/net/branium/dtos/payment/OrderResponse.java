package net.branium.dtos.payment;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.branium.dtos.course.CourseResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer orderId;
    private List<CourseResponse> orderDetails = new ArrayList<>();
    private BigDecimal totalDiscountPrice;
    private BigDecimal totalPrice;
}
