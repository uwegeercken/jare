package com.datamelt.util;

import java.math.BigDecimal;

public class DataTypeBigDecimal implements DataTypeProperties{
    @Override
    public Class getDataType() {
        return BigDecimal.class;
    }

    @Override
    public Object getObject(String value) {
        BigDecimal bd= new BigDecimal(0);
        try
        {
            bd = new BigDecimal(value);
        }
        catch(Exception ex)
        {
        }
        return  bd;
    }
}
