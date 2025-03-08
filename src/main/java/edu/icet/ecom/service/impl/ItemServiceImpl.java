package edu.icet.ecom.service.impl;

import edu.icet.ecom.dto.Customer;
import edu.icet.ecom.dto.Item;
import edu.icet.ecom.entity.CustomerEntity;
import edu.icet.ecom.entity.ItemEntity;
import edu.icet.ecom.repository.ItemRepository;
import edu.icet.ecom.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repo;
    private final ModelMapper mapper;
    @Override
    public void save(Item item) {
        repo.save(mapper.map(item, ItemEntity.class));
    }

    @Override
    public Item findById(Long id) {
        return mapper.map(repo.findById(id),Item.class);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void update(Item item) {
        repo.save(mapper.map(item, ItemEntity.class));
    }

    @Override
    public List<Item> findall() {
        List<Item> list=new ArrayList<>();
        List<ItemEntity> all = repo.findAll();

        all.forEach(itemEntity -> {
            list.add(mapper.map(itemEntity,Item.class));
        });
        return list;
    }
}
