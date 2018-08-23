package hssc.androidview.base;

import android.os.Bundle;

/**
 * BaseFragment接口
 */
public interface BaseFragmentInterface {

	/**
	 * 初始化对象
	 */
	public void initBoot();

	/**
	 * 初始化View
	 */
	public void initViews();

	/**
	 * 初始化数据
	 */
	public void initData(Bundle bundle);

	/**
	 * 初始化事件
	 */
	public void initEvents();
	
}
