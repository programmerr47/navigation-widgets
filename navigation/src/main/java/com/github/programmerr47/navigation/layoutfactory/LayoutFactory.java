package com.github.programmerr47.navigation.layoutfactory;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface LayoutFactory {
    View produceLayout(LayoutInflater inflater, @Nullable ViewGroup container);
}
