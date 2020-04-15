package com.sample.test

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.menasr.andyext.constantObjects.Andy
import com.menasr.andyext.constantObjects.ConstantUtils
import com.menasr.andyext.customClasses.LazySwappableRecyclerAdapter
import com.menasr.andyext.extensionFunctions.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loge(ConstantUtils.CHARACTER_POOL)

        sampleview.startPulseAnimation()

        val adapter = AdapterLazySwappableRecycler(recyclerView)
        Handler().postDelayed({adapter.canSwapOrDrag(true)},4000)
        adapter.addLazyLoadCallback(object : LazySwappableRecyclerAdapter.LazyLoadRecyclerCallback {
            override fun onLoadMore() {
                if (count <= 20)
                    adapter.addItem(generateMoreItems(5))
                else adapter.canLazyLoadAgain(false)
            }

        })

        initRecyclerViewAdapter(
            recyclerView,
            adapter
        )

        adapter.addItem(generateMoreItems(5))
    }

    fun generateMoreItems(size: Int): MutableList<CustomModel> {
        val list = ArrayList<CustomModel>()
        val modifiedSize = count + size
        (count..modifiedSize).onEach {
            list.add(CustomModel("name $it", "surname"))
        }
        count = modifiedSize

        return list
    }
}
