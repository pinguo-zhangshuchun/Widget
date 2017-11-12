package com.kari.widget.refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * File:   RefreshLayout.java
 * Author: kari
 * Date:   17-11-12 on 20:08
 */
public class RefreshLayout extends ViewGroup implements NestedScrollingParent {

    final static String TAG = "RefreshLayout";
    final static float DEFAULT_HEADER_HEIGHT = 0.2f;
    final static float DEFAULT_FOOTER_HEIGHT = 0.2f;

    private Header mHeader;
    private View mContent;
    private Footer mFooter;

    private int mHeaderHeight;
    private int mFooterHeight;

    private boolean mEnablePull;
    private boolean mEnablePush;

    private int mLastY;
    private boolean mCanPull;

    private NestedScrollingParentHelper mHelper;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        mHelper = new NestedScrollingParentHelper(this);
        mEnablePull = true;
        mEnablePush = true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        retrieveChild();
        if (mContent == null) {
            throw new RuntimeException("No content found!");
        }
    }

    private void retrieveChild() {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            RefreshLayout.LayoutParams lp = (RefreshLayout.LayoutParams) child.getLayoutParams();
            switch (lp.getRole()) {
                case LayoutParams.ROLE_HEADER:
                    if (child instanceof Header) {
                        mHeader = (Header) child;
                    } else {
                        Log.e(TAG, "invalid header (header must implements RefreshLayout.Header) !");
                    }
                    break;

                case LayoutParams.ROLE_FOOTER:
                    if (child instanceof Footer) {
                        mFooter = (Footer) child;
                    } else {
                        Log.e(TAG, "invalid footer (footer must implements RefreshLayout.Footer) !");
                    }
                    break;

                case LayoutParams.ROLE_CONTENT:
                    mContent = child;
                    break;
            }
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int width = MeasureSpec.getSize(widthSpec);
        int height = MeasureSpec.getSize(heightSpec);
        Log.d(TAG, "onMeasure:width=" + width + ",height=" + height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mContent.layout(l, t, r, b);
        if (mHeader != null) {
            View header = (View) mHeader;
            header.layout(l, -mHeaderHeight, r, 0);
        }
    }

    public void setRefreshComplete() {

    }

    public void setLoadMoreComplete() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) event.getY();
                Log.d(TAG, "down:" + mLastY);
                break;

            case MotionEvent.ACTION_MOVE:
                mCanPull = event.getY() < mLastY;
                mLastY = (int) event.getY();
                Log.d(TAG, "move:" + mLastY);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if (target == mContent &&
                nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL &&
                !ViewCompat.canScrollVertically(mContent, -1)) {

            return true;

        } else {
            return false;
        }
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        Log.d(TAG, "onAccepted:child:" + child.getClass().getName() + ", target:" + target.getClass().getName());
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.d(TAG, "onStop target:" + target.getClass().getName());

    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return null;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet set) {
        return new RefreshLayout.LayoutParams(getContext(), set);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public final static int ROLE_HEADER = 0x0001;
        public final static int ROLE_CONTENT = 0x0001 << 1;
        public final static int ROLE_FOOTER = 0x0001 << 2;

        private int mRole;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray ta = c.obtainStyledAttributes(attrs, R.styleable.RefreshLayout);
            mRole = ta.getInt(R.styleable.RefreshLayout_rl_role, 0);
            ta.recycle();
        }

        /**
         * @returs
         * @see #ROLE_CONTENT
         * @see #ROLE_HEADER
         * @see #ROLE_FOOTER
         */
        public int getRole() {
            return mRole;
        }
    }

    public interface Header {
        void onPullStart(RefreshLayout layout);

        void onPulling(RefreshLayout layout, float progress);

        void onPullFinish(RefreshLayout layout);
    }

    public interface Footer {
        void onPushStart(RefreshLayout layout);

        void onPushing(RefreshLayout layout, float progress);

        void onPushFinish(RefreshLayout layout);
    }

    public interface OnRefreshListener {
        void onRefresh(RefreshLayout layout);
    }
}
