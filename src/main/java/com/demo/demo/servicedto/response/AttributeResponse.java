package com.demo.demo.servicedto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeResponse {
    private int id;
    private String key;
    private String value;
    private String state;

    private String binType;
}
