package hssc.androidview.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
public class AutoSwipeRefreshLayout extends SwipeRefreshLayout{
    private ListView view;
    public AutoSwipeRefreshLayout(Context context) {
        super(context);
    }

    public AutoSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setView(ListView view) {
        this.view = view;
    }

    /**
     * 自动刷新
     */
    public void autoRefresh(boolean autoRefresh) {
        if (autoRefresh) {
            try {
                Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
                mCircleView.setAccessible(true);
                View progress = (View) mCircleView.get(this);
                progress.setVisibility(VISIBLE);

                Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
                setRefreshing.setAccessible(true);
                setRefreshing.invoke(this, true, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean canChildScrollUp() {
        if (view != null) {
            final AbsListView absListView = (AbsListView) view;
            return absListView.getChildCount() > 0
                    && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                    .getTop() < absListView.getPaddingTop());
        }
        return super.canChildScrollUp();
    }
}
