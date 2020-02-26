package com.gtk.kettle;

import com.gtk.kettle.utils.KettleUtil;

public class KettleApplication {

    public static void main(String[] args) {
        String transFileName = "path/kettle/trans.ktr";
        try {
            KettleUtil.callNativeTrans(transFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
