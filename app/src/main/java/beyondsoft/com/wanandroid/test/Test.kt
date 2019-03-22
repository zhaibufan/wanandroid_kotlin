package beyondsoft.com.wanandroid.test

import beyondsoft.com.wanandroid.test.Test.lengthOfLongestSubstring
import java.util.ArrayList

class Solution {

    fun main(args : Array<String>) {


    }

    fun lengthOfLongestSubstring(str: String): Int {
        val list = ArrayList<String>()
        for (i in 0 until str.length) {
            val sb = StringBuilder()
            sb.append(str[i])
            for (j in i + 1 until str.length) {
                val c = str[j].toString()
                if (sb.toString().contains(c)) {
                    break
                }
                sb.append(c)
            }
            list.add(sb.toString())
        }
        var maxLength = 0
        for (result in list) {
            val length = result.length
            if (length > maxLength) {
                maxLength = length
            }
        }
        return maxLength
    }

}