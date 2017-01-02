package com.github.programmerr47.navigation;

import android.support.annotation.IntDef;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

public final class NavigationItems extends ArrayList<NavigationItems.NavigationItem> {
    public static NavigationItems of(NavigationItem... items) {
        return new NavigationItems(asList(items));
    }

    public NavigationItems(int initialCapacity) {
        super(initialCapacity);
    }

    public NavigationItems() {
        super();
    }

    public NavigationItems(Collection<? extends NavigationItem> c) {
        super(c);
    }

    public List<AHBottomNavigationItem> bottomNavigationItems() {
        List<AHBottomNavigationItem> result = new ArrayList<>();
        for (NavigationItem item : this) {
            result.add(item.ahItem);
        }

        return result;
    }

    public static final class NavigationItem {
        private final int type;
        private final AHBottomNavigationItem ahItem;

        public static NavigationItem navigationItem(int type, int titleRes, int iconRes, int colorRes) {
            return new NavigationItem(type, new AHBottomNavigationItem(titleRes, iconRes, colorRes));
        }

        public NavigationItem(int type, AHBottomNavigationItem ahItem) {
            this.type = type;
            this.ahItem = ahItem;
        }

        public int type() {
            return type;
        }
    }
}
