package com.menasr.andy

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Constructor

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
 * @param layoutOrientation orientation of adapter, default is RecyclerView.VERTICAL
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

/**for passing single argument in viewModel*/
@Suppress("unused")
class ParametrizedFactory(private val T: Any) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val constructor: Constructor<T> = modelClass.getDeclaredConstructor(T::class.java)
        return constructor.newInstance(T)
    }
}

/**For multiple arguments, in ViewModel*/
class MultipleParametrizedFactory(private var constructorParams: Array<out Any>) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        logr("Don't use callbacks or Context as parameters in order to avoid leaks!!","MultipleParametrizedFactory" )
        return when (constructorParams.size) {
            0 -> {
                modelClass.newInstance()
            }
            else -> {
                val parameterClasses: Array<Class<*>> =
                    constructorParams.map { param -> param.javaClass }.toList().toTypedArray()
                modelClass.getConstructor(*parameterClasses).newInstance(*constructorParams)
            }
        }
    }
}