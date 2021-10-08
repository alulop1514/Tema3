package exercicis


import java.io.ObjectInputStream
import java.io.FileInputStream
import javax.xml.parsers.DocumentBuilderFactory
import java.io.EOFException
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun main() {
    val f = ObjectInputStream(FileInputStream ("Rutes.obj"))
    val doc = DocumentBuilderFactory.newInstance ().newDocumentBuilder().newDocument()
    val arrel = doc.createElement ("rutes")
    doc.appendChild(arrel)

    try {
        while (true) {
            val e = f.readObject() as Ruta
            val ruta = doc.createElement("ruta")

            val nom = doc.createElement("nom")
            nom.textContent = e.nom
            ruta.appendChild(nom)

            val desnivell = doc.createElement("desnivell")
            desnivell.textContent = e.desnivell.toString()
            ruta.appendChild(desnivell)

            val desnivellA = doc.createElement("desnivellAcumulat")
            desnivellA.textContent = e.desnivellAcumulat.toString()
            ruta.appendChild(desnivellA)

            val punts = doc.createElement("punts")
            for (i in e.llistaDePunts) {
                val punt = doc.createElement("punt")
                punt.setAttribute("num", (e.llistaDePunts.indexOf(i) + 1).toString())

                val nomPunt = doc.createElement("nom")
                nomPunt.appendChild(doc.createTextNode(i.nom))

                val latitud = doc.createElement("latitud")
                latitud.textContent = e.getPuntLatitud(e.llistaDePunts.indexOf(i)).toString()

                val longitud = doc.createElement("longitud")
                longitud.textContent = e.getPuntLongitud(e.llistaDePunts.indexOf(i)).toString()

                punt.appendChild(nomPunt)
                punt.appendChild(latitud)
                punt.appendChild(longitud)
                punts.appendChild(punt)
            }
            ruta.appendChild(punts)
            arrel.appendChild(ruta)
        }
    } catch (ex: EOFException) {
        f.close()
    }
    val transformacio = TransformerFactory.newInstance().newTransformer()
    transformacio.setOutputProperty(OutputKeys.INDENT, "yes")
    transformacio.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
    transformacio.transform(DOMSource(doc), StreamResult("Rutes.xml"))
    println("Fitxer creat")
}