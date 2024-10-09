package no.usn.kvisli.listedemocompose.model

import android.content.res.Resources
import no.usn.kvisli.listedemocompose.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

data class Kommune(
    val kommuneNummer: String,
    val kommuneNavn: String,
    val admSenter: String,
    val fylke: String,
    val folkeTall: Int,
    val areal: Double,
    val kommuneVaapen: String
) {
    // (Sekundær) konstruktør som bygger et Kommune-objekt basert på et JSONObject
    constructor(jsonKommune: JSONObject) :
        this(
            kommuneNummer=jsonKommune.optString("Kommunenr"),
            kommuneNavn=jsonKommune.optString("Kommunenavn"),
            admSenter=jsonKommune.optString("Adm. senter"),
            fylke=jsonKommune.optString("Fylke"),
            folkeTall=jsonKommune.optInt("Folketall"),
            areal=jsonKommune.optDouble("Areal"),
            kommuneVaapen=jsonKommune.optString("Kommunevaapen")
        )

    companion object {
        @JvmStatic
        @Throws(JSONException::class, NullPointerException::class)
        fun lagKommuneListe(res: Resources): ArrayList<Kommune> {
            // Bygg en ArrayList av Kommune-objekter
            val kommuneListe = ArrayList<Kommune>()
            try {
                val jsonData: String = lesKommuneDataFraJSONFil(res)
                val jsonAlleKomm = JSONObject(jsonData)
                val jsonKommuneTabell: JSONArray = jsonAlleKomm.optJSONArray("kommuner")

                for (i in 0 until jsonKommuneTabell.length())
                    kommuneListe.add(Kommune(jsonKommuneTabell[i] as JSONObject))

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return kommuneListe
        } // End of lagKommuneListe

        @JvmStatic
        @Throws(IOException::class, NullPointerException::class)
        private fun lesKommuneDataFraJSONFil(res:Resources): String {
            val ressursfil = R.raw.kommunedata
            var enLinje: String?
            var heleFilen = StringBuilder()

            try {
                val inputStream =  res.openRawResource(ressursfil)
                val reader = BufferedReader(InputStreamReader(inputStream))

                while (reader.readLine().also { enLinje = it } != null)
                    heleFilen = heleFilen.append(enLinje)

            } catch (e: IOException) {
                e.printStackTrace()
            }
            return heleFilen.toString()
        }  // End of lesKommuneDataFraFil

    } // End of companion object

}
