package com.demo.demo.webdto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCardbinWebRequest {
    private int binNumber;
    private String binType;
}
