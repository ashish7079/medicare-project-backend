// =========================
// FULL OrderController.java
// =========================

package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.OrderRequest;
import com.example.demo.Repositorys.OrderRepo;
import com.example.demo.model.OrderMedicine;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderRepo repo;

    @PostMapping("/save-order")

    public String saveOrder(

            @RequestBody OrderRequest request

    ) {

        OrderMedicine order =
                new OrderMedicine();

        order.setMedicineName(
                request.getMedicineName()
        );

        order.setUserName(
                request.getUserName()
        );

        order.setAmount(
                request.getAmount()
        );

        order.setPaymentId(
                request.getPaymentId()
        );
        order.setAuthId(
                request.getAuthId()
        );

        order.setStatus("PAID");

        repo.save(order);

        return "Order Saved";
    }

    @GetMapping("/my-orders/{authId}")

    public List<OrderMedicine> myOrders(

            @PathVariable Long authId

    ) {

        return repo.findByAuthId(authId);
    }

   
    @GetMapping("/all-orders")

    public List<OrderMedicine> getOrders() {

        return repo.findAll();
    }

    @PutMapping("/update-status/{id}")

    public String updateStatus(

            @PathVariable Long id,

            @RequestParam String status

    ) {

        OrderMedicine order =
                repo.findById(id)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Order Not Found"
                                )
                        );

        order.setStatus(status);

        repo.save(order);

        return "Status Updated";
    }

    @PutMapping("/track-order/{id}")

    public String trackOrder(

            @PathVariable Long id,

            @RequestParam double latitude,

            @RequestParam double longitude,

            @RequestParam String estimatedTime

    ) {

        OrderMedicine order =
                repo.findById(id)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Order Not Found"
                                )
                        );

        order.setLatitude(latitude);

        order.setLongitude(longitude);

        order.setEstimatedTime(
                estimatedTime
        );

        repo.save(order);

        return "Tracking Updated";
    }

    @PutMapping("/assign-delivery/{id}")

    public String assignDelivery(

            @PathVariable Long id,

            @RequestParam String deliveryBoy

    ) {

        OrderMedicine order =
                repo.findById(id)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Order Not Found"
                                )
                        );

        order.setDeliveryBoy(
                deliveryBoy
        );

        repo.save(order);

        return "Delivery Boy Assigned";
    }
}