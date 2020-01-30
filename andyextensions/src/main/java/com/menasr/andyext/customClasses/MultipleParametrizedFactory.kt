package com.menasr.andyext.customClasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**For multiple arguments, in ViewModel
 *Use [ViewModelProviders.of(this,MultipleParametrizedFactory(arrayOfObject)).get(YourViewModel::class.java)]
 * */
class MultipleParametrizedFactory(private var constructorParams: Array<out Any>) :
        ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (constructorParams.size) {
            0 -> modelClass.newInstance()
            else -> {
                val parameterClasses: Array<Class<*>> =
                        constructorParams.map { param -> param.javaClass }.toList().toTypedArray()
                modelClass.getConstructor(*parameterClasses).newInstance(*constructorParams)
            }
        }
    }
}