package com.leon.rpc.protocol;

/**
 * Copyright (c) 2021 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author longmu
 * @since 2021/2/19
 */
public class Message<T> {

    private Header header;
    private T request;

    public Message(Header header, T request){
        this.header = header;
        this.request = request;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }
}