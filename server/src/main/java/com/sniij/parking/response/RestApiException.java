package com.sniij.parking.response;


import com.sniij.parking.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {

    private final ExceptionCode exceptionCode;

}
