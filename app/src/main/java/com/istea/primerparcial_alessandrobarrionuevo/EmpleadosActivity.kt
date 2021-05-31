package com.istea.primerparcial_alessandrobarrionuevo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import java.util.Random

class EmpleadosActivity : AppCompatActivity() {
    lateinit var lbl : TextView
    lateinit var btn: Button
    lateinit var spinnerCajeros: Spinner
    lateinit var pedidosLayout: LinearLayout
    lateinit var btnRepartidores: Button

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleados)

        //Mapeo
        lbl = findViewById(R.id.lbl)
        btn = findViewById(R.id.btn)
        spinnerCajeros = findViewById(R.id.s_MostrarCajeros)
        pedidosLayout = findViewById(R.id.llayout)
        btnRepartidores = findViewById(R.id.btn_Repartidores)

        //variables, listas, adapters
        var arrayParaRepartidor = arrayListOf<String>()
        var caja: String = ""
        var pedidos: ArrayList<String> = intent.getStringArrayListExtra("ped") as ArrayList<String>
        var arrayDeCajas = arrayListOf<String>("Cajero1","Cajero2","Cajero3")
        var adapterCajeros = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayDeCajas)
        spinnerCajeros.adapter = adapterCajeros
        val view = LayoutInflater.from(this).inflate(R.layout.pedidoscajeroslayout,null)
        val pedidosRealizados: TextView = view.findViewById(R.id.lbl_PedidosRealizados)

        spinnerCajeros.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                caja = arrayDeCajas[position]
                Toast.makeText(this@EmpleadosActivity,caja,Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        var contadorRepartidor1 = 0
        var contadorRepartidor2 = 0
        var contadorRepartidor3 = 0
        var contadorRepartidor4 = 0
        var mejorRepartidor = ""

        btn.setOnClickListener {
            pedidosRealizados.setText("")
            if (pedidos != null){
                for (item in pedidos) {
                    if (item.contains(caja)){
                        var random = Random()
                        var numero = random.nextInt(4)+1
                        //Toast.makeText(this, numero.toString(),Toast.LENGTH_SHORT).show()
                        pedidosRealizados.text = pedidosRealizados.text.toString() + item +"\n"
                        arrayParaRepartidor.add(item + numero.toString())
                    }
                }
            }
            if (arrayParaRepartidor != null){
                for (item in arrayParaRepartidor){
                    if (item.contains("1")) {
                        contadorRepartidor1++
                    }
                    if (item.contains("2")){
                        contadorRepartidor2++
                    }
                    if (item.contains("3")){
                        contadorRepartidor3++
                    }
                    if (item.contains("4")){
                        contadorRepartidor4++
                    }
                }
            }
            if (contadorRepartidor1 > contadorRepartidor2 && contadorRepartidor1 > contadorRepartidor3 && contadorRepartidor1> contadorRepartidor4){
                mejorRepartidor = "Repartidor 1"
            }else if (contadorRepartidor2 > contadorRepartidor3 && contadorRepartidor2> contadorRepartidor4){
                mejorRepartidor = "Repartidor 2"
            }else if (contadorRepartidor3 > contadorRepartidor4){
                mejorRepartidor = "Repartidor 3"
            }else{
                mejorRepartidor = "Repartidor 4"
            }

            pedidosLayout.removeAllViews()
            pedidosLayout.addView(view)
        }



        btnRepartidores.setOnClickListener {
            pedidosRealizados.setText("")

            pedidosRealizados.text = "Repartidor 1: total pedidos $contadorRepartidor1 \n" + "Repartidor 2: total pedidos $contadorRepartidor2 \n" +
                                     "Repartidor 2: total pedidos $contadorRepartidor3 \n"+ "Repartidor 4: total pedidos $contadorRepartidor4 \n" +
                                     "Mejor repartidor: $mejorRepartidor"
            pedidosLayout.removeAllViews()
            pedidosLayout.addView(view)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menudemiheladeria, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menuPedir->{
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.menuCYR->{
                Toast.makeText(this,"Estas viendo los pedidos ",Toast.LENGTH_SHORT).show()
                true
            }
            else->{
                true
            }
        }
    }
}