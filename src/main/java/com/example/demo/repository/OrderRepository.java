package com.example.demo.repository;

import com.example.demo.models.Orders;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Orders, Long> {
    Object findByNumber(int number);

    Iterable<Orders> findByDate(Date date);

    Iterable<Orders> findByDateBetween(Date date1, Date date2);
}
