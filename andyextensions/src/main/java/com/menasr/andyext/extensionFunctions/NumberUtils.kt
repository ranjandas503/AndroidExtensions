@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import com.menasr.andyext.constantObjects.ConstantUtils.CHARACTER_POOL
import kotlin.random.Random

/**
 * This method filter the value in array, which is two frequent
 *
 * @param input  input array to filter
 * @param output output array to filter
 * @param filter factor to filter input and output(best is 0.05 to 0.25)
 * @return output array on basis of filter
 */
fun lowPassFilter(input: FloatArray, output: FloatArray?, filter: Float): FloatArray {
    if (output == null) return input
    for (i in input.indices)
        output[i] = output[i] + filter * (input[i] - output[i])

    return output
}

/**Get a random int number
 * @param fromNo starting number
 * @param toNo ending number*/
fun getRandomInt(fromNo: Int = 0, toNo: Int = 1000) = Random.nextInt(fromNo, toNo)

/**Get a random double number
 * @param fromNo starting number
 * @param toNo ending number*/
fun getRandomDouble(fromNo: Double = 0.0, toNo: Double = 1000.0) = Random.nextDouble(fromNo, toNo)

/** Get a random string
 * @param lengthOfString provide the length of the string, default is 20*/
fun getRandomString(lengthOfString: Int = 20) = (1..lengthOfString)
    .map { Random.nextInt(0, CHARACTER_POOL.size) }
    .map(CHARACTER_POOL::get)
    .joinToString("")

/**Generate random list of integer, default is 20
 * @param fromNo starting number
 * @param toNo ending number
 * @param noToBeGenerated total no of number to be generated.*/
fun generateRandomNumberList(fromNo: Int = 0, toNo: Int = 1000, noToBeGenerated: Int = 20) =
    List(noToBeGenerated) { Random.nextInt(fromNo, toNo) }

/**Generate random list of Double, default is 20
 * @param fromNo starting number
 * @param toNo ending number
 * @param noToBeGenerated total no of number to be generated.*/
fun generateRandomDoubleNumberList(fromNo: Double = 0.0, toNo: Double = 1000.0, noToBeGenerated: Int = 20) =
    List(noToBeGenerated) { Random.nextDouble(fromNo, toNo) }