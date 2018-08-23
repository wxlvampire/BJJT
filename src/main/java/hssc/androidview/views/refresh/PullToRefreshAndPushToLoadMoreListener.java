package hssc.androidview.views.refresh;

/**
 * Created by li on 2017/11/6.
 */
public interface PullToRefreshAndPushToLoadMoreListener {
    /**
     * 刷新时会去回调此方法，在方法内编写具体的刷新逻辑。注意此方法是在主线程中调用的， 需要另开线程来进行耗时操作。
     */
    void onRefresh();

    /**
     * 加载更多时会去回调此方法，在方法内编写具体的加载更多逻辑。注意此方法是在主线程中调用的， 需要另开线程来进行耗时操作。
     */
    void onLoadMore();
}
