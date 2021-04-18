class TestRestaurants():
    def __init__(self, name, id, external_id, image_url, review_count, rating, address, phone, categories, price):
        self.name = name
        self.id = id
        self.external_id = external_id
        self.image_url = image_url
        self.review_count = review_count
        self.rating = rating
        self.address = address
        self.phone = phone
        self.categories = categories
        self.price = price


def get_single_restaurant_resp():
    restaurant = TestRestaurants(id=1, name='restaurant', external_id='avqw1214',
                                 image_url='image_url', review_count=10,
                                 rating=4.5, address='address', phone='+13034567819',
                                 categories=['cat1', 'cat2'], price='$$')
    return [restaurant]


def get_multi_restaurant_resp():
    import random
    num = random.randint(2, 8)
    restaurants = []
    for val in range(num):
        restaurants.append(TestRestaurants(id=val, name='restaurant', external_id='avqw1214',
                                           image_url='image_url', review_count=10,
                                           rating=4.5, address='address', phone='+13034567819',
                                           categories=['cat1', 'cat2'], price='$$'))
    return restaurants
