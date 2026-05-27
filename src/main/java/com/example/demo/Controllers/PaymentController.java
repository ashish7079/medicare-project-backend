package com.example.demo.Controllers;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.PaymentRequest;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

	 private static final String KEY =
	            "rzp_test_SrL6YBZOpvbhDg";

	    private static final String SECRET =
	            "SaEjQOwtEtcD2KpU1C26ZwuA";

	    @PostMapping("/create-order")
	    public String createOrder(

	            @RequestBody PaymentRequest request

	    ) throws Exception {

	        RazorpayClient client =
	                new RazorpayClient(KEY, SECRET);

	        JSONObject object =
	                new JSONObject();

	        // Dynamic Amount
	        object.put(
	                "amount",
	                request.getAmount() * 100
	        );

	        object.put("currency", "INR");

	        object.put("receipt", "txn_123");

	        Order order =
	                client.orders.create(object);

	        return order.toString();
	    }
	
}
