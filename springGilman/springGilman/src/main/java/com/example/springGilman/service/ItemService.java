package com.example.springGilman.service;

import com.example.springGilman.domain.Item;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemService {
    @Transactional
    public Item save(Item item);

    @Transactional(readOnly = true)
    public List<Item> findAll();

    @Transactional(readOnly = true)
    public Item findById(Long id);
    @Transactional
    public void update(Long id, Item item);
}