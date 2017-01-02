package com.github.programmerr47.navigation.menu;

import android.support.v7.widget.PopupMenu;
import android.util.SparseArray;
import android.view.MenuItem;

public final class MenuActions implements PopupMenu.OnMenuItemClickListener {
    private static final MenuAction DUMMY = new SimpleMenuAction();
    private final SparseArray<MenuAction> actions;

    private MenuActions(Builder builder) {
        this.actions = builder.actions;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        actions.get(itemId, DUMMY).execute();
        return actions.indexOfKey(itemId) > 0;
    }

    public static final class Builder {
        private final SparseArray<MenuAction> actions = new SparseArray<>();

        public Builder() {}

        public Builder(MenuActionItem... items) {
            for (MenuActionItem item : items) {
                action(item.id, item.action);
            }
        }

        public Builder action(int itemId, MenuAction action) {
            actions.put(itemId, action == null ? DUMMY : action);
            return this;
        }

        public Builder append(MenuActions menuActions) {
            return append(menuActions.actions);
        }

        public Builder append(MenuActions.Builder anotherActionBuilder) {
            return append(anotherActionBuilder.actions);
        }

        private Builder append(SparseArray<MenuAction> actions) {
            for (int i = 0; i < actions.size(); i++) {
                this.actions.put(actions.keyAt(i), actions.valueAt(i));
            }
            return this;
        }

        public MenuActions build() {
            return new MenuActions(this);
        }
    }

    public static final class MenuActionItem {
        private final int id;
        private final MenuAction action;

        public static MenuActionItem item(int id, MenuAction action) {
            return new MenuActionItem(id, action);
        }

        public MenuActionItem(int id, MenuAction action) {
            this.id = id;
            this.action = action;
        }
    }
}
