package exercicis

import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Element
import java.io.FileWriter
import javax.xml.parsers.DocumentBuilderFactory

fun main(args: Array<String>) {
    val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("cotxes.xml")
    val arrelXML = doc.getDocumentElement()  // apuntarà a l'element arrel
    val llista = arrelXML.getElementsByTagName("vehiculo")
    val marcas = arrayListOf<String>()
    val matriculas = arrayListOf<String>()
    val motores = arrayListOf<String>()
    val colores = arrayListOf<String>()
    val modelos = arrayListOf<String>()
    val combustibles = arrayListOf<String>()
    val kilometros = arrayListOf<String>()
    val preciosIniciales = arrayListOf<String>()
    val preciosOferta = arrayListOf<String>()
    val arrayExtras = arrayListOf<String>()
    val arrayNumExtras = arrayListOf<Int>()
    val valorExtras = arrayListOf<String>()
    val fotosCotxes = arrayListOf<String>()
    val numFotos = arrayListOf<Int>()

    for (i in 0 until llista.getLength()) {
        val el = llista.item(i) as Element
        marcas.add(el.getElementsByTagName("marca").item(0).textContent)
        modelos.add(el.getElementsByTagName("modelo").item(0).textContent)
        colores.add(el.getElementsByTagName("modelo").item(0).attributes.item(0).textContent)
        matriculas.add(el.getElementsByTagName("matricula").item(0).textContent)
        motores.add(el.getElementsByTagName("motor").item(0).textContent)
        combustibles.add(el.getElementsByTagName("motor").item(0).getAttributes().item(0).textContent)
        kilometros.add(el.getElementsByTagName("kilometros").item(0).textContent)
        preciosIniciales.add(el.getElementsByTagName("precio_inicial").item(0).textContent)
        preciosOferta.add(el.getElementsByTagName("precio_oferta").item(0).textContent)
        val extras = el.getElementsByTagName("extra")
        arrayNumExtras.add(extras.length)
        for (x in 0 until extras.length) {
            val extra = extras.item(x) as Element
            valorExtras.add(extra.attributes.item(0).textContent)
            arrayExtras.add(extra.textContent)
        }
        val fotos = el.getElementsByTagName("foto")
        numFotos.add(fotos.length)
        for (x in 0 until fotos.length) {
            val foto = fotos.item(x) as Element
            fotosCotxes.add(foto.textContent)
        }
    }
    var posicionExtras = 0
    var posicionFotos = 0
    val arrelJson = JSONObject()
    val oferta = JSONObject()
    val vehiculos = JSONArray()
    oferta.put("vehiculos", vehiculos)
    arrelJson.put("oferta", oferta)
    for (i in 0 until marcas.size) {
        val vehiculo = JSONObject()
        vehiculo.put("marca", marcas[i])
        val modelo = JSONObject()
        modelo.put("color", colores[i])
        modelo.put("nombre_modelo", modelos[i])
        vehiculo.put("modelo",modelo)
        val motor = JSONObject()
        motor.put("combustible", combustibles[i])
        motor.put("nombre_motor", motores[i])
        vehiculo.put("motor",motor)
        vehiculo.put("matricula",matriculas[i])
        vehiculo.put("kilometros", kilometros[i])
        vehiculo.put("precio_inicial", preciosIniciales[i])
        vehiculo.put("precio_oferta", preciosOferta[i])
        val extras = JSONArray()
        var aux = 0
        for (x in 0 until arrayNumExtras[i]) {
            val extra = JSONObject()
            extra.put("valor", valorExtras[x + posicionExtras])
            extra.put("nombre_extra", arrayExtras[x + posicionExtras])
            extras.put(extra)
            aux = x
        }
        posicionExtras = aux
        aux = 0
        println(posicionExtras)
        vehiculo.put("extras",extras)
        val fotos = JSONArray()
        for (x in 0 until numFotos[i]) {
            fotos.put(fotosCotxes[x + posicionFotos])
            aux = x
        }
        posicionFotos = aux
        println(posicionFotos)
        vehiculo.put("foto",fotos)
        vehiculos.put(vehiculo)
    }

    val f = FileWriter("cotxes.json")
    f.write(arrelJson.toString(4))
    f.close()
}