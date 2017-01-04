package com.github.programmerr47.navigationwidgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.github.programmerr47.navigation.AutoLayoutNavigationBuilder;
import com.github.programmerr47.navigation.NavigationBuilder;
import com.github.programmerr47.navigation.NavigationFragment;
import com.github.programmerr47.navigation.menu.MenuAction;
import com.github.programmerr47.navigation.menu.MenuActions;

import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;
import static com.github.programmerr47.navigation.AutoLayoutNavigationBuilder.navigation;
import static com.github.programmerr47.navigation.NavigationBuilder.NO_NAV_ICON;
import static com.github.programmerr47.navigationwidgets.AppApplication.appContext;
import static com.github.programmerr47.navigationwidgets.GenerateUtil.generateFrom;
import static com.github.programmerr47.navigationwidgets.constants.NavigationIconType.BACK;
import static com.github.programmerr47.navigationwidgets.constants.NavigationIconType.CLOSE;
import static com.github.programmerr47.navigationwidgets.constants.NavigationIconType.DOWN;
import static com.github.programmerr47.navigationwidgets.constants.NavigationIconType.ENTER;
import static com.github.programmerr47.navigationwidgets.constants.NavigationIconType.UP;
import static com.github.programmerr47.navigationwidgets.constants.NavigationItemType.ACCOUNT;
import static com.github.programmerr47.navigationwidgets.constants.NavigationItemType.MESSAGES;
import static com.github.programmerr47.navigationwidgets.constants.NavigationItemType.SEARCH;

public final class AppFragment extends NavigationFragment {
    private static final Random rand = new Random();
    private static final MenuActions globalMenuActions = buildGlobalActions();

    @Override
    protected NavigationBuilder buildNavigation() {
        return navigation(R.layout.fragment_app)
                .includeToolbar()
                .includeBottomNavigation()
                .toolbarTitle("It's a test!")
                .toolbarSubtitle("Super subtitle test!");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.generateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invalidateNavigation(generateNavigation());
            }
        });
    }

    private NavigationBuilder generateNavigation() {
        return navigation(R.layout.fragment_app)
                .toolbarTitle(generateFrom("", "Test title", "Random Test Title", "Big random awesome heh test title wow!", "RTT", "Hello!"))
                .toolbarSubtitle(generateFrom("", "Test subtitle", "Super Big test subtitle for this test"))
                .toolbarNavigationIcon(generateFrom(BACK, UP, DOWN, ENTER, CLOSE, NO_NAV_ICON))
                .currentBottomBarItem(generateFrom(ACCOUNT, MESSAGES, SEARCH))
                .menuRes(
                        generateFrom(R.menu.test_menu_1, R.menu.test_menu_2, R.menu.test_menu_3),
                        globalMenuActions);
    }

    private static MenuActions buildGlobalActions() {
        return new MenuActions.Builder()
                .action(R.id.search, new ToastAction("Search menu item!"))
                .action(R.id.messages, new ToastAction("Messages menu item!"))
                .action(R.id.back, new ToastAction("bACk menu item!"))
                .action(R.id.up, new ToastAction("UUUUUp menu item!"))
                .action(R.id.down, new ToastAction("DoWn menu item!"))
                .build();
    }

    private static final class ToastAction implements MenuAction {
        private final String text;

        private ToastAction(String text) {this.text = text;}

        @Override
        public void execute() {
            Toast.makeText(appContext(), text, LENGTH_SHORT).show();
        }
    }
}
