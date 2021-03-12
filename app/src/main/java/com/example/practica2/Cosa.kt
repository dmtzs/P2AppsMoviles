package com.example.practica2

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/*data class Cosa(var nombreDeCosa: String="",
                var valorEnPesos:Int = 0,
                val fechaDeCreacion: Date = Date(),
                val numeroDeSerie: UUID= UUID.randomUUID())*/
class Cosa  (): Parcelable{
    var nombreDeCosa: String=""
    var valorEnPesos:Int = 0
    var fechaDeCreacion: Date = Date()
    var numeroDeSerie: UUID= UUID.randomUUID()
    constructor(parcel: Parcel) : this() {
        nombreDeCosa = parcel.readString().toString()
        valorEnPesos = parcel.readInt()
        fechaDeCreacion = parcel.readSerializable() as Date

        numeroDeSerie = parcel.readSerializable() as UUID
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombreDeCosa)
        parcel.writeInt(valorEnPesos)
        parcel.writeSerializable(fechaDeCreacion)
        parcel.writeSerializable(numeroDeSerie)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cosa> {
        override fun createFromParcel(parcel: Parcel): Cosa {
            return Cosa(parcel)
        }

        override fun newArray(size: Int): Array<Cosa?> {
            return arrayOfNulls(size)
        }
    }

}