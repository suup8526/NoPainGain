offset = 0
import os


class YELP_API:
    API_HOST = 'https://api.yelp.com'
    SEARCH_PATH = '/v3/businesses/search'
    BUSINESS_PATH = '/v3/businesses/'  # Business ID will come after slash.
    API_KEY = '0EYLEdjNHKA6MgJmRGt2BTPR6FU8_vHK21ivVrOnPialKSxwZSdQClsNOy-jkd9Afl7nqqak_j_-3aRx6P5ufsWzlcFjB8uJ1Se0-Xi_l8vm2Bhd84P3eoLwJ7hrYHYx'
    DEFAULT_LOCATION = 'Boulder, CO'
    SEARCH_LIMIT = 3
    LIMIT = 2
    SEARCH_API_OBJECTS = 'businesses'


class DBConstants:
    DB_address = str(os.getenv('DATABASE_URL', 'postgresql://localhost/vasusharma'))
    # DB_address = 'postgresql://localhost/vasusharma'


class RabbitmqBroker:
    address = os.getenv('CLOUDAMQP_URL', "amqp://guest:guest@localhost/")
    restaurant_data_notify_queue = 'restaurant_data_notify'


class CRON_INTERVAL:
    fetch_data_and_notify = 40


def set_api_offset():
    global offset
    offset += YELP_API.LIMIT

def get_api_offset():
    return offset