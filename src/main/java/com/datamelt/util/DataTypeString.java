package com.datamelt.util;

public class DataTypeString implements DataTypeProperties{
    @Override
    public Class getDataType() {
        return String.class;
    }

    @Override
    public Object getObject(String value) {
        return value;
    }
}
