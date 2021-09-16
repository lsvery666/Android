package com.example.retrofittest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {

    lateinit var appRetrofit: Retrofit
    lateinit var appService: AppService
    lateinit var baiduRetrofit: Retrofit
    lateinit var baiduService: BaiDuService

    companion object{
        const val TAG = "MainActivity"
    }

    fun parseJSONWithJSONObject(jsonData: String){
        try{
            val jsonArray = JSONArray(jsonData)
            for(i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                Log.d("JSON", "id:"+jsonObject.getInt("id"))
                Log.d("JSON", "name:"+jsonObject.getInt("name"))
            }

        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonArrayStr = "[{'id':'1', 'name':'Curry'}, {'id':'1', 'name':'Curry'}]"
        parseJSONWithJSONObject(jsonArrayStr)

        appRetrofit = Retrofit.Builder()
            .baseUrl("http://10.90.191.236//")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        appService = appRetrofit.create(AppService::class.java)

        baiduRetrofit = Retrofit.Builder()
            .baseUrl("https://www.baidu.com")
            .addConverterFactory(object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<out Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    return object : Converter<ResponseBody, String>{
                        override fun convert(value: ResponseBody): String? {
                            return value.string()
                        }
                    }
                }
            })
            .build()
        baiduService = baiduRetrofit.create(BaiDuService::class.java)


        getAppDataBtn.setOnClickListener {






        }

    }

    fun getBaidu(){
        baiduService.getData().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG, "success")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "fail")
            }

        })
    }

    fun getAppData(){
        appService.getAppData().enqueue(object : Callback<List<App>> {
            override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {
                val list = response.body()
                if (list != null) {
                    for (app in list) {
                        Log.d(TAG, "id is ${app.id}")
                        Log.d(TAG, "name is ${app.name}")
                        Log.d(TAG, "version is ${app.version}")
                    }
                }
            }

            override fun onFailure(call: Call<List<App>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun getSingleApp(){
        appService.getSingleApp().enqueue(object: Callback<App>{
            override fun onResponse(call: Call<App>, response: Response<App>) {
                val app = response.body()
                Log.d(TAG, "GET Single App Success")
                Log.d(TAG, "id is ${app?.id}")
                Log.d(TAG, "name is ${app?.name}")
                Log.d(TAG, "version is ${app?.version}")
            }

            override fun onFailure(call: Call<App>, t: Throwable) {
                Log.d(TAG, "GET Single App Failed")
            }

        })
    }


}