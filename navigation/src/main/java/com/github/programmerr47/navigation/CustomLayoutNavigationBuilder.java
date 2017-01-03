package com.github.programmerr47.navigation;

import com.github.programmerr47.navigation.NavigationDefaults.NavigationDefaultsHolder;
import com.github.programmerr47.navigation.layoutfactory.LayoutFactory;

public final class CustomLayoutNavigationBuilder extends NavigationBuilder<CustomLayoutNavigationBuilder> {

    public CustomLayoutNavigationBuilder(LayoutFactory layoutFactory) {
        super(layoutFactory, NavigationDefaultsHolder.navigationDefaults());
    }

    @Override
    protected CustomLayoutNavigationBuilder getThis() {
        return this;
    }

    public CustomLayoutNavigationBuilder toolbarId(int toolbarId) {
        this.toolbarId = toolbarId;
        return this;
    }

    public CustomLayoutNavigationBuilder bottomBarId(int bottomBarId) {
        this.bottomBarId = bottomBarId;
        return this;
    }

    public AutoLayoutNavigationBuilder auto() {
        return new AutoLayoutNavigationBuilder(layoutFactory());
    }
}
