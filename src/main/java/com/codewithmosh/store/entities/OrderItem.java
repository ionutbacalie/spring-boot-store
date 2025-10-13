package com.codewithmosh.store.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unit_price;
    
    @Column(name = "quantity")
    private int quantity;
    
    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal total_price;

    public OrderItem(Order order, Product product,Integer quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unit_price = product.getPrice();
        this.total_price = unit_price.multiply(BigDecimal.valueOf(quantity));
    }

}
