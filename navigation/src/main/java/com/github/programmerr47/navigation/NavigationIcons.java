package com.github.programmerr47.navigation;

import android.support.annotation.IntDef;

public class NavigationIcons {
    private NavigationIcons() {}

    public static final int BACK = 0;
    public static final int CLOSE = 1;
    public static final int NOTHING = 2;

    @IntDef(value = {BACK, CLOSE, NOTHING})
    public @interface NavigationIcon {}
}
