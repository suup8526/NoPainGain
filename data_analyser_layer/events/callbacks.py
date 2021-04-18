from aio_pika import IncomingMessage
import boto3
import logging

from botocore.exceptions import ClientError
from config import S3Config
import matplotlib.pyplot as plt
from matplotlib.backends import backend_pdf


def plot_graph_ratings(x_values, y_values, x_label, y_label, title):
    '''plot a bar graph'''
    fig = plt.figure(1)
    plt.bar(x=x_values, height=y_values)
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    for index, value in enumerate(y_values):
        plt.text(x_values[index], value + 0.5, value)

    return fig


def plot_graph_reviews(x_values, y_values, x_label, y_label, title):
    '''plot a scatter graph'''
    fig = plt.figure()
    plt.scatter(x=x_values, y=y_values)
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)

    return fig


def upload_file(file_name):
    """Upload a file to an S3 bucket

    :param file_name: File to upload
    :param bucket: Bucket to upload to
    :param object_name: S3 object name. If not specified then file_name is used
    :return: True if file was uploaded, else False
    """

    # Upload the file
    s3 = boto3.resource(
        service_name='s3',
        region_name=S3Config.Bucket_region,
        aws_access_key_id=S3Config.access_id,
        aws_secret_access_key=S3Config.secret_key
    )
    try:
        response = s3.Bucket(S3Config.Bucket_name).upload_file(Filename=file_name, Key=file_name)
    except ClientError as e:
        logging.error(e)
        return False
    return True


async def consume_restaurant_data(message: IncomingMessage):
    """
    on_message doesn't necessarily have to be defined as async.
    Here it is to show that it's possible.
    """
    # print(" [x] Received message %r" % message)
    # print("Message body is: %r" % message.body)
    # print("Before sleep!")
    # print("After sleep!")
    from models.restaurants import Restaurants
    from models import db
    from collections import defaultdict
    tuples = await db.status(db.text("select rating, review_count from restaurants"))
    rating_reviews = tuples[1]
    freq_ratings = defaultdict(int)
    freq_reviews = defaultdict(int)

    reviews = []
    # freq_reviews = {}
    for rating_review in rating_reviews:
        # ratings.append(rating_review[0])
        freq_ratings[rating_review[0]] += 1

    x_values = list(freq_ratings.keys())
    x_values.sort()
    y_values = [freq_ratings[val] for val in x_values]
    print(freq_ratings)
    rating_reviews.sort()

    fig1 = plot_graph_ratings([str(x_value) for x_value in x_values], y_values, 'ratings', 'number of restaurants',
                              'rating vs restaurant_count')
    fig2 = plot_graph_reviews([val[0] for val in rating_reviews], [val[1] for val in rating_reviews], 'ratings',
                              'reviews count',
                              'rating vs reviews count')

    try:
        import os
        os.remove(S3Config.analytics)
    except Exception:
        print("file_not_present")

    pdf = backend_pdf.PdfPages(S3Config.analytics)
    pdf.savefig(fig1)
    pdf.savefig(fig2)
    pdf.close()
    upload_file(S3Config.analytics)
    import os
    os.remove(S3Config.analytics)
