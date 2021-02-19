package com.leon.rpc.constants;

/**
 * Copyright (c) 2021 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author longmu
 * @since 2021/2/19
 */
public class Constants {

    public static boolean isHeartBeats(byte b){
        return b == 1;
    }

    public static boolean isNeedCompress(byte b){
        return b == 2;
    }

}