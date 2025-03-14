package edu.icet.ecom.service.impl;

import edu.icet.ecom.dto.Customer;
import edu.icet.ecom.entity.CustomerEntity;
import edu.icet.ecom.repository.CustomerRepository;
import edu.icet.ecom.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;
    private final ModelMapper mapper;

    @Override
    public void save(Customer customer) {
        CustomerEntity save = repo.save(mapper.map(customer, CustomerEntity.class));
        System.out.println(customer);
    }

    @Override
    public Customer findById(Long id) {
        return mapper.map(repo.findById(id),Customer.class);
    }

    @Override
    public void deleteById(Long id) {
    repo.deleteById(id);
    }

    @Override
    public void update(Customer customer) {
         repo.save(mapper.map(customer,CustomerEntity.class));
    }

    @Override
    public List<Customer> findall() {
        List<Customer> list=new ArrayList<>();
        List<CustomerEntity> all = repo.findAll();

        all.forEach(customerEntity -> list.add(mapper.map(customerEntity,Customer.class)));
        return list;
    }
}
