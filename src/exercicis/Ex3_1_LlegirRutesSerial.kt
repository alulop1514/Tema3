package exercicis


import java.io.DataInputStream
import java.io.FileInputStream

fun main() {
    val f = DataInputStream(FileInputStream("Rutes.dat"))

    while (f.available() > 0) {
        println("Ruta: " + f.readUTF())
        println("Desnivell: " + f.readInt())
        println("Desnivell acumulat: " + f.readInt())
        val nPunts = f.readInt()
        println("Te $nPunts punts")
        for (i in 1..nPunts) {
            println("Punt $i: ${f.readUTF()} (${f.readDouble()},${f.readDouble()})")
        }
        println()
    }
    f.close()
}