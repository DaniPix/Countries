package com.readr.ro.countries.util;

import android.content.Context;

/**
 * Created by Domnica on 11/1/2016.
 */

public class ImageUtils {

    private ImageUtils() {
        // private default constructor
    }

    /**
     * Fetch the flag resource id and verifies if it's called with the first denominator or the second (a third code was not necessary)
     *
     * @param alphaCode            first country code
     * @param alternativeAlphaCode second country code
     * @param context              context
     * @return
     */
    public static int fetchFlagFromAlphaCode(String alphaCode, String alternativeAlphaCode, Context context) {

        int resourceId = context.getResources().getIdentifier(alphaCode.toLowerCase(), "drawable", context.getPackageName());

        if (resourceId != 0) {
            return resourceId;
        } else {
            // do.png drawable name is reserved by java compiler,
            // therefore had to rename the countries code using the second denominator dom.png,
            // and if the first search fails, definitely the second denominator should be there.
            // Instead of this check, which probably adds some insignificant time to the whole process,
            // I could've had a check in the adapter and just use hardcoded dom.png denominator,
            // but I prefer this way better
            resourceId = context.getResources().getIdentifier(alternativeAlphaCode.toLowerCase(), "drawable", context.getPackageName());
            return resourceId;
        }
    }
}
