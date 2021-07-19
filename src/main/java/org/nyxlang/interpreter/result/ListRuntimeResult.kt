package org.nyxlang.interpreter.result

/**
 * Contains a list of data to be returned.
 */
data class ListRuntimeResult(override val data: ArrayList<Any>) : RuntimeResult(data)

/**
 * Creates a data runtime result.
 */
fun listResult(data: ArrayList<Any> = arrayListOf()) = ListRuntimeResult(data)