/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hssc.androidview.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * © 2012 amsoft.cn 名称：OuterScrollView.java 描述：这个ScrollView与内部的滑动不冲突
 *hssc.androidview.views.OuterScrollView
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-5-21 下午3:59:38
 */
public class OuterScrollView extends ScrollView {

	/** The m gesture detector. */
	private GestureDetector mGestureDetector;
	private boolean isDeal = true;
	private OnScrollListener onScrollListener;
	/**
	 * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
	 */
	private int lastScrollY;

	/**
	 * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
	 */
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			int scrollY = OuterScrollView.this.getScrollY();

			//此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
			if(lastScrollY != scrollY){
				lastScrollY = scrollY;
				handler.sendMessageDelayed(handler.obtainMessage(), 5);
			}
			if(onScrollListener != null){
				onScrollListener.onScroll(scrollY);
			}

		};

	};


	/**
	 * Instantiates a new ab pager scroll view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public OuterScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
		setFadingEdgeLength(0);
	}

	/**
	 * 设置滚动接口
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}


	/**
	 * @param ev the ev
	 * @return true, if successful
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
//		return isDeal;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(onScrollListener != null){
			onScrollListener.onScroll(lastScrollY = this.getScrollY());
		}
		switch(ev.getAction()){
			case MotionEvent.ACTION_UP:
				handler.sendMessageDelayed(handler.obtainMessage(), 5);
				break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * The Class YScrollDetector.
	 */
	class YScrollDetector extends SimpleOnGestureListener {

		/**
		 * @param e1        the e1
		 * @param e2        the e2
		 * @param distanceX the distance x
		 * @param distanceY the distance y
		 * @return true, if successful
		 */
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (getScrollY() == 0) {
				return true;
			}
			if (distanceY != 0 && distanceX != 0) {

			}
			if (Math.abs(distanceY) >= Math.abs(distanceX)) {
				return true;
			}
			return false;
		}
	}
}
