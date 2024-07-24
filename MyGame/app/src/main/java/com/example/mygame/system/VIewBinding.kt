package com.example.mygame.system

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.viewbinding.ViewBinding


inline fun <T : ViewBinding> FragmentActivity.viewBindings(
    crossinline bind: (View) -> T
) = object : Lazy<T> {

    private var mCached: T? = null

    override val value: T
        get() = mCached ?: bind(
            findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        ).also {
            mCached = it
        }

    override fun isInitialized(): Boolean = mCached != null

}

inline fun <T : ViewBinding> Fragment.viewBindings(
    crossinline bind: (View) -> T
) = object : Lazy<T> {

    private var mCached: T? = null

    private val mObserver = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            mCached = null
        }
    }

    override val value: T
        get() = mCached ?: bind(requireView()).also {
            viewLifecycleOwner.lifecycle.addObserver(mObserver)
            mCached = it
        }

    override fun isInitialized(): Boolean = mCached != null

}
