package hssc.androidview.views.recycle.horizontal;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;

import hssc.androidview.views.recycle.FlexibleDividerDecoration;

/**
 * Created by li on 2017/9/28.
 */
public class HorizontalDividerBuilder extends FlexibleDividerDecoration.Builder<HorizontalDividerBuilder> {

    public HorizontalMarginProvider mHorizontalMarginProvider = new HorizontalMarginProvider() {
        @Override
        public int dividerLeftMargin(int position, RecyclerView parent) {
            return 0;
        }

        @Override
        public int dividerRightMargin(int position, RecyclerView parent) {
            return 0;
        }
    };

    public HorizontalDividerBuilder(Context context) {
        super(context);
    }

    public HorizontalDividerBuilder margin(final int leftMargin, final int rightMargin) {
        return marginProvider(new HorizontalMarginProvider() {
            @Override
            public int dividerLeftMargin(int position, RecyclerView parent) {
                return leftMargin;
            }

            @Override
            public int dividerRightMargin(int position, RecyclerView parent) {
                return rightMargin;
            }
        });
    }

    public HorizontalDividerBuilder margin(int horizontalMargin) {
        return margin(horizontalMargin, horizontalMargin);
    }

    public HorizontalDividerBuilder marginResId(@DimenRes int leftMarginId, @DimenRes int rightMarginId) {
        return margin(mResources.getDimensionPixelSize(leftMarginId),
                mResources.getDimensionPixelSize(rightMarginId));
    }

    public HorizontalDividerBuilder marginResId(@DimenRes int horizontalMarginId) {
        return marginResId(horizontalMarginId, horizontalMarginId);
    }

    public HorizontalDividerBuilder marginProvider(HorizontalMarginProvider provider) {
        mHorizontalMarginProvider = provider;
        return this;
    }

    public HorizontalDividerItemDecoration build() {
        checkBuilderParams();
        return new HorizontalDividerItemDecoration(this);
    }
}
