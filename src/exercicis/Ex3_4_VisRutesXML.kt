package exercicis

import javax.swing.*
import java.awt.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory


class Finestra : JFrame() {

    init {
        val doc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("Rutes.xml")
        val arrel = doc.documentElement
        // Creem la array del elements ruta
        val llistaRutes = arrel.getElementsByTagName("ruta")

        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Punts d'una ruta"
        setSize(400, 300)
        layout = BorderLayout()

        val panell1 = JPanel(FlowLayout())
        val panell2 = JPanel(BorderLayout())
        add(panell1,BorderLayout.NORTH)
        add(panell2,BorderLayout.CENTER)

        // Guardem els noms de les rutes en llistaNomRutes
        val llistaNomRutes = arrayListOf<String>()
        for (i in 0 until llistaRutes.length) {
            val ruta = llistaRutes.item(i) as Element
            // Guardem el nom del element en la array
            llistaNomRutes.add(ruta.getElementsByTagName("nom").item(0).textContent)
        }
        val combo = JComboBox(llistaNomRutes.toArray())
        panell1.add(combo)

        panell2.add(JLabel("Llista de punts de la ruta:"),BorderLayout.NORTH)
        val area = JTextArea()
        panell2.add(area)

        // Carreguem en area la primera ruta
        var ruta = llistaRutes.item(combo.selectedIndex) as Element
        var llistaPunts = ruta.getElementsByTagName("punt")
        for (i in 0 until llistaPunts.length) {
            val punt = llistaPunts.item(i) as Element
            area.text += "${punt.getElementsByTagName("nom").item(0).textContent} "
            area.text += "(${punt.getElementsByTagName("latitud").item(0).textContent}, "
            area.text += "${punt.getElementsByTagName("longitud").item(0).textContent})"
            area.text += "\n"
        }
        // Listener que cambia el area als punts de cada ruta
        combo.addActionListener {
            area.text = ""
            // Guardem la ruta elegida
            ruta = llistaRutes.item(combo.selectedIndex) as Element
            // Creem una array de punts d'on agafarem els punts
            llistaPunts = ruta.getElementsByTagName("punt")
            for (i in 0 until llistaPunts.length) {
                val punt = llistaPunts.item(i) as Element
                area.text += "${punt.getElementsByTagName("nom").item(0).textContent} "
                area.text += "(${punt.getElementsByTagName("latitud").item(0).textContent}, "
                area.text += "${punt.getElementsByTagName("longitud").item(0).textContent})"
                area.text += "\n"
            }
        }
    }
}

fun main() {
    EventQueue.invokeLater {
        Finestra().isVisible = true
    }
}