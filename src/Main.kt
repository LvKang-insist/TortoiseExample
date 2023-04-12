import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

/**
 * @name Main
 * @package
 * @author 345 QQ:1831712732
 * @time 2022/07/0 16:35
 * @description
 */

fun main() {

    var a = ActivityA()
    a.addListener(object : Listener {
        override fun a() {
            a = ActivityA()
        }

        override fun b() {}
        override fun c() {}
    })

    val s1 = object : Listener by noDpDelegate() {
        override fun a() {
        }
    }

    val c = object :Listener by a(){
        override fun a() {
            TODO("Not yet implemented")
        }
    }
}


class ActivityA {



    fun addListener(listener: Listener) {
        listener.a()
    }
}

class B() {
    var a = 4

}

inline fun <reified T : Any> a(): T {
    return T::class.java.newInstance()
}

data class T(val s:String)

interface Listener {
    fun a()
    fun b()
    fun c()
}


internal inline fun <reified T : Any> noDpDelegate(): T {
    val javaClass = T::class.java
    return Proxy.newProxyInstance(javaClass.classLoader, arrayOf(javaClass), NO_OP_HANDLER) as T
}


private val NO_OP_HANDLER = InvocationHandler { _, _, _ ->
    // no op
}