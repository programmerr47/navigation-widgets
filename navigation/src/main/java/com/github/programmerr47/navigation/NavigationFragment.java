package com.github.programmerr47.navigation;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public abstract class NavigationFragment extends Fragment implements OnTabSelectedListener {
    private static final int COUNT_MESSAGES_LOADER_ID = 10100101;

    private final NavigationItems navItems = NavigationItems.of(
            navigationItem(SEARCH_PAGE, R.string.bottom_bar_tab_search, R.drawable.ic_tab_search, R.color.colorPrimary),
            navigationItem(FAVORITES_PAGE, R.string.bottom_bar_tab_favorites, R.drawable.ic_tab_favorites, R.color.colorPrimary),
            navigationItem(ADVERT_CREATION_PAGE, R.string.bottom_bar_tab_create_advert, R.drawable.ic_tab_create_advert, R.color.colorPrimary, R.color.accent),
            navigationItem(MESSAGES_PAGE, R.string.bottom_bar_tab_messages, R.drawable.ic_tab_messages, R.color.colorPrimary),
            navigationItem(PROFILE_PAGE, R.string.bottom_bar_tab_profile, R.drawable.ic_tab_profile, R.color.colorPrimary));

    private Navigation navigation;
    private NavigationBuilder<?> navigationBuilder;

    private final View.OnClickListener backListener = view -> navigation.back();

    protected Toolbar toolbar;
    protected AmruBottomNavigation bottomNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            screenAnalytics();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof Navigation) {
            navigation = (Navigation) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        navigationBuilder = buildNavigation();
        return navigationBuilder.layoutFactory().produceLayout(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        toolbar = bind(view, navigationBuilder.toolbarId);
        bottomNavigation = bind(view, navigationBuilder.bottomBarId);
        prepareNavigation();
    }

    protected void invalidateNavigation(NavigationBuilder newNavigation) {
        navigationBuilder = newNavigation;
        prepareNavigation();
    }

    private void prepareNavigation() {
        if (toolbar != null) {
            prepareToolbar(toolbar);
        }

        if (bottomNavigation != null) {
            prepareBottomNavigation(bottomNavigation);
        }
    }

    protected void prepareToolbar(Toolbar toolbar) {
        if (navigationBuilder.toolbarTitleRes != 0) {
            toolbar.setTitle(navigationBuilder.toolbarTitleRes);
        } else {
            toolbar.setTitle(navigationBuilder.toolbarTitle);
        }
        if (navigationBuilder.toolbarSubtitleRes != 0) {
            toolbar.setSubtitle(navigationBuilder.toolbarSubtitleRes);
        } else {
            toolbar.setSubtitle(navigationBuilder.toolbarSubtitle);
        }
        if (navigationBuilder.toolbarLogoRes != 0) {
            toolbar.setLogo(navigationBuilder.toolbarLogoRes);
        } else {
            toolbar.setLogo(navigationBuilder.toolbarLogo);
        }
        if (navigationBuilder.toolbarNavigationIcon == NOTHING) {
            toolbar.setNavigationIcon(null);
            toolbar.setNavigationOnClickListener(null);
        } else {
            VectorDrawableCompat navIcon = VectorDrawableCompat.create(
                    getResources(),
                    navigationIconFromType(navigationBuilder.toolbarNavigationIcon),
                    getContext().getTheme());
            DrawableCompat.setTint(navIcon, color(android.R.color.white));
            toolbar.setNavigationIcon(navIcon);
            toolbar.setNavigationOnClickListener(backListener);
        }


        Menu menu = toolbar.getMenu();
        if (menu != null) {
            menu.clear();
        }
        if (!navigationBuilder.menuRes.isEmpty()) {
            MenuActions actions = navigationBuilder.menuActions.build();
            for (Integer menuRes : navigationBuilder.menuRes) {
                toolbar.inflateMenu(menuRes);
            }
            toolbar.setOnMenuItemClickListener(actions::onMenuItemClick);
        }
    }

    private int navigationIconFromType(int navIconType) {
        switch (navIconType) {
            case BACK:
                return R.drawable.ic_arrow_left;
            case CLOSE:
                return R.drawable.ic_close;
            default:
                throw new IllegalArgumentException("There is no icon for navigation icon type: " + navIconType);
        }
    }

    protected void prepareBottomNavigation(AmruBottomNavigation bottomNavigation) {
        bottomNavigation.addItems(navItems.bottomNavigationItems());
        bottomNavigation.setCurrentItem(navigationBuilder.currentBottomBarItem, false);
        bottomNavigation.setOnTabSelectedListener(this);
        bottomNavigation.setTitleState(ALWAYS_SHOW);
        bottomNavigation.setColored(true);

        updateMessagesBadge();
    }

    private void updateMessagesBadge() {
        int lastSavedMessagesCount = PreferencesApp.getLastMessagesCount(getContext());
        if (lastSavedMessagesCount == 0) {
            updateChatBadgeImmediate("");
        } else {
            updateChatBadgeImmediate(String.valueOf(lastSavedMessagesCount));
        }

        getLoaderManager().initLoader(COUNT_MESSAGES_LOADER_ID, null, new BaseEntityLoaderCallbacks<>(getContext(), new MessagesCountLoaderCallback()));
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation result = new Animation() {};
        result.setDuration(0);
        return result;
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        int itemType = navItems.get(position).type();
        if (!wasSelected) {
            String fragmentTag = String.valueOf(itemType);
            switch (itemType) {
                case SEARCH_PAGE:
                    newRootFragment(SearchResultsFragment_.builder().build(), fragmentTag, itemType);
                    return true;
                case FAVORITES_PAGE:
                    newRootFragment(FavoritePagerFragment_.builder().build(), fragmentTag, itemType);
                    return true;
                case ADVERT_CREATION_PAGE:
                    AnalyticsTabBar.clickOnSaleTabbar(getContext());
                    new MainActivity.AddAdvertFragmentBottomBarInitializer(getContext(), navigation, fragmentTag).init();
                    return false;
                case MESSAGES_PAGE:
                    newRootFragment(RoomsFragment_.builder().build(), fragmentTag, itemType);
                    return true;
                case PROFILE_PAGE:
                    newRootFragment(ProfileFragment_.builder().build(), fragmentTag, itemType);
                    return true;
                default:
                    return false;
            }
        } else {
            switch (itemType) {
                case SEARCH_PAGE:
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).ensureSerpAndOpenFilter();
                    }
                    return true;
                default:
                    return false;
            }
        }
    }

    @Override
    public void changeFragment(Fragment fragment, String tag, boolean backStack) {
        navigation.changeFragment(fragment, tag, backStack);
    }

    @Override
    public void newRootFragment(Fragment fragment, String tag, int navigationTab) {
        navigation.newRootFragment(fragment, tag, navigationTab);
    }

    @Override
    public void changeBackStack(FragmentFactory... factories) {
        navigation.changeBackStack(factories);
    }

    @Override
    public boolean back() {
        return navigation != null && navigation.back();
    }

    public void showBottomNavigation() {
        if (bottomNavigation != null) {
            bottomNavigation.setVisibility(VISIBLE);
        }
    }

    public void hideBottomNavigation() {
        if (bottomNavigation != null) {
            bottomNavigation.setVisibility(GONE);
        }
    }

    public void clearChatNewMessagesBadge() {
        updateChatBadge("");
    }

    public void newChatMessages(int count) {
        updateChatBadge(String.valueOf(count));
    }

    private void updateChatBadge(String title) {
        if (bottomNavigation != null) {
            bottomNavigation.setNotification(title, MESSAGES_PAGE);
        }
    }

    private void updateChatBadgeImmediate(String title) {
        if (bottomNavigation != null) {
            bottomNavigation.setNotificationImmediate(title, MESSAGES_PAGE);
        }
    }

    protected void screenAnalytics() {}

    protected NavigationBuilder buildNavigation() {
        return new CustomLayoutNavigationBuilder((inflater, container) -> null);
    }
}
