package com.datamelt.util;

public class DataTypeObject implements DataTypeProperties{
    @Override
    public Class getDataType() {
        return Object.class;
    }

    @Override
    public Object getObject(String value) {
        return null;
    }
}
