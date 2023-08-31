package com.study.base.boot.aggregations.v1.order.presentation;

import com.study.base.boot.aggregations.v1.order.presentation.dto.req.CreateItemDto;
import com.study.base.boot.config.annotations.RestApi;
import com.study.base.boot.aggregations.v1.order.application.ItemService;
import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateItem;
import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateItemStock;
import com.study.base.boot.aggregations.v1.order.presentation.dto.req.CreateItemsDto;
import com.study.base.boot.config.annotations.Post;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestApi("/v1/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;
    @Post
    public List<Long> createItems(
            @Valid
            @RequestBody CreateItemsDto payload) {
        final var create = payload.toCreateItem();
        final var createId = itemService.itemCreates(create);
        return createId;
    }
}