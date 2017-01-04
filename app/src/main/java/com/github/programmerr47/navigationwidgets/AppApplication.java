package com.github.programmerr47.navigationwidgets;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;

import com.github.programmerr47.navigation.NavigationDefaults;

import static com.github.programmerr47.navigation.NavigationDefaults.NavigationDefaultsHolder.initDefaults;
import static com.github.programmerr47.navigationwidgets.constants.NavigationIconType.BACK;
import static com.github.programmerr47.navigationwidgets.constants.NavigationIconType.CLOSE;
import static com.github.programmerr47.navigationwidgets.constants.NavigationIconType.DOWN;
import static com.github.programmerr47.navigationwidgets.constants.NavigationIconType.ENTER;
import static com.github.programmerr47.navigationwidgets.constants.NavigationIconType.UP;
import static com.github.programmerr47.navigationwidgets.constants.NavigationItemType.ACCOUNT;
import static com.github.programmerr47.navigationwidgets.constants.NavigationItemType.MESSAGES;
import static com.github.programmerr47.navigationwidgets.constants.NavigationItemType.SEARCH;

public class AppApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = this;

        initDefaults(new NavigationDefaults()
                .navigationIcon(BACK, R.drawable.arrow_left)
                .navigationIcon(CLOSE, R.drawable.close)
                .navigationIcon(ENTER, R.drawable.subdirectory_arrow_left)
                .navigationIcon(UP, R.drawable.arrow_up)
                .navigationIcon(DOWN, R.drawable.arrow_down)
                .navigationItem(SEARCH, R.string.nav_item_search, R.drawable.magnify, R.color.colorPrimary)
                .navigationItem(ACCOUNT, R.string.nav_item_account, R.drawable.account, R.color.colorAccent)
                .navigationItem(MESSAGES, R.string.nav_item_messages, R.drawable.message_text, R.color.colorPrimaryDark)
                .defaultNavigationIconType(ENTER)
                .defaultBottomNavigationItem(ACCOUNT)
                .navigationIconListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();
                        if (context instanceof Activity) {
                            ((Activity) context).onBackPressed();
                        }
                    }
                }));
    }

    public static Context appContext() {
        return appContext;
    }
}
