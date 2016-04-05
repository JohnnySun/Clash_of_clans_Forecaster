package moe.johnny.tools;

import android.content.Context;

/**
 * Created by bmy001 on 16-3-2.
 */
public class getBarInfo {

    /**
     * Get Status Bar Height (In Pixel)
     * @param context
     * @return int
     */
    static public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Get correct Fab Margin when transparent set
     * @param context
     * @return int
     */
    public int getNavigationBarFabMargin(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        int fab_margin = context.getResources().getIdentifier("fab_margin", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result + fab_margin;
    }
}
