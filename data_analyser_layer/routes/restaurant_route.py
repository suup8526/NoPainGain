from sanic import response, Blueprint

data_analyser_bp = Blueprint('data_analyser_bp')


@data_analyser_bp.route('/greeting_message', methods=['POST'])
async def greeting_handler(request):
    name = request.json.get('name')
    resp = {
        "resp_message": "Hello " + name
    }
    return response.json(resp)


@data_analyser_bp.route('/restaurants', methods=['GET'])
async def get_restaurants(request):
    limit = request.args.get('limit')
    offset = request.args.get('offset')
    print("printing request to get restaurants :- ", limit, offset)
    from models.restaurants import Restaurants
    restaurants = await (Restaurants.query.limit(limit).offset(offset).gino.all())
    final_result = {}
    final_list = []
    for restaurant in restaurants:
        final_list.append({'id': restaurant.id, 'name': restaurant.name, 'external_id': restaurant.external_id,
                           'image_url': restaurant.image_url, 'review_count': restaurant.review_count,
                           'rating': restaurant.rating, 'address': restaurant.address, 'phone': restaurant.phone,
                           'categories': restaurant.categories, 'price': restaurant.price})

    final_result["restaurants"] = final_list

    return response.json(final_result)


