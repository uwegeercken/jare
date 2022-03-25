package com.datamelt.util;

public class DataTypeDouble implements DataTypeProperties{
    @Override
    public Class getDataType() {
        return double.class;
    }

    @Override
    public Object getObject(String value) {
        double d=0;
        try
        {
            d = Double.parseDouble(value);
        }
        catch(Exception ex)
        {
        }
        return  Double.valueOf(d);
    }
}
