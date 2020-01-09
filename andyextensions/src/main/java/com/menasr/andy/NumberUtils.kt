@file:Suppress("unused")

package com.menasr.andy

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
