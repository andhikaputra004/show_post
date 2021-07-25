package com.example.kumparan.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(body))
}

inline fun <T : Any, L : LiveData<T>> Fragment.observeNonNull(
    liveData: L,
    crossinline body: (T) -> Unit
) {
    liveData.observe(viewLifecycleOwner, Observer { it?.let(body) })
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

inline fun <T : Any, L : LiveData<T>> LifecycleOwner.observeNonNull(
    liveData: L,
    crossinline body: (T) -> Unit
) {
    liveData.observe(this, Observer { it?.let(body) })
}


fun <T> Fragment.viewBinding(initialise: () -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

        private var binding: T? = null

        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            binding
                ?: initialise().also {
                    binding = it
                    this@viewBinding.viewLifecycleOwner.lifecycle.addObserver(this)
                }
    }