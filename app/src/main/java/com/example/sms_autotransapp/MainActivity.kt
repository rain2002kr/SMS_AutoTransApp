package com.example.sms_autotransapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sms_autotransapp.Contact_Log.ContactLogActivity
import com.example.sms_autotransapp.Contact_item.ContactRegisterActivity
import com.example.sms_autotransapp.Info.AppInfoActivity
import com.example.sms_autotransapp.Send_SmS.SendSmSActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val MY_PERMISSIONS_REQUEST_RECEIVE_SMS :Int  = 1
    val MY_PERMISSIONS_REQUEST_SEND_SMS :Int  = 2
    private val multiplePermissionsCode = 100
    private val requiredPermissions = arrayOf(
        Manifest.permission.SEND_SMS,
        Manifest.permission.RECEIVE_SMS
        //Manifest.permission.READ_PHONE_STATE,
        //Manifest.permission.ACCESS_COARSE_LOCATION,
    )


    val list = listOf<MainViewModel>(
        MainViewModel(R.drawable.network, "전송 리스트","자동 문자 전송 정보를 저장하는 화면입니다."),
        MainViewModel(R.drawable.log, "전송 내역","자동 전송된 문자 내역을 확인하는 화면입니다."),
        MainViewModel(R.drawable.sms, "문자 보내기","문자를 전송 할수 있는 화면입니다."),
        MainViewModel(R.drawable.info, "정보","버전을 확인 할수 있습니다.")
    )
    val adapter = MainViewAdapter(this, R.layout.sub_main_view, list)
    val versionInfo = mapOf(
        "version" to "1.0.0",
        "update" to "2020.01.19",
        "comment" to "Update logView"
     )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()


        container_main.adapter = adapter
        adapter.setItemClickListener(object  : MainViewAdapter.ItemClickListener{
            val contexts = applicationContext

            override fun onClick(view: View, position: Int) {
                Log.d("SSS", "${position}번 리스트 선택")
                screenChagne(position)
            }
        })

        container_main.setHasFixedSize(true)
        container_main.layoutManager = GridLayoutManager(this,1, RecyclerView.VERTICAL,false)
    }
    private fun checkPermissions() {
        //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
        var rejectedPermissionList = ArrayList<String>()

        //필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
        for(permission in requiredPermissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면...
        if(rejectedPermissionList.isNotEmpty()){
            //권한 요청!
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(array), multiplePermissionsCode)
        }
    }
    //권한 요청 결과 함수
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            multiplePermissionsCode -> {
                if(grantResults.isNotEmpty()) {
                    for((i, permission) in permissions.withIndex()) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            //권한 획득 실패
                            Log.i("TAG", "The user has denied to $permission")
                            Log.i("TAG", "I can't work for you anymore then. ByeBye!")
                        }
                    }
                }
            }
        }
    }

fun screenChagne(pos:Int){

    when(pos){
        0 -> {
            val intent = Intent(this, ContactRegisterActivity::class.java)
            setintent(intent)
        }
        1 -> {
            val intent = Intent(this, ContactLogActivity::class.java)
            setintent(intent)
        }
        2 -> {
            val intent = Intent(this, SendSmSActivity::class.java)
            setintent(intent)
        }
        3 -> {
            val intent = Intent(this, AppInfoActivity::class.java)
            setintent(intent)
        }

    }

}
fun setintent(intent : Intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }



}
