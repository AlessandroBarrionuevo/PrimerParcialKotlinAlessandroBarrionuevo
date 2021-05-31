package com.istea.primerparcial_alessandrobarrionuevo

import Clases.*
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var eGustos: EditText
    lateinit var topping: CheckBox
    lateinit var spinnerToppings : Spinner
    lateinit var btnGustos: Button
    lateinit var botonHacerPedido: Button
    lateinit var ir: Button
    lateinit var spinnerMedidas: Spinner
    lateinit var spinnerCajeros: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eGustos = findViewById(R.id.e_IngresoGustos)
        btnGustos = findViewById(R.id.btn_IngresoGusto)
        topping = findViewById(R.id.cb_Topping)
        spinnerToppings = findViewById(R.id.s_Toppings)
        spinnerCajeros = findViewById(R.id.s_Cajeros)
        botonHacerPedido = findViewById(R.id.btn_HacerPedido)
        ir= findViewById(R.id.btn_IrA)
        spinnerMedidas = findViewById(R.id.spinner_Medidas)

        //variables globales
        var sabores = arrayListOf<String>()
        var cajero: String = " No entro "
        var pedidosClientes = arrayListOf<String>()

        //Contadores
        var contadorCono: Int = 0
        var contadorCuarto: Int = 0
        var contadorKilo: Int = 0
        var contadorCajero1: Int = 0
        var contadorCajero2: Int = 0
        var contadorCajero3: Int = 0

        //Variable para poder obtener la eleccion del spinner
        var eleccion: String = ""
        var eleccionTopping: String = ""

        //Lista que describe las medidas que tenemos disponibles
        var medidasDeHelados = arrayListOf<String>("Cono", "Cuarto","Kilo")
        var toppingsParaSpinner = arrayListOf<String>()
        var cajeros = arrayListOf<String>("Cajero1","Cajero2","Cajero3")

        //se crea el adaptador del spinner donde se le da el contexto a mostrar y la lista de datos
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, medidasDeHelados)
        val adapterCajeros = ArrayAdapter(this, android.R.layout.simple_spinner_item, cajeros)

        //incorporando adapters a los spinners
        spinnerMedidas.adapter = adapter
        spinnerCajeros.adapter = adapterCajeros

        spinnerMedidas.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                eleccion = medidasDeHelados[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                generarToastShowLong(this@MainActivity, "Error no se selecciono en el spinner")
            }
        }

        //Comienzo logica sobre los eventos
        btnGustos.setOnClickListener {
            if(eleccion.contains("Cono") && contadorCono <= 1){
                sabores.add(eGustos.text.toString())
                contadorCono++
                eGustos.setText(" ")
            }

            //cuarto
            if (eleccion.contains("Cuarto") && contadorCuarto <= 2){
                sabores.add(eGustos.text.toString())
                contadorCono++
                eGustos.setText("")
            }

            //kilo
            if (eleccion.contains("Kilo") && contadorKilo <= 3){
                sabores.add(eGustos.text.toString())
                contadorKilo++
                eGustos.setText("")
            }
        }

        //opcional de topping
        topping.setOnClickListener {
            toppingsParaSpinner = generadorDeArrayToppings(eleccion)
            val adapterSpinnerToppings = ArrayAdapter(this, android.R.layout.simple_spinner_item, toppingsParaSpinner)
            spinnerToppings.adapter = adapterSpinnerToppings
        }

        //seleccion de topping
        spinnerToppings.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                eleccionTopping = toppingsParaSpinner[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        //Eleccion de cajero
        spinnerCajeros.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                cajero = cajeros[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        //Al hacer un pedido se va a verificar la medida y se va a armar un pedido unido con las opciones que se fue seleccionando
        botonHacerPedido.setOnClickListener {
            if(eleccion == "Cuarto" ){
                var helado: CuartoHelado = CuartoHelado(sabores[0],sabores[1],sabores[2], eleccionTopping)
                var nombre: String = "Pedido: $eleccion sabores: ${helado.sabor1}, ${helado.sabor2}, ${helado.sabor3}  Topping: $eleccionTopping caja: $cajero "
                pedidosClientes.add(nombre)
                contadorCajero1++
                generarToastShowLong(this," pedido realizado con exito" )
            }

            if(eleccion == "Cono"){
                var helado:ConoHelado = ConoHelado(sabores[0], sabores[1])
                pedidosClientes.add("Pedido: ${helado.sabor1}, ${helado.sabor2}  Topping: $eleccionTopping  caja: $cajero")
                contadorCajero2++
                generarToastShowLong(this," pedido realizado con exito")
            }

            if(eleccion == "Kilo"){
                var helado:KiloHelado = KiloHelado(sabores[0],sabores[1],sabores[2],sabores[3], eleccionTopping )
                pedidosClientes.add("Pedido: ${helado.sabor1}, ${helado.sabor2}, ${helado.sabor3}, ${helado.sabor4}  Topping: $eleccionTopping  caja: $cajero")
                contadorCajero3++
                generarToastShowLong(this," pedido realizado con exito")
            }

            contadorCono = 0
            contadorCuarto = 0
            contadorKilo = 0

            sabores.clear()
        }

        //btn que nos sirve para ir a la actividad de empleados y ver los pedidos
        ir.setOnClickListener {
            val intent = Intent(this, EmpleadosActivity::class.java)
            intent.putExtra("ped",pedidosClientes)
            startActivity(intent)
        }
    }

    //funciones
    fun generarToastShowLong(context: Context, msj:String) {
        Toast.makeText(context, msj, Toast.LENGTH_SHORT).show()
    }


    fun generadorDeArrayToppings(medida:String): ArrayList<String>{
        var toppings = arrayListOf<String>("Crema de vainilla", "Chocolate fundido")
        if (medida.contains("Kilo")){
            toppings.add("Almendras")
            return toppings
        }else if(medida == null || medida == "Cono" ){
            toppings.clear()
            return toppings
        }else{
            return toppings
        }
    }
}
