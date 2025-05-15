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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repo;
    private final ModelMapper mapper;
    @Override
    public void save(Item item) {
        if (item.getImageUrl() == null || item.getImageUrl().isEmpty()) {
            throw new IllegalArgumentException("Image URL cannot be null or empty");
        }

        ItemEntity entity = new ItemEntity(
                null, item.getItemNo(), item.getItemType(), item.getName(),
                item.getPrice(), item.getImageUrl()
        );

        repo.save(entity);
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
        if (item.getId() == null) {
            throw new IllegalArgumentException("ID is required for update");
        }

        Optional<ItemEntity> existing = repo.findById(item.getId());
        if (existing.isEmpty()) {
            throw new RuntimeException("Item not found with ID: " + item.getId());
        }

        repo.save(mapper.map(item, ItemEntity.class));
    }

    @Override
    public List<Item> findall() {
        List<Item> list=new ArrayList<>();
        List<ItemEntity> all = repo.findAll();

        all.forEach(itemEntity -> list.add(mapper.map(itemEntity,Item.class)));
        return list;
    }
}
