package com.maloshpal.exchangerates.ui.util

import android.app.Activity
import android.support.v4.app.Fragment
import android.util.Log

import org.androidannotations.api.view.HasViews
import org.androidannotations.api.view.OnViewChangedListener
import java.lang.IllegalArgumentException

import java.util.HashMap

object ClassUtils {

// MARK: - Properties

    private val TAG = ClassUtils::class.java.simpleName

    // Cache of previously looked up generated classes
    private val sClassMap = HashMap<Class<*>, Class<*>>()

// MARK: - Methods

    fun getGeneratedClass(clazz: Class<*>): Class<*> {
        try {
            return loadGeneratedClass(clazz)
        }
        catch (ex: ClassNotFoundException) {
            Log.e(TAG, Log.getStackTraceString(ex))
            throw IllegalArgumentException("No class is generated for passed class", ex)
        }
    }

// MARK: - Private Methods

    @Throws(ClassNotFoundException::class)
    private fun loadGeneratedClass(clazz: Class<*>?): Class<*> {
        requireNotNull(clazz) { "Clazz is null" }
        require(Activity::class.java.isAssignableFrom(clazz) || Fragment::class.java.isAssignableFrom(clazz))
        { "Only classes inherited from Activity or Fragment are allowed" }

        var foundClass = sClassMap[clazz]
        if (foundClass == null) {

            val cname = clazz.canonicalName!! + "_"
            foundClass = Class.forName(cname, false, clazz.classLoader)

            // Check if found class was generated with AndroidAnnotations
            if (!clazz.isAssignableFrom(foundClass!!) || !HasViews::class.java.isAssignableFrom(foundClass)
                    || !OnViewChangedListener::class.java.isAssignableFrom(foundClass)) {
                throw ClassNotFoundException("Found class was not generated with AndroidAnnotations: $cname")
            }

            // Put this found class id to cache
            sClassMap[clazz] = foundClass
        }

        // Done
        return foundClass
    }
}