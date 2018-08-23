package hssc.androidview.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.ScrollView;

/**
 * 嵌套在ScrollView中的GridView
 */
public class GridviewForScroll extends GridView {
	private ScrollView parentScrollView;

	public GridviewForScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GridviewForScroll(Context context) {
		super(context);
	}

	public GridviewForScroll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setParentScrollView(ScrollView parentScrollView) {
		this.parentScrollView = parentScrollView;
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
