package hssc.androidview.views;

import android.text.SpannableString;

/**
 * Created by sks on 2016/1/20.
 */
public interface TabsTitleInterface {
    SpannableString getTabTitle(int position);
    int getTabDrawableBottom(int position);

    int getTabDrawableLeft(int position);

    int getTabDrawableRight(int position);

    int getTabDrawableTop(int position);
}
