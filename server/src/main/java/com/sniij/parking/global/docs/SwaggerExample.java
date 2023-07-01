package com.sniij.parking.global.docs;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class SwaggerExample {

    @Operation(summary="요약", description="설명")
    @ApiResponse(code = 200, message="ok")
    @PostMapping("/ex/")
    public ResponseDto exampleMethod() {
        return new ResponseDto();
    }
}


