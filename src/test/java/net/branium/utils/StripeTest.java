package net.branium.utils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCancelParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.junit.jupiter.api.Test;

public class StripeTest {

    @Test
    void stripePaymentIntentTest() throws StripeException {

        // This is your test secret API key.
        Stripe.apiKey = "sk_test_51Q2psLDmtILFtFXBrBDezDUOCI1ptshMVrd69SUafboSO0tlMeAOiQowE06xoe3s7sxgC5S1ULVFlEoRlzzuForx00oo0xZWYG";


        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(2223L)
                        .setCurrency("usd")
                        // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);

//        CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret(), paymentIntent.getId());
        Gson gson = new Gson();
        String json = gson.toJson(paymentIntent);

        System.out.println(paymentIntent.getId());
        System.out.println(json);
    }

    @Test
    void cancelPaymentIntent() throws StripeException {
//        // This is your test secret API key.
//        Stripe.apiKey = "sk_test_51Q2psLDmtILFtFXBrBDezDUOCI1ptshMVrd69SUafboSO0tlMeAOiQowE06xoe3s7sxgC5S1ULVFlEoRlzzuForx00oo0xZWYG";
//
//        PaymentIntent rresource = PaymentIntent.retrieve("pi_3Q7fA1DmtILFtFXB0WTPZ8ao");
//        PaymentIntentCancelParams params = PaymentIntentCancelParams.builder().build();
//        PaymentIntent paymentIntent = rresource.cancel(params);
//        System.out.println(paymentIntent);
    }
}
