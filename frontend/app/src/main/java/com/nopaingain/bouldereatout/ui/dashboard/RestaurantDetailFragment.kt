package com.nopaingain.bouldereatout.ui.dashboard

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.network.model.restaurant.SimpleRestaurantModel
import com.nopaingain.bouldereatout.ui.base.BaseFragment
import com.nopaingain.bouldereatout.utils.getRatingColor
import com.nopaingain.bouldereatout.utils.loadImage
import com.nopaingain.bouldereatout.viewmodels.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_restaurant_detail.*


class RestaurantDetailFragment : BaseFragment() {

    private var simpleRestaurantModel: SimpleRestaurantModel? = null
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
        simpleRestaurantModel = arguments?.get("model") as SimpleRestaurantModel?
        simpleRestaurantModel ?: return
        loadRestaurantDetails(simpleRestaurantModel)
    }

    override fun setupUI() {
        ivCall?.setOnClickListener(onClickListener)
        ivMap?.setOnClickListener(onClickListener)
    }

    private fun loadRestaurantDetails(restaurant: SimpleRestaurantModel?) {
        restaurant ?: return
        if (restaurant.imageUrl != null)
            ivRestaurant?.loadImage(R.drawable.ic_restaurant, restaurant.imageUrl)
        else
            ivRestaurant?.setImageResource(R.drawable.ic_restaurant)
        (activity as AppCompatActivity).supportActionBar?.title = restaurant.name
        tvRestaurantName?.text = restaurant.name ?: ""
        if (restaurant.categories != null && restaurant.categories.isNotEmpty())
            tvRestaurantCuisine?.text = restaurant.categories.joinToString(separator = ", ")
        tvRestaurantLoc?.text = restaurant.address ?: getString(R.string.boulder)
        tvRestaurantRatingValue?.text = (restaurant.rating ?: 0).toString()
        tvRestaurantDetail?.text =
            getString(R.string.More_detail_coming_soon)

        val ratingColor = getRatingColor(requireContext(), restaurant.rating ?: 0.0)
        tvRestaurantRatingValue?.setTextColor(ratingColor)
        ivRestaurantRating.setColorFilter(ratingColor, android.graphics.PorterDuff.Mode.SRC_IN)

        tvRestaurantReviewCount.text = getString(R.string.review_count, restaurant.reviewCount)
        if (restaurant.price != null)
            tvRestaurantPrice.text = getString(R.string.price, restaurant.price)
    }

    override fun onClick(view: View) {
        when (view) {
            ivCall -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:" + (simpleRestaurantModel?.phone ?: ""))
                startActivity(intent)
            }
            ivMap -> {
                val map = "http://maps.google.co.in/maps?q=${simpleRestaurantModel?.address}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map))
                startActivity(intent)
            }
        }
    }

    override fun isOnBackPressed(): Boolean = true

    override fun showActionBar(): Boolean = true

    override fun showActionBarBackBtn(): Boolean = true
}