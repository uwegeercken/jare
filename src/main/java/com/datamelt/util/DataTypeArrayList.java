package com.datamelt.util;

import java.util.ArrayList;

public class DataTypeArrayList implements DataTypeProperties{
    @Override
    public Class getDataType() {
        return ArrayList.class;
    }

    @Override
    public Object getObject(String value) {
        return null;
    }
}
