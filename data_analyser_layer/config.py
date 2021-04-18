import os


class RabbitmqBroker:
    address = os.getenv('CLOUDAMQP_URL', "amqp://guest:guest@localhost/")
    restaurant_data_queue = 'restaurant_data_notify'


class DBConstants:
    DB_address = str(os.getenv('DATABASE_URL', 'postgresql://localhost/vasusharma'))


class S3Config:
    Bucket_name = str(os.getenv('S3_BUCKET_NAME', 'nopaingain'))
    Bucket_region = 'us-east-2'
    access_id = str(os.getenv('AWS_ACCESS_KEY_ID', 'AKIAYC6GOQJCG3PDUDMI'))
    secret_key = str(os.getenv('AWS_SECRET_ACCESS_KEY', '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'))
    analytics = "analytics.pdf"


