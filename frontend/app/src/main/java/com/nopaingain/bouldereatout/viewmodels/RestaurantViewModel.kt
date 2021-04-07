package com.nopaingain.bouldereatout.viewmodels

import androidx.lifecycle.LiveData
import com.nopaingain.bouldereatout.network.model.restaurant.SimpleRestaurantModel

class RestaurantViewModel : BaseViewModel() {

    private lateinit var restaurantListResponse: LiveData<ArrayList<SimpleRestaurantModel>>
    private var restaurantList: ArrayList<SimpleRestaurantModel> = ArrayList()

    fun getRestaurantListing(offset: String?,
                             limit: Int): ArrayList<SimpleRestaurantModel> {
        addDummyData()
        return restaurantList
    }

    fun getRestaurantDetail(id: String): SimpleRestaurantModel? {
        for (restaurant in restaurantList) {
            if (id == restaurant.id) {
                return restaurant
            }
        }
        return null
    }


    private fun addDummyData() {
        restaurantList.clear()
        restaurantList.add(SimpleRestaurantModel(id="1", name = "The Sink", location = "Boulder", cusine = "American", rating = "4.4"))
        restaurantList.add(SimpleRestaurantModel(id="2", name = "Pizzeria Locale", location = "Boulder", cusine = "American, Italian", rating = "4.5"))
        restaurantList.add(SimpleRestaurantModel(id="3", name = "Le French Cafe", location = "Boulder", cusine = "French", rating = "4.7"))
        restaurantList.add(SimpleRestaurantModel(id="4", name = "Hapa Sushi Grill", location = "Boulder", cusine = "Sushi", rating = "4.5"))
        restaurantList.add(SimpleRestaurantModel(id="5", name = "The Yellow Deli", location = "Boulder", cusine = "Deli", rating = "4.4"))
        restaurantList.add(SimpleRestaurantModel(id="6", name = "Verde", location = "Boulder", cusine = "Mexican", rating = "4.4"))
        restaurantList.add(SimpleRestaurantModel(id="7", name = "Sherpa", location = "Boulder", cusine = "Nepalese", rating = "4.5"))
    }

}