package projeto.util

import java.io.File

class GerenciaDir {
    private val TMP = "tmp/"
    private val LOG = "log/"

    fun iniciar(a: Int, b: Int, c: Int, d: Int) {
        if (a == 1) criar(a, b)
        if (c == 1) limpar(c, d)
    }

    private fun criar(a: Int, b: Int) {
        if (a == 1) File(TMP).mkdirs()
        if (b == 1) File(LOG).mkdirs()

    }

    private fun limpar(a: Int, b: Int) {
        if (a == 1) {
            File(TMP).list().forEach {
                File(TMP + it).delete()
            }
        }
        if (b == 1) {
            File(LOG).list().forEach{
                File(LOG + it).delete()
            }
        }
    }
}



