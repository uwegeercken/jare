package com.datamelt.util;

public class DataTypeInteger implements DataTypeProperties{
    @Override
    public Class getDataType() {
        return Integer.class;
    }

    @Override
    public Object getObject(String value) {
        int i=0;
        try
        {
            i = Integer.parseInt(value);
        }
        catch(Exception ex)
        {
        }
        return Integer.valueOf(i);
    }
}
