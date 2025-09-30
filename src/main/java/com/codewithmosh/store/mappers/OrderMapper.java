package com.codewithmosh.store.mappers;

import org.mapstruct.Mapper;

import com.codewithmosh.store.dtos.CheckoutResponse;
import com.codewithmosh.store.entities.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    CheckoutResponse toDto (Order cart);
}
