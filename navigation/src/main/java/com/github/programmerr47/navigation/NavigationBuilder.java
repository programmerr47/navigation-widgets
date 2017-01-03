package com.github.programmerr47.navigation;

import android.graphics.drawable.Drawable;

import com.github.programmerr47.navigation.layoutfactory.LayoutFactory;
import com.github.programmerr47.navigation.menu.MenuActions;

import java.util.ArrayList;
import java.util.List;

public abstract class NavigationBuilder<T extends NavigationBuilder<T>> {
    public static final int NO_NAV_ICON = -1;

    private final LayoutFactory layoutFactory;
    private final NavigationDefaults navigationDefaults;

    int toolbarId = R.id.toolbar;
    int bottomBarId = R.id.bottomNavigation;

    int currentBottomBarItem;
    int toolbarNavigationIcon;
    int toolbarTitleRes;
    CharSequence toolbarTitle;
    int toolbarSubtitleRes;
    CharSequence toolbarSubtitle;
    int toolbarLogoRes;
    Drawable toolbarLogo;

    List<Integer> menuRes = new ArrayList<>();
    MenuActions.Builder menuActions = new MenuActions.Builder();

    public NavigationBuilder(LayoutFactory layoutFactory, NavigationDefaults navigationDefaults) {
        this.layoutFactory = layoutFactory;
        this.navigationDefaults = navigationDefaults;
        this.toolbarNavigationIcon = navigationDefaults.defaultNavigationIconType();
    }

    protected abstract T getThis();

    public LayoutFactory layoutFactory() {
        return layoutFactory;
    }

    public NavigationDefaults navigationDefaults() {
        return navigationDefaults;
    }

    public T currentBottomBarItem(int currentBottomBarItem) {
        if (!navigationDefaults.navigationItems().contains(currentBottomBarItem)) {
            throw new IllegalArgumentException("There is no navigation item for type: " + currentBottomBarItem);
        }

        this.currentBottomBarItem = currentBottomBarItem;
        return getThis();
    }

    public T toolbarNavigationIcon(int toolbarNavigationIcon) {
        if (!navigationDefaults.navigationIcons().contains(toolbarNavigationIcon) &&
                toolbarNavigationIcon != NO_NAV_ICON) {
            throw new IllegalArgumentException("There is no navigation icon for type: " + toolbarNavigationIcon);
        }

        this.toolbarNavigationIcon = toolbarNavigationIcon;
        return getThis();
    }

    public T toolbarTitleRes(int toolbarTitleRes) {
        this.toolbarTitleRes = toolbarTitleRes;
        return getThis();
    }

    public T toolbarTitle(CharSequence toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
        return getThis();
    }

    public T toolbarSubtitleRes(int toolbarSubtitleRes) {
        this.toolbarSubtitleRes = toolbarSubtitleRes;
        return getThis();
    }

    public T toolbarSubtitle(CharSequence toolbarSubtitle) {
        this.toolbarSubtitle = toolbarSubtitle;
        return getThis();
    }

    public T toolbarLogoRes(int toolbarLogoRes) {
        this.toolbarLogoRes = toolbarLogoRes;
        return getThis();
    }

    public T toolbarLogo(Drawable toolbarLogo) {
        this.toolbarLogo = toolbarLogo;
        return getThis();
    }

    public T menuRes(int menuRes, MenuActions.MenuActionItem... items) {
        return menuRes(menuRes, new MenuActions.Builder(items));
    }

    public T menuRes(int menuRes, MenuActions.Builder menuBuilder) {
        this.menuRes.add(menuRes);
        this.menuActions.append(menuBuilder);
        return getThis();
    }

    public T menuRes(int menuRes, MenuActions menuActions) {
        this.menuRes.add(menuRes);
        this.menuActions.append(menuActions);
        return getThis();
    }
}
