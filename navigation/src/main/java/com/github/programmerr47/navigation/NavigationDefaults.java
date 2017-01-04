package com.github.programmerr47.navigation;


import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.github.programmerr47.navigation.NavigationIcons.NavigationIcon;
import com.github.programmerr47.navigation.NavigationItems.NavigationItem;

import static java.util.Arrays.asList;

public final class NavigationDefaults {
    private static final View.OnClickListener DUMMY_NAV_ICON_LISTENER = new View.OnClickListener() {
        @Override public void onClick(View view) {}
    };

    private NavigationItems navigationItems = new NavigationItems();
    private NavigationIcons navigationIcons = new NavigationIcons();
    private View.OnClickListener navigationIconListener = DUMMY_NAV_ICON_LISTENER;
    private int defaultNavigationIconType;
    private int defaultBottomNavigationItem;

    public NavigationDefaults navigationItems(NavigationItem... navigationItems) {
        this.navigationItems.addAll(navigationItems);
        return this;
    }

    public NavigationDefaults navigationItem(int type, int titleRes, int iconRes, int colorRes) {
        return navigationItem(NavigationItem.navigationItem(type, titleRes, iconRes, colorRes));
    }

    public NavigationDefaults navigationItem(NavigationItem navigationItem) {
        navigationItems.add(navigationItem);
        return this;
    }

    public NavigationDefaults navigationIcons(NavigationIcon... navigationIcons) {
        this.navigationIcons.addAll(asList(navigationIcons));
        return this;
    }

    public NavigationDefaults navigationIcon(int type, @DrawableRes int drawableRes) {
        return navigationIcon(NavigationIcon.navigationIcon(type, drawableRes));
    }

    public NavigationDefaults navigationIcon(int type, @NonNull Drawable drawable) {
        return navigationIcon(NavigationIcon.navigationIcon(type, drawable));
    }

    public NavigationDefaults navigationIcon(NavigationIcon navigationIcon) {
        this.navigationIcons.add(navigationIcon);
        return this;
    }

    public NavigationDefaults navigationIconListener(View.OnClickListener listener) {
        this.navigationIconListener = listener == null ? DUMMY_NAV_ICON_LISTENER : listener;
        return this;
    }

    public NavigationDefaults defaultNavigationIconType(int defaultNavigationIconType) {
        this.defaultNavigationIconType = defaultNavigationIconType;
        return this;
    }

    public NavigationDefaults defaultBottomNavigationItem(int defaultBottomNavigationItem) {
        this.defaultBottomNavigationItem = defaultBottomNavigationItem;
        return this;
    }

    public NavigationItems navigationItems() {
        return navigationItems;
    }

    public NavigationIcons navigationIcons() {
        return navigationIcons;
    }

    public View.OnClickListener navigationIconListener() {
        return navigationIconListener;
    }

    public int defaultNavigationIconType() {
        return defaultNavigationIconType;
    }

    public int defaultBottomNavigationItem() {
        return defaultBottomNavigationItem;
    }

    public static final class NavigationDefaultsHolder {
        private static NavigationDefaults defaults;

        public static void initDefaults(NavigationDefaults defaults) {
            NavigationDefaultsHolder.defaults = defaults;
        }

        public static NavigationDefaults navigationDefaults() {
            return defaults;
        }
    }
}
