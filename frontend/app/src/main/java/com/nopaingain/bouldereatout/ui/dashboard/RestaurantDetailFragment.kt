package com.nopaingain.bouldereatout.ui.dashboard

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.network.model.restaurant.SimpleRestaurantModel
import com.nopaingain.bouldereatout.ui.base.BaseFragment
import com.nopaingain.bouldereatout.utils.loadImage
import com.nopaingain.bouldereatout.viewmodels.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_restaurant_detail.*

class RestaurantDetailFragment : BaseFragment() {

    private lateinit var restaurantViewModel: RestaurantViewModel
    private lateinit var prefs: SharedPreferences

    override fun getLayoutID(): Int = R.layout.fragment_restaurant_detail

    override fun init() {
        restaurantViewModel =
            ViewModelProvider(requireActivity()).get(RestaurantViewModel::class.java)
        prefs = sessionManager.customPrefs()
        initObservers()
    }

    private fun initObservers() {
        restaurantViewModel.obProcessing.observe(this, Observer {
            if (it == true) {
                displayProgress()
            } else {
                hideProgress()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = arguments?.getString("id")
        id ?: return
        loadRestaurantDetails(restaurantViewModel.getRestaurantDetail(id))
    }

    override fun setupUI() {


    }

    private fun loadRestaurantDetails(restaurant: SimpleRestaurantModel?) {
        restaurant ?: return
        if (restaurant.imageUrl != null)
            ivRestaurant?.loadImage(R.drawable.ic_restaurant, restaurant.imageUrl)
        else
            ivRestaurant?.setImageResource(R.drawable.ic_restaurant)
        (activity as AppCompatActivity).supportActionBar?.title = restaurant.name
        tvRestaurantName?.text = restaurant.name
        tvRestaurantCuisine?.text = restaurant.cusine
        tvRestaurantLoc?.text = restaurant.location
        tvRestaurantRatingValue?.text = restaurant.rating
        tvRestaurantDetail?.text =
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
    }

    override fun onClick(view: View) {

    }

    override fun isOnBackPressed(): Boolean = true

    override fun showActionBar(): Boolean = true

    override fun showActionBarBackBtn(): Boolean = true
}