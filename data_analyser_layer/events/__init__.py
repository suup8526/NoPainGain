from aio_pika import connect
from config import RabbitmqBroker
from events.callbacks import consume_restaurant_data


async def connect_to_queue(loop):
    # Perform connection
    connection = await connect(RabbitmqBroker.address, loop=loop)
    print(connection)
    # Creating a channel
    channel = await connection.channel()

    # Declaring queue
    queue = await channel.declare_queue(RabbitmqBroker.restaurant_data_queue)

    # Start listening the queue with name 'hello'
    await queue.consume(consume_restaurant_data, no_ack=True)
