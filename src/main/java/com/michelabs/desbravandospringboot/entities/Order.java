package com.michelabs.desbravandospringboot.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.michelabs.desbravandospringboot.entities.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_order")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant moment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    // informando do banco de dados que o valor a ser gravado, será um inteiro, porém, externo à classe, continuará como um ENUM (OrderStatus).
    private Integer orderStatus;

    public Order(Long id, Instant moment, OrderStatus orderStatus, User client) {
        this.id = id;
        this.moment = moment;
        setOrderStatus(orderStatus);
        this.client = client;
    }
    // alterado manualmente para receber um integer, e o retornar como um enum (OrderStatus)
    public OrderStatus getOrderStatus() {
        return OrderStatus.valueOf(orderStatus);
    }

    // alterado manualmente para receber um enum (OrderStatus), e o retornar como Integer.
    public void setOrderStatus(OrderStatus orderStatus) {
        if(orderStatus != null)
            this.orderStatus = orderStatus.getCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}