package com.datamelt.util;

public class DataTypeFloat implements DataTypeProperties{
    @Override
    public Class getDataType() {
        return float.class;
    }

    @Override
    public Object getObject(String value) {
        float f = 0;
        try
        {
            f = Float.parseFloat(value);
        }
        catch(Exception ex)
        {
        }
        return  Float.valueOf(f);
    }
}
