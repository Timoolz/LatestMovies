package com.olamide.latestmovies.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SortType {


    public static final String POPULAR = "POPULAR";
    public static final String TOP_RATED = "TOP_RATED";
    public static final String FAVOURITE = "FAVOURITE";

    @StringDef({POPULAR, TOP_RATED, FAVOURITE})
    @Retention(RetentionPolicy.SOURCE)

    public @interface SortTypeDef {}

    public final String sortType;

    public SortType(@SortTypeDef String sortType) {
        this.sortType = sortType;
    }


}
