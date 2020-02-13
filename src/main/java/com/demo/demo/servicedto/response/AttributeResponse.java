package com.demo.demo.servicedto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeResponse {
    private int id;
    private String key;
    private String value;
    private String state;

    private String binType;
}
