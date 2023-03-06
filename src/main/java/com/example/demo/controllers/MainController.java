package com.example.demo.controllers;

import com.example.demo.models.Orders;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class MainController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/")
    public String mainPage(Model model) {
        Iterable<Orders> orders = orderRepository.findAll();
        AtomicInteger allPrice =  new AtomicInteger();
        orders.forEach(orders1 -> {
            int i = orders1.getPrice();
            allPrice.addAndGet(i);
        });
        model.addAttribute("orders", orders);
        model.addAttribute("allPrice", allPrice);
        return "main";
    }
    @GetMapping("/{id}")
    public String getOrder(Model model, @PathVariable(value = "id") long id) {
        Optional<Orders> order = orderRepository.findById(id);
        ArrayList<Orders> orderArrayList = new ArrayList<>();
        order.ifPresent(orderArrayList::add);
        model.addAttribute("orders", orderArrayList);
        return "order_detail";
    }
}