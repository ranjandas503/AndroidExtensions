package com.menasr.andy.extensionFunctions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.Constructor

/**for passing single argument in viewModel*/
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