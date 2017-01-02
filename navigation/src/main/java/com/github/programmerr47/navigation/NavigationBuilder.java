package com.github.programmerr47.navigation;

import android.graphics.drawable.Drawable;

import com.github.programmerr47.navigation.NavigationIcons.NavigationIcon;
import com.github.programmerr47.navigation.layoutfactory.LayoutFactory;
import com.github.programmerr47.navigation.menu.MenuActions;

import java.util.ArrayList;
import java.util.List;

import static com.github.programmerr47.navigation.NavigationIcons.BACK;

public abstract class NavigationBuilder<T extends NavigationBuilder<T>> {
    private final LayoutFactory layoutFactory;

    int toolbarId = R.id.toolbar;
    int bottomBarId = R.id.bottomNavigation;

    int currentBottomBarItem;
    @NavigationIcon int toolbarNavigationIcon = BACK;
    int toolbarTitleRes;
    CharSequence toolbarTitle;
    int toolbarSubtitleRes;
    CharSequence toolbarSubtitle;
    int toolbarLogoRes;
    Drawable toolbarLogo;

    List<Integer> menuRes = new ArrayList<>();
    MenuActions.Builder menuActions = new MenuActions.Builder();

    public NavigationBuilder(LayoutFactory layoutFactory) {
        this.layoutFactory = layoutFactory;
    }

    protected abstract T getThis();

    public LayoutFactory layoutFactory() {
        return layoutFactory;
    }

    public T currentBottomBarItem(int currentBottomBarItem) {
        this.currentBottomBarItem = currentBottomBarItem;
        return getThis();
    }

    public T toolbarNavigationIcon(@NavigationIcon int toolbarNavigationIcon) {
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
