package com.datamelt.util;

public class DataTypeBoolean implements DataTypeProperties{
    @Override
    public Class getDataType() {
        return boolean.class;
    }

    @Override
    public Object getObject(String value) {
        boolean b= false;
        try
        {
            b = Boolean.parseBoolean(value);
        }
        catch(Exception ex)
        {
        }
        return  Boolean.valueOf(b);
    }
}
