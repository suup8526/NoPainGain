from aio_pika import IncomingMessage


async def consume_restaurant_data(message: IncomingMessage):
    """
    on_message doesn't necessarily have to be defined as async.
    Here it is to show that it's possible.
    """
    print(" [x] Received message %r" % message)
    print("Message body is: %r" % message.body)
    print("Before sleep!")
    print("After sleep!")
