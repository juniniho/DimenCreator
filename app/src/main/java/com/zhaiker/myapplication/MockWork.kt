package com.zhaiker.myapplication

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.concurrent.thread


object MockWork {

    var job : Job? = null

    fun longTimeMethod(result: (txt : String)->Unit) {
        thread {
            Thread.sleep(5000)
            result.invoke("hhh")
        }
    }

    suspend fun getResult() : String = suspendCancellableCoroutine{continuation->
        longTimeMethod {
            continuation.resume(it)
        }

    }

    fun startWork(){
        Log.d("chenyu","startWork...")
        job = CoroutineScope(Dispatchers.IO).launch {
            while (true){
//                longTimeMethod {
//                    Log.d("chenyu",it)
//                }

                val res = getResult()
                Log.d("chenyu",res)

                delay(2000)
            }
        }

    }

    fun stopWork(){
        job?.cancel()
        Log.d("chenyu","call cancel")
    }



}