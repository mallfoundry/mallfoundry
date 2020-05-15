package com.mallfoundry.cart;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "cart_tokens")
public class CartToken {

    @Id
    @Column(name = "token_")
    private String token;

    @Column(name = "cart_id_")
    private String cartId;
}
