package hssc.androidview.base;

import android.content.Intent;

/**
 * Activity基础接口
 */
public interface BaseInterface{
	
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
	 * @param data 数据
	 */
	public void initData(Intent data);

	/**
	 * 初始化事件
	 */
	public void initEvents();

}
