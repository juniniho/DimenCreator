package com.zhaiker.dimencreator

import java.io.File

object DimenCreator {

    /**
     * 生成Dimens文件，文件位于项目的根目录
     */
    fun createDimens() {
        val fontMax = 100 //字体最大值
        val designMax = 500 //设计图最大尺寸
        val file = File("dimens.xml")
        file.writeText("<resources>\n")
        for (index in 0..fontMax) {
            file.appendText("    <dimen name=\"font_" + index + "sp\">" + index + "sp</dimen>\n")
        }
        file.appendText("\n")
        for (index in 0..designMax) {
            file.appendText("    <dimen name=\"size_" + index + "dp\">" + index + "mm</dimen>\n")
        }
        file.appendText("\n")
        for (index in 0..designMax) {
            (index + 0.5).let {
                file.appendText("    <dimen name=\"size_" + it + "dp\">" + it + "mm</dimen>\n")
            }
        }
        file.appendText("\n" + "</resources>")
    }
}