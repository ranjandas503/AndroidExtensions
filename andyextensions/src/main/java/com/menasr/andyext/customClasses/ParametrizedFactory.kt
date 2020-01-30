package com.menasr.andyext.customClasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.Constructor

/**for passing single argument in viewModel
 * Use [ViewModelProviders.of(this,ParametrizedFactory(object)).get(YourViewModel::class.java)]
 * */
class ParametrizedFactory(private val T: Any) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val constructor: Constructor<T> = modelClass.getDeclaredConstructor(T::class.java)
        return constructor.newInstance(T)
    }
}
