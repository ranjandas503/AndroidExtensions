package com.menasr.andy.extensionFunctions

import android.os.SystemClock
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.menasr.andy.AndyExtApp

private var mLastClickTime: Long = 0

/**Method to check weather we can call again or not*/
fun canClickAgain(): Boolean {
    // Preventing multiple clicks, using threshold of 1 second
    return if (SystemClock.elapsedRealtime() - mLastClickTime < AndyExtApp.doubleClickDuration) {
        false
    } else {
        mLastClickTime = SystemClock.elapsedRealtime()
        true
    }
}

/**Set adapter of recyclerView
 * @param recyclerView your recyclerView
 * @param yourAdapter your adapter(must extend RecyclerView.Adapter)
 * @param layoutOrientation LinearLayoutManager orientation of adapter, default is RecyclerView.VERTICAL
 * @param fixedSize isFixed size of recyclerView, default is true*/
fun <T : RecyclerView.Adapter<*>> initRecyclerViewAdapter(
    recyclerView: RecyclerView,
    yourAdapter: T,
    layoutOrientation: Int = RecyclerView.VERTICAL,
    fixedSize: Boolean = true
) {
    recyclerView.apply {
        layoutManager = LinearLayoutManager(this.context, layoutOrientation, false)
        adapter = yourAdapter
        setHasFixedSize(fixedSize)
    }
}

/**Set adapter of recyclerView
 * @param recyclerView your recyclerView
 * @param yourAdapter your adapter(must extend RecyclerView.Adapter)
 * @param yourLayoutManager Pass your own layout manager
 * @param fixedSize isFixed size of recyclerView, default is true*/
fun <T : RecyclerView.Adapter<*>> initRecyclerViewAdapter(
    recyclerView: RecyclerView,
    yourAdapter: T,
    yourLayoutManager:RecyclerView.LayoutManager,
    fixedSize: Boolean = true
) {
    recyclerView.apply {
        layoutManager = yourLayoutManager
        adapter = yourAdapter
        setHasFixedSize(fixedSize)
    }
}