package com.nopaingain.bouldereatout.ui.dashboard

import android.annotation.SuppressLint
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
import com.nopaingain.bouldereatout.utils.Constants
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
        getRestaurant(0)
        initRV()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
    }

    private fun initRV() {
        restaurantAdapter = RestaurantAdapter(requireContext(), restaurantList, this)
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
                    getRestaurant(rvRestaurant.layoutManager?.itemCount?: 0)
            }
        })
    }

    private fun getRestaurant(offset: Int) {
        restaurantViewModel.getRestaurantListing(offset, 10).observe(this, Observer {
            it ?: return@Observer
            restaurantList.addAll(it)

            showNext = it.size == 10
            isFirst = false
            isLoading = false
            restaurantAdapter?.removeLoadingFooter()
            restaurantAdapter?.notifyDataSetChanged()

        })

    }

    @SuppressLint("InvalidAnalyticsName")
    override fun onRestaurantClick(position: Int) {
        val bundle = bundleOf("id" to restaurantList[position].id, "name" to restaurantList[position].name)
        firebaseAnalytics.logEvent(Constants.EVENT_RESTAURANT_CLICK, bundle)
        val param = bundleOf("model" to restaurantList[position])
        findNavController().navigate(R.id.action_mainListingFragment_to_restaurantDetailFragment, param)
    }

    override fun onClick(view: View) {}

    override fun isOnBackPressed(): Boolean = true

    override fun showActionBar(): Boolean = true

    override fun showActionBarBackBtn(): Boolean = false
}