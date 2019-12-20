package beyondsoft.com.wanandroid.utils

import android.app.Activity
import java.util.*
import kotlin.collections.HashSet

class ActivityManagers{

    companion object {
        var stack = Stack<Activity>()

        fun push(activity: Activity) {
            stack.push(activity)
        }

        fun remove(activity: Activity) {
            if (!activity.isFinishing) {
                activity.finish()
            }
            stack.remove(activity)
        }
        fun test() {
            var str : Array<Int> = arrayOf(1,2,3)
            str[0] = 1
            var str1:IntArray = intArrayOf(1,2)
            var str2 = arrayOf("1")
            var str3 : CharArray
            var list : List<String> = listOf()
            var mutableList : HashSet<String> = hashSetOf()
            mutableList.add("1")
            var s : Sequence<String> = sequenceOf("1")
            val asSequence = list.asSequence()
            val iterator = asSequence.iterator()
        }

    }
}