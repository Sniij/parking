package com.sniij.parking.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleResponse<T> {
    private T data;
}
