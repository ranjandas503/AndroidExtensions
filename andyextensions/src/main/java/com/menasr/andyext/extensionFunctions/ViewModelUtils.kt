@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
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

//------------------------------------------------------------------------------------------

/**initialize your viewModel with single argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> Fragment.initViewModel(constructorObject: Any, viewModelClass: Class<V>): V {
    return ViewModelProviders.of(this, ParametrizedFactory(constructorObject)).get(viewModelClass)
}

/**initialize your viewModel with single argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> FragmentActivity.initViewModel(
    constructorObject: Any,
    viewModelClass: Class<V>
): V {
    return ViewModelProviders.of(this, ParametrizedFactory(constructorObject)).get(viewModelClass)
}

/**initialize your viewModel with single argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> AppCompatActivity.initViewModel(
    constructorObject: Any,
    viewModelClass: Class<V>
): V {
    return ViewModelProviders.of(this, ParametrizedFactory(constructorObject)).get(viewModelClass)
}

/**initialize your viewModel with single argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> DialogFragment.initViewModel(
    constructorObject: Any,
    viewModelClass: Class<V>
): V {
    return ViewModelProviders.of(this, ParametrizedFactory(constructorObject)).get(viewModelClass)
}

//---------------------------------------------------------------------------------------------------

/**initialize your viewModel with multiple argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> DialogFragment.initViewModel(
    constructorObject: Array<out Any>,
    viewModelClass: Class<V>
): V {
    return ViewModelProviders.of(this, MultipleParametrizedFactory(constructorObject))
        .get(viewModelClass)
}

/**initialize your viewModel with multiple argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> Fragment.initViewModel(
    constructorObject: Array<out Any>,
    viewModelClass: Class<V>
): V {
    return ViewModelProviders.of(this, MultipleParametrizedFactory(constructorObject))
        .get(viewModelClass)
}

/**initialize your viewModel with multiple argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> FragmentActivity.initViewModel(
    constructorObject: Array<out Any>,
    viewModelClass: Class<V>
): V {
    return ViewModelProviders.of(this, MultipleParametrizedFactory(constructorObject))
        .get(viewModelClass)
}

/**initialize your viewModel with multiple argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> AppCompatActivity.initViewModel(
    constructorObject: Array<out Any>,
    viewModelClass: Class<V>
): V {
    return ViewModelProviders.of(this, MultipleParametrizedFactory(constructorObject))
        .get(viewModelClass)
}