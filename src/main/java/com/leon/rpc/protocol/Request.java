package com.leon.rpc.protocol;

import java.io.Serializable;

/**
 * Copyright (c) 2021 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author longmu
 * @since 2021/2/19
 */
public class Request implements Serializable {
    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 请求参数类型
     */
    private Class[] argsTypes;

    /**
     * 请求方法参数
     */
    private Object[] args;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class[] getArgsTypes() {
        return argsTypes;
    }

    public void setArgsTypes(Class[] argsTypes) {
        this.argsTypes = argsTypes;
    }
}