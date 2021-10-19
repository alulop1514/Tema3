package exercicis



import javax.swing.*
import java.awt.*
import org.json.JSONTokener
import org.json.JSONObject
import org.json.JSONArray
import java.net.URL
import java.io.InputStreamReader

class FinestraBicicas : JFrame() {
    var estacions = JSONArray()
    var listModel = DefaultListModel<String>()
    var list = JList(listModel)
    var area = JTextArea(5, 15)

    init {

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setTitle("JSON: Vista detallada BICICAS")
        setSize(800, 800)
        setLayout(BorderLayout())
        val panell1 = JPanel(FlowLayout())
        val panell2 = JPanel(GridLayout(1, 2))
        add(panell1, BorderLayout.NORTH)
        add(panell2, BorderLayout.CENTER)
        list.setForeground(Color.blue)
        var scroll1 = JScrollPane(list)
        var scroll2 = JScrollPane(area)
        panell2.add(scroll1)
        panell2.add(scroll2)

        panell1.add(JLabel("LListat actual BICICAS"))

        agafarBicicas()

        mostrarEstacions()

        list.addListSelectionListener() {
            if (list.getSelectedIndex() >= 0)
                visualitzaEstacio(list.getSelectedIndex())
        }
    }

    fun agafarBicicas() {
        // Instruccions per a llegir de la pàgina de Bicicas i col·locar en arrel
        val bicicas = URL("http://gestiona.bicicas.es/apps/apps.php");
        val arrel = (JSONTokener(bicicas.openConnection().getInputStream()).nextValue() as JSONArray).get(0) as JSONObject
        estacions = arrel.getJSONArray("ocupacion")
    }

    fun mostrarEstacions() {
        // Instruccions per a introduir en el JList les estacions
        // La manera d'anar introduint informació en el JList és a través del DefaultListModel:
        // listModel.addElement("Linia que es vol introduir ")
        for (i in 0 until estacions.length()) {
            val estacion = estacions.get(i) as JSONObject
            listModel.addElement("${estacion.get("id")}.- ${estacion.get("punto")} (${estacion.get("ocupados")}/${estacion.get("puestos")})")
        }
    }

    fun visualitzaEstacio(numEst: Int) {
        val estacion = estacions.get(numEst) as JSONObject
        area.text = ""
        area.text += "           ${estacion.get("id")}.- ${estacion.get("punto")}\n\n" +
                "Numero estacio: ${estacion.get("id")}\n" +
                "Nom estacio: ${estacion.get("punto")}\n" +
                "Posicions: ${estacion.get("puestos")}\n" +
                "Posicions ocupades: ${estacion.get("ocupados")}\n" +
                "Latitud:  ${estacion.get("latitud")}\n" +
                "Longitud: ${estacion.get("longitud")}\n" +
                "Percentatge alta ocupacio: ${estacion.get("porcentajeAltaOcupacion")}\n" +
                "Percentatge baixa ocupacio: ${estacion.get("porcentajeBajaOcupacion")}"
    }
}

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        FinestraBicicas().isVisible = true
    }
}

