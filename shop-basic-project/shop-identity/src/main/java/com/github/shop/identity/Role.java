package com.github.shop.identity;

import lombok.Getter;
import lombok.Setter;

/**
 * A security role.
 *
 * @author Zhi Tang
 */
@Getter
@Setter
public class Role {

    /**
     * Name of this role.
     */
    private String name;

    /**
     * Label of this role.
     */
    private String label;

    /**
     * Description of this role.
     */
    private String description;
}
