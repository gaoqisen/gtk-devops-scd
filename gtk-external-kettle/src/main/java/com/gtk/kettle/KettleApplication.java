package com.gtk.kettle;

import com.gtk.kettle.utils.KettleUtil;

public class KettleApplication {

    public static void main(String[] args) {
        String transFileName = "/Users/jasongao/Documents/测试.ktr";
        try {
            KettleUtil.callNativeTrans(transFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
