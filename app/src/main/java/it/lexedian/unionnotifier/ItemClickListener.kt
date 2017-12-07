package it.lexedian.unionnotifier

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Created by Daniele Pellone on 05/12/2017.
 */
class ItemClickListener(context: Context, private var listener: OnItemClickListener) : RecyclerView.OnItemTouchListener {
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    var gestureDetector: GestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return true
        }
    })

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {}

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        val childView = rv?.findChildViewUnder(e?.x!!, e.y)
        if(childView != null && gestureDetector.onTouchEvent(e))
            listener.onItemClick(childView, rv.getChildAdapterPosition(childView))
        return false
    }
}