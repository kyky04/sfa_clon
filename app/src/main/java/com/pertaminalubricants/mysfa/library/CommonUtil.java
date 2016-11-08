package com.pertaminalubricants.mysfa.library;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nunu on 11/8/2016.
 */

public class CommonUtil {

    public static String getCurrentDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return format.format(date);
    }
}
