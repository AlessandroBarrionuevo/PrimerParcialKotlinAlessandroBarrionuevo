package Clases

import android.os.Parcelable
import java.io.Serializable

abstract class Helado: Serializable {
     abstract var gustos: ArrayList<String>
     abstract var medida: String
}