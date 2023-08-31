package com.study.base.boot.aggregations.v1.order.application;

import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateItem;
import com.study.base.boot.aggregations.v1.order.domain.ItemAggregate;
import com.study.base.boot.aggregations.v1.order.infrastructure.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public List<Long> itemCreates(List<CreateItem> createItem) {
        final var items = ItemAggregate.creates(itemRepository, createItem);

        return items.stream()
                .map(ItemAggregate::getId) //id 반환
                .toList();
    }
}