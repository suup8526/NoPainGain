package com.nopaingain.bouldereatout.ui.dashboard

import android.content.SharedPreferences
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.network.model.restaurant.SimpleRestaurantModel
import com.nopaingain.bouldereatout.ui.base.BaseFragment
import com.nopaingain.bouldereatout.ui.base.PaginationScrollListener
import com.nopaingain.bouldereatout.viewmodels.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_main_listing.*

class MainListingFragment : BaseFragment(), RestaurantAdapter.OnRestaurantClickListener {

    private lateinit var restaurantViewModel: RestaurantViewModel
    private lateinit var prefs: SharedPreferences
    private var restaurantAdapter: RestaurantAdapter? = null
    private var restaurantList: ArrayList<SimpleRestaurantModel> = ArrayList()

    private var isLoading = false
    private var isFirst = true
    private var showNext = false

    override fun getLayoutID(): Int = R.layout.fragment_main_listing

    override fun init() {
        restaurantViewModel = ViewModelProvider(requireActivity()).get(RestaurantViewModel::class.java)
        prefs = sessionManager.customPrefs()
        initObservers()
    }

    private fun initObservers() {
        restaurantViewModel.obProcessing.observe(this, Observer {
            if (it == true){
                displayProgress()
            } else {
                hideProgress()
            }
        })
    }

    override fun setupUI() {
        initSV()
        restaurantList = restaurantViewModel.getRestaurantListing("", 10)
        initRV()
//        restaurantAdapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
    }

    private fun initSV() {
        svRestaurant?.queryHint = getString(R.string.search_hint)
        svRestaurant?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //Call api
                return false
            }

        })
    }

    private fun initRV() {
        restaurantAdapter = RestaurantAdapter(restaurantList, this)
        rvRestaurant.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = restaurantAdapter
        }

        rvRestaurant?.addOnScrollListener(object :
            PaginationScrollListener(rvRestaurant.layoutManager as LinearLayoutManager) {
            override fun showNext(): Boolean {
                return showNext
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                restaurantAdapter?.addLoadingFooter()
//                if (showNext())
//                    getRestaurant(rvRestaurant.layoutManager?.itemCount.toString())
            }
        })
    }

    private fun getRestaurant(offset: String) {
        isFirst = false
        isLoading = false
        restaurantAdapter?.removeLoadingFooter()
        restaurantAdapter?.notifyDataSetChanged()
    }

    override fun onRestaurantClick(position: Int) {
        val bundle = bundleOf("id" to restaurantList[position].id)
        findNavController().navigate(R.id.action_mainListingFragment_to_restaurantDetailFragment, bundle)
    }

    override fun onClick(view: View) {}

    override fun isOnBackPressed(): Boolean = true

    override fun showActionBar(): Boolean = true

    override fun showActionBarBackBtn(): Boolean = false
}