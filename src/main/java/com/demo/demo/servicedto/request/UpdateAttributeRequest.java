package com.demo.demo.servicedto.request;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAttributeRequest {
    private int id;
    private String key;
    private String value;
}
