package com.syncatec.xpathlite

import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

fun <T> iz(operand: T?): Matcher<in T?> = CoreMatchers.equalTo<T>(operand)
