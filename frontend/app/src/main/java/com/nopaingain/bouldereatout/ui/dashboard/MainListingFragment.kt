package com.nopaingain.bouldereatout.ui.dashboard

import android.content.SharedPreferences
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.network.model.restaurant.SimpleRestaurantModel
import com.nopaingain.bouldereatout.ui.base.BaseFragment
import com.nopaingain.bouldereatout.ui.base.PaginationScrollListener
import kotlinx.android.synthetic.main.fragment_main_listing.*

class MainListingFragment : BaseFragment(), RestaurantAdapter.OnRestaurantClickListener {

    private lateinit var prefs: SharedPreferences
    private var restaurantAdapter: RestaurantAdapter? = null
    private var restaurantList: ArrayList<SimpleRestaurantModel> = ArrayList()

    private var isLoading = false
    private var isFirst = true
    private var showNext = false

    override fun getLayoutID(): Int = R.layout.fragment_main_listing

    override fun init() {

    }

    override fun setupUI() {
        initSV()
        initRV()
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
                if (showNext())
                    getRestaurant(rvRestaurant.layoutManager?.itemCount.toString())
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
        //Goto detail page
    }

    override fun onClick(view: View) {}

    override fun isOnBackPressed(): Boolean = true

    override fun showActionBar(): Boolean = true

    override fun showActionBarBackBtn(): Boolean = false
}