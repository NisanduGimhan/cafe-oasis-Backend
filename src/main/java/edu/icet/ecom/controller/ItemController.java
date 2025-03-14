package edu.icet.ecom.controller;

import edu.icet.ecom.dto.Customer;
import edu.icet.ecom.dto.Item;
import edu.icet.ecom.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
@CrossOrigin()
public class ItemController {

    private final ItemService service;

    @PostMapping("/add")
    public void add(@RequestBody Item item){service.save(item);
        System.out.println(item);}


    @GetMapping("/search/{id}")
    public Item search(@PathVariable Long id){
        return service.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){service.deleteById(id);}

    @PutMapping("/update")
    public void update(@RequestBody Item item){service.update(item);}

    @GetMapping("/get-all")
    public List<Item> getall(){

        return service.findall();
    }
}
