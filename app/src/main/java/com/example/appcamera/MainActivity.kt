package com.example.appcamera

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Arrays

class MainActivity : AppCompatActivity() {
    private var check: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initPermissions()

        var button = findViewById<FloatingActionButton>(R.id.btnTakePhoto)
        button.setOnClickListener {
                if (check){
                    val intent = Intent("android.media.action.IMAGE_CAPTURE")
                    startActivityForResult(intent,0)
                }else{
                    Toast.makeText(this, "Não tem permissão para usar a câmera", Toast.LENGTH_SHORT).show()
                }
            }

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null && requestCode == 0 && resultCode == Activity.RESULT_OK){
            val bundle: Bundle? = data.extras
                if(bundle != null){
                   var bitmap = bundle.get("data") as Bitmap
                    var imgPhoto = findViewById<ImageView>(R.id.imgPhoto)
                    imgPhoto.setImageBitmap(bitmap)
                }
        }
    }


    //permissions
    private fun initPermissions() {
        if(!getPermission()){
            setPermission()
        }else{
            check = true
        }
    }

    private fun getPermission(): Boolean{
        return  (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun setPermission() {
        val permissionList = listOf<String>(android.Manifest.permission.CAMERA)

        ActivityCompat.requestPermissions(this, permissionList.toTypedArray(),1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1 ->  {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Log.i("Permission:*","Permission has granted by user")
                    Toast.makeText(this, "Não tem permissão para usar a câmera", Toast.LENGTH_SHORT).show()

                }else {
                    Log.i("Permission:*","Permission has granted by user")
                    check = false
                }
            }
        }
    }
}
