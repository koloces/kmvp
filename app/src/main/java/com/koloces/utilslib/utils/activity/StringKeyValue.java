package com.koloces.utilslib.utils.activity;

import java.io.Serializable;

/**
 * Created on 2019/3/30
 */
public class StringKeyValue implements Serializable {
    public String key;
    public String value;

    public StringKeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
