import logging, requests
from config import YELP_API, get_api_offset, set_api_offset
from urllib.parse import quote
from models.restaurants import Restaurants

logger = logging.getLogger(__name__)


async def yelp_request(host, path, api_key, url_params=None):
    """Given your API_KEY, send a GET request to the API.
    Args:
        host (str): The domain host of the API.
        path (str): The path of the API after the domain.
        API_KEY (str): Your API Key.
        url_params (dict): An optional set of query parameters in the request.
    Returns:
        dict: The JSON response from the request.
    Raises:
        HTTPError: An error occurs from the HTTP request.
    """
    url_params = url_params or {}
    url = '{0}{1}'.format(host, quote(path.encode('utf8')))
    headers = {
        'Authorization': 'Bearer %s' % api_key,
    }

    print(u'Querying {0} ...'.format(url))

    response = requests.request('GET', url, headers=headers, params=url_params)

    return response.json()


async def fetch_data_and_notify() -> None:
    """
    creates meetings for recordable calvents
    """
    url_params = {
        'location': YELP_API.DEFAULT_LOCATION,
        'limit': YELP_API.LIMIT,
        'offset': get_api_offset()
    }

    restaurants = await yelp_request(host=YELP_API.API_HOST, path=YELP_API.SEARCH_PATH, api_key=YELP_API.API_KEY,
                             url_params=url_params)
    restaurants = restaurants[YELP_API.SEARCH_API_OBJECTS]

    # for now extracting only the name and alias of the restaurants
    db_restaurant_objects = [{'name': restaurant['name'], 'alias': restaurant['alias']} for restaurant in restaurants]

    print(restaurants)
    set_api_offset()
    import random
    await Restaurants.insert().values(db_restaurant_objects).gino.scalar()

