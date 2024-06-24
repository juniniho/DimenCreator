package com.zhaiker.myapplication

object Comm {


    private val list = mutableListOf<Callback>()

    fun addCallback(callback : Callback){
        list.add(callback)


    }

    fun removeCallback(callback: Callback){
        val index = list.indexOf(callback)
        if(index >= 0){
            list.removeAt(index)
        }


    }

    fun start(){
        Thread{
            while (true){
                for(i in 1 until list.size){
                    list[i].onRecv(2)
                }

                Thread.sleep(50)
            }
        }.start()
    }


    interface Callback{
        fun onRecv(data : Int)
    }
}