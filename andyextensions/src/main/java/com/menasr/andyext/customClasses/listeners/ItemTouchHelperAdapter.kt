package com.menasr.andyext.customClasses.listeners

/**
 * Interface to listen for a move or dismissal event from a [androidx.recyclerview.widget.ItemTouchHelper.Callback].
 */
internal interface ItemTouchHelperAdapter {

    /**
     * Called when an item has been dragged far enough to trigger a move. This is called every time
     * an item is shifted, and **not** at the end of a "drop" event.<br></br>
     * <br></br>
     * Implementations should call [androidx.recyclerview.widget.RecyclerView.Adapter#notifyItemMoved(int, int)] after
     * adjusting the underlying data to reflect this move.
     *
     * @param fromPosition The start position of the moved item.
     * @param toPosition   Then resolved position of the moved item.
     * @return True if the item was moved to the new adapter position.
     */
    fun onItemDrag(fromPosition: Int, toPosition: Int): Boolean


    /**
     * Called when an item has been dismissed by a swipe.<br></br>
     * <br></br>
     * Implementations should call [androidx.recyclerview.widget.RecyclerView.Adapter#notifyItemRemoved(int)] after
     * adjusting the underlying data to reflect this removal.
     *
     * @param position The position of the item dismissed.
     */
    fun onItemDismiss(position: Int,swipeDirection:Int)
}
