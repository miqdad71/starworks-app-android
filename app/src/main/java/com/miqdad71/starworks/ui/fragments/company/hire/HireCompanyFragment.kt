package com.miqdad71.starworks.ui.fragments.company.hire

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentHireCompanyBinding
import com.miqdad71.starworks.ui.base.BaseFragmentCoroutine
import com.miqdad71.starworks.ui.fragments.company.hire.approve.ApproveFragment
import com.miqdad71.starworks.ui.fragments.company.hire.reject.RejectFragment
import com.miqdad71.starworks.ui.fragments.company.hire.wait.WaitFragment
import com.miqdad71.starworks.util.ViewPagerAdapter

class HireCompanyFragment : BaseFragmentCoroutine<FragmentHireCompanyBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_hire_company
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarActionBar()
        setContentViewHiring()
    }

    private fun setToolbarActionBar() {
        val tb = (activity as AppCompatActivity)

        tb.setSupportActionBar(binding.toolbar)
        tb.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        tb.supportActionBar?.title = "Hiring Project"
    }

    private fun setContentViewHiring() {
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        val adapter = ViewPagerAdapter(childFragmentManager)

        adapter.addFrag(WaitFragment(), "Wait")
        adapter.addFrag(ApproveFragment(), "Approve")
        adapter.addFrag(RejectFragment(), "Reject")
        binding.viewPager.adapter = adapter
    }
}

/*
class HireCompanyFragment : Fragment() {

    private lateinit var binding: FragmentHireCompanyBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var sharedPref: SharedPreference
    private lateinit var userDetail: HashMap<String, String>
    private lateinit var service: HireAPI
    private var cnId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?  ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hire_company, container, false)
        super.onCreateView(inflater, container, savedInstanceState)

        sharedPref = SharedPreference(requireActivity())
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(requireActivity()).create(HireAPI::class.java)

        userDetail = sharedPref.getAccountUser()

        setToolbarActionBar()

        binding.rvHireCompany.layoutManager = LinearLayoutManager(requireActivity().applicationContext, RecyclerView.VERTICAL, false)
        val adapter = CompanyHireAdapter()
        binding.rvHireCompany.adapter = adapter

        getHireCnId()
        return binding.root
    }

    private fun getHireCnId() {

        coroutineScope.launch {
            try {
                val resultData = service.getAllHireCompany(sharedPref.getIdCompany(),"wait")
                val dataFromResult = resultData.data

                val list = dataFromResult.map {
                    HireModel(
                        hrId = it.hrId,
                        enId = it.enId,
                        pjId = it.pjId,
                        acName = it.acName,
                        hrPrice = it.hrPrice,
                        hrMessage = it.hrMessage,
                        hrStatus = it.hrStatus,
                        hrDateConfirm = it.hrDateConfirm,
                        pjProjectName = it.pjProjectName,
                        pjDescription = it.pjDescription,
                        pjDeadline = it.pjDeadline,
                        cnCompany = it.cnCompany,
                        cnField = it.cnField,
                        cnCity = it.cnCity,
                        cnProfile = it.cnProfile
                    )
                }

                (binding.rvHireCompany.adapter as CompanyHireAdapter).addList(list)

            } catch (e: Throwable) {
                Log.d("message", e.toString())
            }
        }
    }

    private fun setToolbarActionBar() {
        val tb = (activity as AppCompatActivity)

        tb.setSupportActionBar(binding.toolbar)
        tb.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        tb.supportActionBar?.title = "Hiring Project"
    }

}*/
