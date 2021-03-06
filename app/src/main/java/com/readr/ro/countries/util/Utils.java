package com.readr.ro.countries.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Domnica on 11/1/2016.
 */

public class Utils {

    private Utils() {
        // private default constructor
    }

    /**
     * Fetch the flag resource id and verifies if it's called with the first denominator or the second (a third code was not necessary)
     *
     * @param alphaCode            first country code
     * @param alternativeAlphaCode second country code
     * @param context              context
     * @return resourceId          resourceId
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
            // I could've had a check slide_in_left the adapter and just use hardcoded dom.png denominator,
            // but I prefer this way better
            resourceId = context.getResources().getIdentifier(alternativeAlphaCode.toLowerCase(), "drawable", context.getPackageName());
            return resourceId;
        }
    }

    public static boolean isCallingCodeCorrect(String[] callingCodes) {
        boolean result = false;

        if (callingCodes != null) {
            for (String callingCode : callingCodes) {
                // if at least one calling code is eligible proceed
                if (!callingCode.isEmpty()) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }
}
