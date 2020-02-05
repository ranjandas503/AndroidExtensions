@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.menasr.andyext.customClasses.MultipleParametrizedFactory
import com.menasr.andyext.customClasses.ParametrizedFactory

/**This function provides constructor view model object
 *
 * call is like <b>ViewModelProvider(context,viewModelFactory(YourViewModelClass(constructorObject).get(YourViewModelClass::class.java))</b>
 *
 * @return YourViewModelClass
 *
 * You call call [initViewModel] for a more cleaner approach*/
inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) = object : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
}

//-----------------------------------------------------------------------------------------------------------------------------------

/**initialize your viewModel with single argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> Fragment.initViewModel(constructorObject: Any, viewModelClass: Class<V>): V {
    return ViewModelProvider(this, ParametrizedFactory(constructorObject)).get(viewModelClass)
}

/**Pass your constructor with parameter, like
 * for ex: <b>initViewModel(YourViewModelClass(constructorObject), YourViewModelClass::class.java)</b>
 *
 * return your provided view model class*/
fun <V : ViewModel> Fragment.initViewModel(constructorObject: V, viewModelClass: Class<V>): V = ViewModelProvider(this, viewModelFactory { constructorObject }).get(viewModelClass)

/**initialize your viewModel with single argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> FragmentActivity.initViewModel(constructorObject: Any, viewModelClass: Class<V>): V {
    return ViewModelProvider(this, ParametrizedFactory(constructorObject)).get(viewModelClass)
}

/**Pass your constructor with parameter, like
 * for ex: <b>initViewModel(YourViewModelClass(constructorObject), EditSeriesViewModel::class.java)</b>
 *
 * return your provided view model class*/
fun <V : ViewModel> FragmentActivity.initViewModel(constructorObject: V, viewModelClass: Class<V>): V = ViewModelProvider(this, viewModelFactory { constructorObject }).get(viewModelClass)

/**initialize your viewModel with single argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> AppCompatActivity.initViewModel(constructorObject: Any, viewModelClass: Class<V>): V {
    return ViewModelProvider(this, ParametrizedFactory(constructorObject)).get(viewModelClass)
}

/**Pass your constructor with parameter, like
 * for ex: <b>initViewModel(YourViewModelClass(constructorObject), EditSeriesViewModel::class.java)</b>
 *
 * return your provided view model class*/
fun <V : ViewModel> AppCompatActivity.initViewModel(constructorObject: V, viewModelClass: Class<V>): V = ViewModelProvider(this, viewModelFactory { constructorObject }).get(viewModelClass)

/**initialize your viewModel with single argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> DialogFragment.initViewModel(constructorObject: Any, viewModelClass: Class<V>): V {
    return ViewModelProvider(this, ParametrizedFactory(constructorObject)).get(viewModelClass)
}

/**Pass your constructor with parameter, like
 * for ex: <b>initViewModel(YourViewModelClass(constructorObject), EditSeriesViewModel::class.java)</b>
 *
 * return your provided view model class*/
fun <V : ViewModel> DialogFragment.initViewModel(constructorObject: V, viewModelClass: Class<V>): V = ViewModelProvider(this, viewModelFactory { constructorObject }).get(viewModelClass)

//--------------------------------------------------------------------------------------------------------------------------------------------

/**initialize your viewModel with multiple argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> DialogFragment.initViewModel(constructorObject: Array<out Any>, viewModelClass: Class<V>): V {
    return ViewModelProvider(this, MultipleParametrizedFactory(constructorObject)).get(viewModelClass)
}

/**initialize your viewModel with multiple argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> Fragment.initViewModel(constructorObject: Array<out Any>, viewModelClass: Class<V>): V {
    return ViewModelProvider(this, MultipleParametrizedFactory(constructorObject))
            .get(viewModelClass)
}

/**initialize your viewModel with multiple argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> FragmentActivity.initViewModel(constructorObject: Array<out Any>, viewModelClass: Class<V>): V {
    return ViewModelProvider(this, MultipleParametrizedFactory(constructorObject))
            .get(viewModelClass)
}

/**initialize your viewModel with multiple argument and viewModelClass
 * @param constructorObject your object which you want to pass to your ViewModel Class
 * @param viewModelClass your view model class, like <ViewModelClass>::class.java*/
fun <V : ViewModel> AppCompatActivity.initViewModel(constructorObject: Array<out Any>, viewModelClass: Class<V>): V {
    return ViewModelProvider(this, MultipleParametrizedFactory(constructorObject))
            .get(viewModelClass)
}

//---------------------------------------------------------------------------------------------------------------------------------------------