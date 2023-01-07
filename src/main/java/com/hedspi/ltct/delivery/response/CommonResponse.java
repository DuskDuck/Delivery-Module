package com.hedspi.ltct.delivery.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

    private Result result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public CommonResponse() {
    }

    public CommonResponse result(String errorCode,String errorMessage, boolean ok){
        this.result = new Result().errorCode(errorCode).message(errorMessage).isOk(ok);
        return this;
    }

    public CommonResponse errorCode(String errorCode){
        if(this.result == null){
            this.result = new Result();
        }
        this.result.errorCode(errorCode);
        return this;
    }

    public CommonResponse message(String message){
        if(this.result == null){
            this.result = new Result();
        }
        this.result.message(message);
        return this;
    }

    public CommonResponse isOk(boolean ok){
        if(this.result == null){
            this.result = new Result();
        }
        this.result.isOk(ok);
        return this;
    }
    public CommonResponse data(T data){
        this.data = data;
        return this;
    }


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public CommonResponse(Result result, T data) {
        this.result = result;
        this.data = data;
    }

    public CommonResponse(Result result) {
        this.result = result;
    }
}

