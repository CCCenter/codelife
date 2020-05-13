package com.codelife.cloud.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;
@Data
public class Role implements Serializable {
    private Set<Permission> permissions;
}
