package net.branium.dtos.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIntentRequest {
    @NotNull(message = "orderId must not be null")
    @Positive(message = "orderId must be positive number")
    private int orderId;
}
