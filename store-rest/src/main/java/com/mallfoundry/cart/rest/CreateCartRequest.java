package com.mallfoundry.cart.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCartRequest extends CartRequest {
    private String id;
}
