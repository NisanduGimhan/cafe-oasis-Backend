package edu.icet.ecom.service;

import edu.icet.ecom.dto.Item;

import java.util.List;

public interface ItemService {
    void save(Item item);

    Item findById(Long id);

    void deleteById(Long id);

    void update(Item item);

    List<Item> findall();
}
