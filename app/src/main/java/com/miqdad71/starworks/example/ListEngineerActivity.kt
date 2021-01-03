package com.miqdad71.starworks.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityListEngineerBinding
import com.miqdad71.starworks.data.remote.ApiClient
import kotlinx.coroutines.*

class ListEngineerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListEngineerBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ListEngineerApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_engineer)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(applicationContext)?.create(ListEngineerApiService::class.java)

        binding.recyclerView.adapter = ListEngineerAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        getAllEngineer()
    }

    private fun getAllEngineer() {
        coroutineScope.launch {
            Log.d("android2", "Start: ${Thread.currentThread().name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("android2", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getAllEngineer()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ListEngineerResponse) {
                Log.d("android2", result.toString())
                val list = result.data?.map {
                    ListEngineerModel(
                        it.enId, it.acId, it.acName, it.enJobTitle,
                        it.enJobType, it.enDomicile, it.enDescription, it.enProfile
                    )
                }

                (binding.recyclerView.adapter as ListEngineerAdapter).addList(list)
            }

        }

    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}
