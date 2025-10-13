package com.codewithmosh.store.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date created_at;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal total_price;

    @OneToMany(mappedBy = "order" , cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<OrderItem> items = new LinkedHashSet<>();

    public Set<OrderItem> getItems() {
        return items;
    }

    public static Order fromCart(Cart cart, User customer) {
        var order = new Order();
        order.setTotal_price(cart.getTotalPrice());
        order.setStatus(PaymentStatus.PENDING);
        order.setCustomer(customer);


        cart.getItems().forEach( (CartItem item) -> {
            OrderItem orderItem = new OrderItem(
                order,
                item.getProduct(),
                item.getQuantity()
            );
            order.getItems().add(orderItem);
            order.items.add(orderItem);
        });

        return order;
    }

    public boolean isPlacedBy(User customer) {
        return this.customer.equals(customer);
    }
}
