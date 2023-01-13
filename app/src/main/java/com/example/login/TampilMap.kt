package com.example.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.location.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory

import com.google.android.libraries.places.api.Places

class TampilMap : AppCompatActivity(), OnMapReadyCallback {


    private val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var mMap: GoogleMap



    var currentLocation: LatLng = LatLng( -6.966667, 110.416664)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tampil_map)

        // Fetching API_KEY
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["com.google.android.geo.API_KEY"]
        val apiKey = value.toString()

        // Initializing lokasi API
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        // Initializing Map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initializing lokasi
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Button mencari lokasi
        val btn = findViewById<Button>(R.id.lokasiterkini)
        btn.setOnClickListener {
            getLastLocation()
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        getLastLocation()


    }

    // Mencari Lokasi Saat Ini
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        mMap.clear()
                        mMap.addMarker(MarkerOptions().position(currentLocation))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))




                        val restoloc = LatLng(-7.054743, 110.434028)
                        mMap.addMarker(MarkerOptions().position(restoloc).title("Burjo Gondrong")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico1)))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(restoloc))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))

                        var restoran2: LatLng = LatLng( -7.040348820537521, 110.50336794146031)
                        mMap.addMarker(MarkerOptions().position(restoran2).title("Pelems Padang")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico1)))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(restoran2))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))

                        var restoran3: LatLng = LatLng( -7.05232900252665, 110.43305014828)
                        mMap.addMarker(MarkerOptions().position(restoran3).title("Angkringan Mas Gondrong")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico1)))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(restoran3))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))

                        var restoran4: LatLng = LatLng( -7.064369953552218, 110.43418840827566)
                        mMap.addMarker(MarkerOptions().position(restoran4).title("Warteg Baskoro Jaya")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico1)))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(restoran4))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))

                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    // Mendapatkan Lokasi Terkini
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()!!
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            currentLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)
        }
    }

    // Cek GPS On atau Off
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // Cek Perizinan Lokasi GPS
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    // Request menyalakan GPS
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    // Akses Lokasi Disetujui
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }
}