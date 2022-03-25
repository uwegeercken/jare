package com.datamelt.util;

public class DataTypeLong implements DataTypeProperties{
    @Override
    public Class getDataType() {
        return long.class;
    }

    @Override
    public Object getObject(String value) {
        long l=0;
        try
        {
            l = Long.parseLong(value);
        }
        catch(Exception ex)
        {
        }
        return  Long.valueOf(l);
    }
}
