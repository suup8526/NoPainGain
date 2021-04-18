from sanic import response, Blueprint

data_analyser_bp = Blueprint('data_analyser_bp')


async def get_restaurants_from_db(limit, offset):
    from models.restaurants import Restaurants
    return await Restaurants.query.limit(limit).offset(offset).gino.all()


@data_analyser_bp.route('/restaurants', methods=['GET'])
async def get_restaurants(request):
    limit = request.args.get('limit')
    offset = request.args.get('offset')
    print("printing request to get restaurants :- ", limit, offset)
    restaurants = await get_restaurants_from_db(limit=limit, offset=offset)
    final_result = {}
    final_list = []
    for restaurant in restaurants:
        final_list.append({'id': restaurant.id, 'name': restaurant.name, 'external_id': restaurant.external_id,
                           'image_url': restaurant.image_url, 'review_count': restaurant.review_count,
                           'rating': restaurant.rating, 'address': restaurant.address, 'phone': restaurant.phone,
                           'categories': restaurant.categories, 'price': restaurant.price})

    final_result["restaurants"] = final_list

    return response.json(final_result)