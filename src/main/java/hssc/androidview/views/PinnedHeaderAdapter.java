package hssc.androidview.views;

import android.view.View;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface PinnedHeaderAdapter {
    public static final int PINNED_HEADER_GONE = 0;
    public static final int PINNED_HEADER_VISIBLE = 1;
    public static final int PINNED_HEADER_PUSHED_UP = 2;

    int getPinnedHeaderState(int position);

    void configurePinnedHeader(View header, int position, int alpha);
}
