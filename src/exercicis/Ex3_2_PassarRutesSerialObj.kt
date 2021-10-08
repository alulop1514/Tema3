package exercicis

import java.io.DataInputStream
import java.io.FileInputStream
import java.io.ObjectOutputStream
import java.io.FileOutputStream

fun main() {
    val fIn = DataInputStream(FileInputStream("Rutes.dat"))
    val fOut = ObjectOutputStream(FileOutputStream("Rutes.obj"))

    while (fIn.available() > 0) {
        val nom = fIn.readUTF()
        val desnivell = fIn.readInt()
        val desnivellA = fIn.readInt()
        val nPunts = fIn.readInt()
        val llistaPunts : MutableList<PuntGeo> = arrayListOf()
        for (i in 1..nPunts) {
            llistaPunts.add(PuntGeo(fIn.readUTF(), Coordenades(fIn.readDouble(), fIn.readDouble())))
        }
        val ruta = Ruta(nom,desnivell,desnivellA,llistaPunts)
        fOut.writeObject(ruta)
    }
    println("Fitxer creat")
    fIn.close()
    fOut.close()
}

