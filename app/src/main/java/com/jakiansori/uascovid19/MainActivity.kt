package com.jakiansori.uascovid19

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jakiansori.uascovid19.retrofit.ApiService
import com.lazday.covid_19.MainModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    private fun getData() {
        showLoading(true)
        ApiService.endpoint.getData()
                .enqueue(object : Callback<List<MainModel>> {
                    override fun onFailure(call: Call<List<MainModel>>, t: Throwable) {
                        showLoading(false)
                    }

                    override fun onResponse(call: Call<List<MainModel>>, response: Response<List<MainModel>>) {
                        showLoading(false)
                        if (response.isSuccessful) {
                            val mainModel: List<MainModel> = response.body()!!
                            setResponse(mainModel)
                        }
                    }

                })
    }

    private fun setResponse(mainModel: List<MainModel>) {
        val response = mainModel[0]
        textViewResult.setText(
                "Positif: ${response.positif} \nPositif: ${response.sembuh} \nMeninggal: ${response.meninggal}"
        )
    }

    private fun showLoading(loading: Boolean) {
        when (loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

}