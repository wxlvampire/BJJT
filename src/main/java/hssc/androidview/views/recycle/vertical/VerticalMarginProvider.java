package hssc.androidview.views.recycle.vertical;

import android.support.v7.widget.RecyclerView;

/**
 * Created by li on 2017/9/28.
 */
public interface VerticalMarginProvider {
    /**
     * Returns top margin of divider.
     *
     * @param position Divider position (or group index for GridLayoutManager)
     * @param parent   RecyclerView
     * @return top margin
     */
    int dividerTopMargin(int position, RecyclerView parent);

    /**
     * Returns bottom margin of divider.
     *
     * @param position Divider position (or group index for GridLayoutManager)
     * @param parent   RecyclerView
     * @return bottom margin
     */
    int dividerBottomMargin(int position, RecyclerView parent);
}
