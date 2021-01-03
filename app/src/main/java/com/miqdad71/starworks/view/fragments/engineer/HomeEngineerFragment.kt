package com.miqdad71.starworks.view.fragments.engineer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentHomeEngineerBinding
//import com.miqdad71.starworks.view.detail_profile.ProfileDetailActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.*


class HomeEngineerFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var binding: FragmentHomeEngineerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

//      binding fragments
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_engineer, container, false)
        return binding.root

    }


}
//class HomeEngineerFragment : AppCompatActivity() {
//
//    private lateinit var rootView: View
//    private lateinit var binding: FragmentHomeBinding
//    private lateinit var sharedPref: SharedPreference
//    private lateinit var userDetail: HashMap<String, String>
//    private lateinit var coroutineScope: CoroutineScope
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        binding = DataBindingUtil.setContentView(this, R.layout.fragment_home)
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        sharedPref = SharedPreference(view.context)
//        userDetail = sharedPref.getAccountUser()
//        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
//
//        if (sharedPref.getLevelUser() == 0) {
//            binding.title = "Login as Engineer"
//        } else {
//            binding.title = "Login as Company"
//        }
//
//        binding.accountModel = AccountModel(ac_name = "Hai, ${userDetail[NAME]}")
//
//        setupWebDevRecyclerView()
//        getAllProject(view)
//
//    }
//    private fun setupWebDevRecyclerView() {
//        binding.rvWeb.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
//
//        val adapter = HomeEngineerAdapter()
//        binding.rvWeb.adapter = adapter
//
//        adapter.setOnItemClickCallback(object: HomeEngineerAdapter.OnItemClickCallback {
//            override fun onItemClick(data: EngineerModel) {
//                intents<ProfileDetailActivity>(activity)
//            }
//        })
//    }
//    private fun getAllProject(view: View) {
//        val service = ApiClient.getApiClient(view.context).create(EngineerAPI::class.java)
//
//        coroutineScope.launch {
//            val response = withContext(Dispatchers.IO) {
//                try {
//                    service.getAllEngineer()
//                } catch (e: Throwable) {
//                    e.printStackTrace()
//                }
//            }
//
//            if (response is EngineerResponse) {
//                val list = response.data.map {
//                    EngineerModel(it.enId, it.acId)
//                }
//
//                (binding.rvWeb.adapter as HomeEngineerAdapter).addList(list)
//            }
//        }
//    }
//}