package com.leon.rpc.compress;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Copyright (c) 2021 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author longmu
 * @since 2021/2/19
 */
public class Compressor {

    public static byte[] compress(byte[] originBytes) throws IOException {
        if(null == originBytes || originBytes.length == 0){
            return null;
        }
        return Snappy.compress(originBytes);
    }

    public static byte[] unCompress(byte[] originBytes) throws IOException {
        if(null == originBytes || originBytes.length == 0){
            return null;
        }
        return Snappy.uncompress(originBytes);
    }
}