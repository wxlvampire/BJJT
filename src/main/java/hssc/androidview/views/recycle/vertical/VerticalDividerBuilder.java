package hssc.androidview.views.recycle.vertical;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;

import hssc.androidview.views.recycle.FlexibleDividerDecoration;

/**
 * Created by li on 2017/9/28.
 */
public class VerticalDividerBuilder extends FlexibleDividerDecoration.Builder<VerticalDividerBuilder> {

    public VerticalMarginProvider mMarginProvider = new VerticalMarginProvider() {
        @Override
        public int dividerTopMargin(int position, RecyclerView parent) {
            return 0;
        }

        @Override
        public int dividerBottomMargin(int position, RecyclerView parent) {
            return 0;
        }
    };

    public VerticalDividerBuilder(Context context) {
        super(context);
    }

    public VerticalDividerBuilder margin(final int topMargin, final int bottomMargin) {
        return marginProvider(new VerticalMarginProvider() {
            @Override
            public int dividerTopMargin(int position, RecyclerView parent) {
                return topMargin;
            }

            @Override
            public int dividerBottomMargin(int position, RecyclerView parent) {
                return bottomMargin;
            }
        });
    }

    public VerticalDividerBuilder margin(int verticalMargin) {
        return margin(verticalMargin, verticalMargin);
    }

    public VerticalDividerBuilder marginResId(@DimenRes int topMarginId, @DimenRes int bottomMarginId) {
        return margin(mResources.getDimensionPixelSize(topMarginId),
                mResources.getDimensionPixelSize(bottomMarginId));
    }

    public VerticalDividerBuilder marginResId(@DimenRes int verticalMarginId) {
        return marginResId(verticalMarginId, verticalMarginId);
    }

    public VerticalDividerBuilder marginProvider(VerticalMarginProvider provider) {
        mMarginProvider = provider;
        return this;
    }

    public VerticalDividerItemDecoration build() {
        checkBuilderParams();
        return new VerticalDividerItemDecoration(this);
    }
}
