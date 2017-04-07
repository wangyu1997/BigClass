package com.njtech.bigclass.utils;

import android.content.Context;
import android.net.Uri;

/**
 * Created by wangyu on 07/04/2017.
 */

public class ImageUtil {
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

}
