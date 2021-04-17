import os


class RabbitmqBroker:
    address = os.getenv('CLOUDAMQP_URL', "amqp://guest:guest@localhost/")
    restaurant_data_queue = 'restaurant_data_notify'


class DBConstants:
    DB_address = str(os.getenv('DATABASE_URL', 'postgresql://localhost/vasusharma'))
