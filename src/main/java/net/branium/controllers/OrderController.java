package net.branium.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.payment.*;
import net.branium.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderResponse>> checkOut(
            @RequestBody
            @Valid
            @Size(min = 1, message = "At least one item to create order")
            List<OrderItemRequest> request) {
        OrderResponse orderResponse = orderService.checkOut(request);
        var response = ApiResponse.<OrderResponse>builder()
                .message("create order successful")
                .result(orderResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/payment")
    public ResponseEntity<ApiResponse<PaymentIntentResponse>> payment(
            @RequestBody @Valid PaymentIntentRequest request) {
        PaymentIntentResponse responseBody = orderService.createPayment(request.getOrderId());
        var response = ApiResponse.<PaymentIntentResponse>builder()
                .message("create payment successful")
                .result(responseBody)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // order status in request only: "succeeded" or "failed" or "canceled"
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> updateOrderStatusAfterPayment(@RequestBody Map<String, String> request,
                                                                        @PathVariable(name = "id") String id) {
        String status = request.get("status");
        int orderId = Integer.parseInt(id);
        OrderDetailResponse orderDetailResponse = orderService.updateOrderStatus(orderId, status);
        var response = ApiResponse.<OrderDetailResponse>builder()
                .message("update order status successful")
                .result(orderDetailResponse)
                .build();
        return ResponseEntity.ok(response);
    }


}
