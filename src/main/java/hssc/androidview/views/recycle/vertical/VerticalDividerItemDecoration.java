package hssc.androidview.views.recycle.vertical;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import hssc.androidview.views.recycle.FlexibleDividerDecoration;

/**
 * Created by yqritc on 2015/01/15.
 */
public class VerticalDividerItemDecoration extends FlexibleDividerDecoration {

    private VerticalMarginProvider mMarginProvider;

    protected VerticalDividerItemDecoration(VerticalDividerBuilder builder) {
        super(builder);
        mMarginProvider = builder.mMarginProvider;
    }

    @Override
    protected Rect getDividerBound(int position, RecyclerView parent, View child) {
        Rect bounds = new Rect(0, 0, 0, 0);
        int transitionX = (int) ViewCompat.getTranslationX(child);
        int transitionY = (int) ViewCompat.getTranslationY(child);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        bounds.top = child.getTop() + transitionY;
        bounds.bottom = child.getBottom() + transitionY;

        int dividerSize = getDividerSize(position, parent);
        if (mDividerType == DividerType.DRAWABLE || mDividerType == DividerType.SPACE) {
            if (alignTopEdge(parent, position)) {
                bounds.top += mMarginProvider.dividerTopMargin(position, parent);
            }
            if (alignBottomEdge(parent, position)) {
                bounds.bottom -= mMarginProvider.dividerBottomMargin(position, parent);
            }

            bounds.left = child.getRight() + params.rightMargin + transitionX;
            bounds.right = bounds.left + dividerSize;
        } else {
            // set center point of divider
            int halfSize = dividerSize / 2;
            bounds.left = child.getRight() + params.rightMargin + halfSize + transitionX;
            bounds.right = bounds.left;
        }

        if (mPositionInsideItem) {
            bounds.left -= dividerSize;
            bounds.right -= dividerSize;
        }

        return bounds;
    }

    private boolean alignTopEdge(RecyclerView parent, int position) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup lookup = manager.getSpanSizeLookup();
            int spanCount = manager.getSpanCount();
            if (manager.getOrientation() == GridLayoutManager.VERTICAL) // 垂直布局
            {
                if (manager.getReverseLayout()) {
                    if (lookup.getSpanGroupIndex(position, spanCount) ==
                            lookup.getSpanGroupIndex(parent.getAdapter().getItemCount() - 1, spanCount)) // 第一行
                    {
                        return true;
                    }
                } else {
                    if (lookup.getSpanGroupIndex(position, spanCount) == 0) // 第一行
                    {
                        return true;
                    }
                }
            } else // 水平布局
            {
                return lookup.getSpanIndex(position, spanCount) == 0;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) manager.findViewByPosition(position).getLayoutParams();
            int spanCount = manager.getSpanCount();
            int spanIndex = params.getSpanIndex();

            if (manager.getOrientation() == StaggeredGridLayoutManager.VERTICAL) // 垂直布局
            {
                if (manager.getReverseLayout()) {
                    int[] lastPosition = manager.findLastVisibleItemPositions(null);

                    boolean hasTop = false;
                    for (int p : lastPosition) {
                        if (p != position && p != -1) {
                            StaggeredGridLayoutManager.LayoutParams params1 = (StaggeredGridLayoutManager.LayoutParams) manager.findViewByPosition(p).getLayoutParams();
                            if (params1.getSpanIndex() == spanIndex) {
                                hasTop = true;
                                break;
                            }
                        }
                    }
                    return !hasTop;
                } else {
                    return position < spanCount;
                }
            } else // 水平布局
            {
                return params.getSpanIndex() == 0;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            return true;
        }
        return false;
    }

    private boolean alignBottomEdge(RecyclerView parent, int position) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup lookup = manager.getSpanSizeLookup();
            int spanCount = manager.getSpanCount();
            int itemCount = parent.getAdapter().getItemCount();
            if (manager.getOrientation() == GridLayoutManager.VERTICAL) // 垂直布局
            {
                if (manager.getReverseLayout()) {
                    return lookup.getSpanGroupIndex(position, spanCount) == 0;
                } else {
                    int lastRowFirstPosition = 0;
                    for (int i = itemCount - 1; i >= 0; i--) {
                        if (lookup.getSpanIndex(i, spanCount) == 0) {
                            lastRowFirstPosition = i;
                            break;
                        }
                    }
                    if (position >= lastRowFirstPosition) {
                        return true;
                    }
                }
            } else // 水平布局
            {
                return positionTotalSpanSize(manager, position) == spanCount;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) manager.findViewByPosition(position).getLayoutParams();
            int spanCount = manager.getSpanCount();
            int spanIndex = params.getSpanIndex();

            if (manager.getOrientation() == StaggeredGridLayoutManager.VERTICAL) // 垂直布局
            {
                if (manager.getReverseLayout()) {
                    return position < spanCount;
                } else {
                    int[] lastPosition = manager.findLastVisibleItemPositions(null);

                    boolean hasBottom = false;
                    for (int p : lastPosition) {
                        if (p != position && p != -1) {
                            StaggeredGridLayoutManager.LayoutParams params1 = (StaggeredGridLayoutManager.LayoutParams) manager.findViewByPosition(p).getLayoutParams();
                            if (params1.getSpanIndex() == spanIndex) {
                                hasBottom = true;
                                break;
                            }
                        }
                    }
                    return !hasBottom;
                }
            } else // 水平布局
            {
                return spanIndex == spanCount - 1;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            return true;
        }
        return false;
    }

    @Override
    protected void setItemOffsets(Rect outRect, int position, RecyclerView parent) {
        if (mPositionInsideItem) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        outRect.set(0, 0, getDividerSize(position, parent), 0);
    }

    private int getDividerSize(int position, RecyclerView parent) {
        if (mPaintProvider != null) {
            return (int) mPaintProvider.dividerPaint(position, parent).getStrokeWidth();
        } else if (mSizeProvider != null) {
            return mSizeProvider.dividerSize(position, parent);
        } else if (mDrawableProvider != null) {
            Drawable drawable = mDrawableProvider.drawableProvider(position, parent);
            return drawable.getIntrinsicWidth();
        }
        throw new RuntimeException("failed to get size");
    }
}