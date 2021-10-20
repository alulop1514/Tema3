package exercicis



import javax.swing.*
import java.awt.*
import com.squareup.moshi.Moshi
import java.io.File

class FinestraJSON : JFrame() {

    init {
        val llistaRutes: ArrayList<Ruta> = arrayListOf<Ruta>()
        val json = File("Rutes.json").readText()

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(Rutes::class.java)
        val rutes = adapter.fromJson(json)
        val lRutes = rutes!!.rutes
        for (i in lRutes) {
            llistaRutes.add(i)
        }
        defaultCloseOperation = EXIT_ON_CLOSE
        setTitle("JSON: Punts d'una ruta")
        setSize(400, 300)
        setLayout(BorderLayout())

        val panell1 = JPanel(FlowLayout())
        val panell2 = JPanel(BorderLayout())
        add(panell1, BorderLayout.NORTH)
        add(panell2, BorderLayout.CENTER)

        val nomsLlistaRutes = arrayListOf<String>()
        for (i in llistaRutes) {
            nomsLlistaRutes.add(i.nom)
        }
        val combo = JComboBox(nomsLlistaRutes.toArray())
        panell1.add(combo)

        panell2.add(JLabel("Llista de punts de la ruta:"), BorderLayout.NORTH)
        val area = JTextArea()
        panell2.add(area)


        combo.addActionListener {
            area.text = ""
            val llistaPunts = llistaRutes[combo.selectedIndex].llistaDePunts
            for (i in llistaPunts) {
                area.text += "${i.nom} "
                area.text += "(${i.coord.latitud}, "
                area.text += "${i.coord.longitud})"
                area.text += "\n"
            }
        }
        combo.selectedIndex = 0
    }
}
fun main() {
    EventQueue.invokeLater {
        FinestraJSON().isVisible = true
    }
}

