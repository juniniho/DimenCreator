package com.zhaiker.myapplication

class Test1 : Comm.Callback {
    override fun onRecv(data: Int) {
        Comm.removeCallback(this)
    }

    init {

        Comm.addCallback(this)
    }

}