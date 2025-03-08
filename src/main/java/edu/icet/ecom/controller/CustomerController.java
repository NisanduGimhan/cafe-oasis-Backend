package edu.icet.ecom.controller;

import edu.icet.ecom.dto.Customer;
import edu.icet.ecom.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

    final CustomerService service;

    @PostMapping("/add")
    public void add(@RequestBody Customer customer){service.save(customer);}

    @GetMapping("/search/{id}")
    public Customer search(@PathVariable Long id){
        return service.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){service.deleteById(id);}

    @PutMapping("/update")
    public void update(@RequestBody Customer customer){service.update(customer);}

    @GetMapping("/get-all")
    public List<Customer> getall(){

        return service.findall();
    }




}
