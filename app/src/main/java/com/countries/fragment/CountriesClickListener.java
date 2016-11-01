package com.readr.ro.countries.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Domnica on 11/1/2016.
 */

public class CountriesClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector mGestureDetector;
    private OnItemClickListener mOnItemClickListener;
    private RecyclerView mRecyclerView;


    public CountriesClickListener(Context context, RecyclerView recyclerView, OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
        this.mRecyclerView = recyclerView;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View children = rv.findChildViewUnder(e.getX(), e.getY());
        if (children != null && mGestureDetector.onTouchEvent(e) && mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(children, mRecyclerView.getChildAdapterPosition(children));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        // not needed in the current context
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // not needed in the current context
    }


}
