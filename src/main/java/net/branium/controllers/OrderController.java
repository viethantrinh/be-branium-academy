package net.branium.controllers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.payment.OrderItemRequest;
import net.branium.dtos.payment.OrderResponse;
import net.branium.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderResponse>> checkOut(@RequestBody List<OrderItemRequest> request) {
        OrderResponse orderResponse = orderService.checkOut(request);
        var response = ApiResponse.<OrderResponse>builder()
                .message("create order successful")
                .result(orderResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/payment")
    public ResponseEntity<ApiResponse<?>> payment(@RequestBody Map<String, Integer> request) {
        Map<String, String> responseBody = orderService.createPayment(request.get("orderId"));
        var response = ApiResponse.<Map<String, String>>builder()
                .message("create payment successful")
                .result(responseBody)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
