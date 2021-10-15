package exercicis

import com.squareup.moshi.Moshi
import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
fun main() {
    val fIn = ObjectInputStream(FileInputStream("Rutes.obj"))
    val arrayRutes : MutableList<Ruta> = arrayListOf<Ruta>()
    try {
        while (true) {
            val ruta = fIn.readObject() as Ruta
            arrayRutes.add(ruta)
        }
    } catch (eof: EOFException) {
        fIn.close()
    }
    val rutes = Rutes(arrayRutes)
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(Rutes::class.java)
    val json = adapter.toJson(rutes)

    File("Rutes.json").writeText(json)
}
