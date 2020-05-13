package com.codelife.cloud.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class Permission implements Serializable {
    private String code;
    private Integer type;
}
