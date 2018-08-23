package hssc.androidview.views.recycle.horizontal;

import android.support.v7.widget.RecyclerView;

/**
 * Created by li on 2017/9/28.
 */
public interface HorizontalMarginProvider {

    /**
     * Returns left margin of divider.
     *
     * @param position Divider position (or group index for GridLayoutManager)
     * @param parent   RecyclerView
     * @return left margin
     */
    int dividerLeftMargin(int position, RecyclerView parent);

    /**
     * Returns right margin of divider.
     *
     * @param position Divider position (or group index for GridLayoutManager)
     * @param parent   RecyclerView
     * @return right margin
     */
    int dividerRightMargin(int position, RecyclerView parent);
}
