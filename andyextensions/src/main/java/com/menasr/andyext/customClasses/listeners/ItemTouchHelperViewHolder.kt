package com.menasr.andyext.customClasses.listeners

/**
 * Interface to notify an item ViewHolder of relevant callbacks from [@see android.support.v7.widget.helper.ItemTouchHelper.Callback].
 */
internal interface ItemTouchHelperViewHolder {

    /**
     * Called when the [@see ItemTouchHelper] first registers an item as being moved or swiped.
     * Implementations should update the item view to indicate it's active state.
     */
    fun onItemSelected()


    /**
     * Called when the [@see ItemTouchHelper] has completed the move or swipe, and the active item
     * state should be cleared.
     */
    fun onItemClear()
}
