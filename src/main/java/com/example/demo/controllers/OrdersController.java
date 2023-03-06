package com.example.demo.controllers;

import ch.qos.logback.classic.pattern.DateConverter;
import com.example.demo.models.Orders;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class OrdersController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/AddOrder")
    public String ordersPage(Model model) {
        return "add_order";
    }
    @PostMapping("/AddOrder/new")
    public String newOrder(Model model,
                           @RequestParam int number, @RequestParam Date date, @RequestParam String title,
                           @RequestParam int price, @RequestParam Date deadline)
    {
        orderRepository.save(new Orders(number, date, title, price, deadline));
        return "redirect:/";
    }
    @GetMapping("/find")
    public String find(Model model) {
        return "find_order";
    }
    @PostMapping("find/number")
    public String findByNumber(Model model, @RequestParam int number) {
        Object order = orderRepository.findByNumber(number);
        model.addAttribute("orders", order);
        return "main";
    }
    @PostMapping("find/date")
    public String findByDates(Model model, @RequestParam Date date1, @RequestParam Date date2) {
        Iterable<Orders> ordersList = orderRepository.findByDateBetween(date1, date2);
        AtomicInteger allPrice =  new AtomicInteger();
        ordersList.forEach(orders -> {
            int i = orders.getPrice();
            allPrice.addAndGet(i);
        });
        model.addAttribute("orders", ordersList);
        model.addAttribute("allPrice", allPrice);
        return "main";
    }
}