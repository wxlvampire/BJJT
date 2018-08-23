package hssc.androidview.views.recycle.horizontal;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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
public class HorizontalDividerItemDecoration extends FlexibleDividerDecoration {

    private HorizontalMarginProvider mHorizontalMarginProvider;

    protected HorizontalDividerItemDecoration(HorizontalDividerBuilder builder) {
        super(builder);
        mHorizontalMarginProvider = builder.mHorizontalMarginProvider;
    }

    @Override
    protected Rect getDividerBound(int position, RecyclerView parent, View child) {
        Rect bounds = new Rect(0, 0, 0, 0);
        int transitionX = (int) ViewCompat.getTranslationX(child);
        int transitionY = (int) ViewCompat.getTranslationY(child);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        bounds.left = child.getLeft() + transitionX;
        bounds.right = child.getRight() + transitionX;

        int dividerSize = getDividerSize(position, parent);
        if (mDividerType == DividerType.DRAWABLE || mDividerType == DividerType.SPACE) {
            if (alignLeftEdge(parent, position)) {
                bounds.left += mHorizontalMarginProvider.dividerLeftMargin(position, parent);
            }

            if (alignRightEdge(parent, position)) {
                bounds.right -= mHorizontalMarginProvider.dividerRightMargin(position, parent);
            } else {
                // 交叉位置特殊处理
                bounds.right += getDividerSize(position, parent);
            }
            bounds.top = child.getBottom() + params.bottomMargin + transitionY;
            bounds.bottom = bounds.top + dividerSize;
        } else {
            int halfSize = dividerSize / 2;
            bounds.top = child.getBottom() + params.bottomMargin + halfSize + transitionY;
            bounds.bottom = bounds.top;
        }

        if (mPositionInsideItem) {
            bounds.top -= dividerSize;
            bounds.bottom -= dividerSize;
        }

        return bounds;
    }

    private boolean alignLeftEdge(RecyclerView parent, int position) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup lookup = manager.getSpanSizeLookup();
            int spanCount = manager.getSpanCount();
            if (manager.getOrientation() == GridLayoutManager.VERTICAL) // 垂直布局
            {
                if (lookup.getSpanIndex(position, spanCount) == 0) // 第一列
                {
                    return true;
                }
            } else // 水平布局
            {
                if (manager.getReverseLayout()) {
                    return lookup.getSpanGroupIndex(position, spanCount) == lookup.getSpanGroupIndex(parent.getAdapter().getItemCount() - 1, spanCount);
                } else {
                    if (lookup.getSpanGroupIndex(position, spanCount) == 0) {
                        return true;
                    }
                }
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) manager.findViewByPosition(position).getLayoutParams();
            int spanCount = manager.getSpanCount();
            int spanIndex = params.getSpanIndex();

            if (manager.getOrientation() == StaggeredGridLayoutManager.VERTICAL) // 垂直布局
            {
                return spanIndex == 0;
            } else // 水平布局
            {
                if (manager.getReverseLayout()) {
                    int[] lastPosition = manager.findLastVisibleItemPositions(null);
                    boolean hasDirectionAlign = false;
                    for (int p : lastPosition) {
                        if (p != position && p != -1) {
                            StaggeredGridLayoutManager.LayoutParams params1 = (StaggeredGridLayoutManager.LayoutParams) manager.findViewByPosition(p).getLayoutParams();
                            if (params1.getSpanIndex() == spanIndex) {
                                hasDirectionAlign = true;
                                break;
                            }
                        }
                    }
                    return !hasDirectionAlign;
                } else {
                    return position < spanCount;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            return true;
        }
        return false;
    }

    private boolean alignRightEdge(RecyclerView parent, int position) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup lookup = manager.getSpanSizeLookup();
            int spanCount = manager.getSpanCount();
            int itemCount = parent.getAdapter().getItemCount();
            if (manager.getOrientation() == GridLayoutManager.VERTICAL) // 垂直布局
            {
                if (positionTotalSpanSize(manager, position) == spanCount) {
                    return true;
                }
            } else // 水平布局
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
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) manager.findViewByPosition(position).getLayoutParams();
            int spanCount = manager.getSpanCount();
            int spanIndex = params.getSpanIndex();

            if (manager.getOrientation() == StaggeredGridLayoutManager.VERTICAL) // 垂直布局
            {
                return spanIndex == spanCount - 1;
            } else // 水平布局
            {
                if (manager.getReverseLayout()) {
                    return position < spanCount;
                } else {
                    int[] lastPosition = manager.findLastVisibleItemPositions(null);

                    boolean hasRight = false;
                    for (int p : lastPosition) {
                        if (p != position && p != -1) {
                            StaggeredGridLayoutManager.LayoutParams params1 = (StaggeredGridLayoutManager.LayoutParams) manager.findViewByPosition(p).getLayoutParams();
                            if (params1.getSpanIndex() == spanIndex) {
                                hasRight = true;
                                break;
                            }
                        }
                    }
                    return !hasRight;
                }
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
        outRect.set(0, 0, 0, getDividerSize(position, parent));
    }

    private int getDividerSize(int position, RecyclerView parent) {
        if (mPaintProvider != null) {
            return (int) mPaintProvider.dividerPaint(position, parent).getStrokeWidth();
        } else if (mSizeProvider != null) {
            return mSizeProvider.dividerSize(position, parent);
        } else if (mDrawableProvider != null) {
            Drawable drawable = mDrawableProvider.drawableProvider(position, parent);
            return drawable.getIntrinsicHeight();
        } else if (mSpaceProvider != null) {
            return mSpaceProvider.dividerSize(position, parent);
        }
        throw new RuntimeException("failed to get size");
    }
}