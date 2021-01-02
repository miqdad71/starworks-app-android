package com.miqdad71.starworks.view.fragments.engineer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentPortfolioBinding
import com.miqdad71.starworks.view.adapter.ListPortfolioRecycleViewAdapter
import com.miqdad71.starworks.view.model.Databases
import com.miqdad71.starworks.view.model.Portfolio

class PortofolioEngineerFragment : Fragment(R.layout.fragment_portfolio) {

    private lateinit var rootView: View
    private lateinit var binding: FragmentPortfolioBinding
    private val listPortfolio = ArrayList<Portfolio>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//      binding fragments
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_portfolio, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val portfolio = Databases.porto

        for (i in 0 until portfolio.size) {
            val porto = Portfolio(
                portfolio[i]
            )
            listPortfolio.add(porto)
        }
        showRecycleList(view)
    }

    private fun showRecycleList(view: View) {
        binding.rvPortfolio.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            adapter = ListPortfolioRecycleViewAdapter(listPortfolio)
        }
    }

}