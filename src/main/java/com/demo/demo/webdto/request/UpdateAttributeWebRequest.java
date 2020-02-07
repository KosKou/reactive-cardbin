package com.demo.demo.webdto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAttributeWebRequest {
    private String key;
    private String value;
}
