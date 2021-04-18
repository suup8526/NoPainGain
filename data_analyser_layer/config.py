import os


class RabbitmqBroker:
    address = os.getenv('CLOUDAMQP_URL', "amqp://guest:guest@localhost/")
    restaurant_data_queue = 'restaurant_data_notify'


class DBConstants:
    DB_address = str(os.getenv('DATABASE_URL', 'postgresql://localhost/vasusharma'))


class S3Config:
    Bucket_name = str(os.getenv('S3_BUCKET_NAME', 'nopaingain'))
    Bucket_region = 'us-east-2'
    access_id = str(os.getenv('AWS_ACCESS_KEY_ID'))
    secret_key = str(os.getenv('AWS_SECRET_ACCESS_KEY'))
    analytics = "analytics.pdf"


