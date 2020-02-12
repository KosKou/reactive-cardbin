package com.demo.demo.servicedto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCardbinRequest {
    private int id;
    private int binNumber;
    private String binType;
}
