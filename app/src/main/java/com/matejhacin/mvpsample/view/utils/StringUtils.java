package com.matejhacin.mvpsample.view.utils;

import android.content.Context;

import com.matejhacin.mvpsample.R;

/**
 * Created by matejhacin on 07/09/2017.
 */

public class StringUtils {

    public static String booleanToYesOrNo(Context context, boolean bool) {
        return bool ? context.getString(R.string.yes) : context.getString(R.string.no);
    }

}
