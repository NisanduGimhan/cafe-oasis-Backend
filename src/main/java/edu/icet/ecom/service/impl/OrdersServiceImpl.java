package edu.icet.ecom.service.impl;

import edu.icet.ecom.dto.Item;
import edu.icet.ecom.dto.Orders;
import edu.icet.ecom.entity.ItemEntity;
import edu.icet.ecom.entity.OrdersEntity;
import edu.icet.ecom.repository.OrderRepository;
import edu.icet.ecom.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrderRepository repo;
    private final ModelMapper mapper;

    @Override
    public void save(Orders orders) {
        repo.save(mapper.map(orders, OrdersEntity.class));


    }

    @Override
    public void deleteById(Long id) {
          repo.deleteById(id);
    }

    @Override
    public List<Orders> findall() {
        List<Orders> list=new ArrayList<>();
        List<OrdersEntity> all = repo.findAll();

        all.forEach(ordersEntity -> {
            list.add(mapper.map(ordersEntity,Orders.class));
        });
        return list;
    }
}
