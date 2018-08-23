package hssc.androidview.views.animateexpandableview;
/**
 * Used for holding information regarding the group.
 */
public class GroupInfo {
    public boolean animating = false;
    public boolean expanding = false;
    public int firstChildPosition;
    /**
     * This variable contains the last known height value of the dummy view.
     * We save this information so that if the user collapses a group
     * before it fully expands, the collapse animation will start from the
     * CURRENT height of the dummy view and not from the full expanded
     * height.
     */
    public int dummyHeight = -1;
}
