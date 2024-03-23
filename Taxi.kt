package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class Taxi : AppCompatActivity(), OnMapReadyCallback, OnMapClickListener {
    //Создаем переменную класса googleMap, не уверен
    private lateinit var mMap: GoogleMap
    // создаем два маркера
    private var marker1: Marker? = null
    private var marker2: Marker? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi)
    //че-то делаем чтобы карта работала
        var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    //находим на XML странице все элементы с которыми будем работать
        var call: Button = findViewById(R.id.Call)
        val forNumber: TextView = findViewById(R.id.forNumber)
        val forName: TextView = findViewById(R.id.forName)
        val forLastName: TextView = findViewById(R.id.forLastName)
        //достаем из этого intent имя, фамилию, номер
        val userName = intent.getSerializableExtra("userName") as String
        val userLastName = intent.getSerializableExtra("userLastName") as String
        val userNumber = intent.getSerializableExtra("userPhone") as String
        //Записываем имя фамилию и номер в TextView, чтобы было видно, что мы рег
        forNumber.text = userNumber
        forName.text = userName
        forLastName.text = userLastName


            //Добавляем кнопке вызвать обработчик нажатия
        call.setOnClickListener {
            // Если оба маркера не пустые, то продолжаем работать
            // Если это не проверять, и выполнить все это с пустыми маркерами, то proebaly
            if(marker1!=null && marker2!=null){
                // добавляем две локации класса Location и добавляем в них широту и долготу маркеров
                val  location1 = Location("")
                location1.latitude = marker1?.position!!.latitude
                location1.longitude = marker1?.position!!.longitude
                val  location2 = Location("")
                location2.latitude = marker2?.position!!.latitude
                location2.longitude = marker2?.position!!.longitude
                // считаем растояние между локациями(маркерами) с помощью встроенного distanceTo()
                val distance = location1.distanceTo(location2)
                //создаем переменную для запуска новой активити, загружаем в нее все что надо, и переходим в новую активити
                val intent = Intent(this, LastActivity::class.java)
                intent.putExtra("distance", distance.toString())
                intent.putExtra("userName", userName)
                intent.putExtra("userLastName", userLastName)
                startActivity(intent)
            } else{
                Toast.makeText(this, "Enter route!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        //хз
        mMap = googleMap
        // это нам вообще не нужно вроде
        var point = LatLng(53.91903565853607, 27.593493693192183)
        //добавляем обработчик событий на карту
        mMap.setOnMapClickListener(this)
        // добавляем на карту здания
        mMap.isBuildingsEnabled = true
        // добавляем на карту возможность смотреть всякую тему внутри зданий, типо в одном здании могут быт много магазинов
        mMap.isIndoorEnabled = true
        // при запуске карты камера сразу подстроится к нашей выбранной точке
        // но так как мы эту точку не используем, то камера никуда не подстроится
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point))



    }



// temp, чтобы знать какой маркер ставить, начальная точка(желтый) или конечная точка(красный)
    var temp = ""

    override fun onMapClick(loc: LatLng) {

//хз, вроде чтобы нажималось, или чтобы маркеры добавлялись
        mMap.setOnMarkerClickListener {
            true
        }
            // если маркара1 еще нет, то добавляем его
        if (marker1 == null) {
            //  добавляем желтый маркер
            marker1 = mMap.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(loc).title(""))
        } else{
            // если маркара2 еще нет, то добавляем его
            if (marker2 == null){
                //  добавляем красный маркер
                marker2 = mMap.addMarker(MarkerOptions().position(loc).title(""))
                // короче и так все понятно, маркеры будут поочередно переставляться
                temp = "marker2"
            } else{
                if (temp == "marker2"){
                    marker1?.remove()
                    marker1 = mMap.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(loc).title(""))
                    temp = "marker1"
                }else if(temp == "marker1"){
                    marker2?.remove()
                    marker2 = mMap.addMarker(MarkerOptions().position(loc).title(""))
                    temp = "marker2"
                }
            }

        }




    }
}