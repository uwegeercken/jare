package com.datamelt.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTypeDate implements DataTypeProperties{
    @Override
    public Class getDataType() {
        return Date.class;
    }

    @Override
    public Object getObject(String value) {
        SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date d = sdtf.parse(value);
            return  d;
        }
        // if it does not work, try to make a date from the value
        catch(Exception ex)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                Date d = sdf.parse(value);
                return  d;
            }
            catch(Exception ex2)
            {
                return null;
            }
        }
    }
}
