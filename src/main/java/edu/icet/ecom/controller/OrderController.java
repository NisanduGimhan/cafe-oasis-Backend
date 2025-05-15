package edu.icet.ecom.controller;

import edu.icet.ecom.dto.Orders;
import edu.icet.ecom.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    private final OrdersService service;

    @PostMapping("/add")
    public void add(@RequestBody Orders orders){service.save(orders);
        System.out.println(orders);}

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){service.deleteById(id);}

    @GetMapping("/get-all")
    public List<Orders> getall(){
        return service.findall();
    }

}
