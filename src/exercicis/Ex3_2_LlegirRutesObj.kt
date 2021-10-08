package exercicis

import java.io.EOFException
import java.io.FileInputStream
import java.io.ObjectInputStream

fun main() {
    val fIn = ObjectInputStream(FileInputStream("Rutes.obj"))

    try {
        while (true) {
            val ruta = fIn.readObject() as Ruta
            ruta.mostrarRuta()
            println()
        }
    } catch (eof: EOFException) {
        fIn.close()
    }
}