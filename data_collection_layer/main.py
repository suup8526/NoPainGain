import uvloop
import asyncio
from sanic import Sanic, response
import logging

if __name__ == '__main__':

    asyncio.set_event_loop(uvloop.new_event_loop())
    cron_app = Sanic(__name__)

    logger = logging.getLogger(__name__)


    @cron_app.listener('before_server_start')
    async def before_server_start(sanic_app, loop) -> None:
        from models import connect_db
        await connect_db()
        from models import db
        from models.restaurants import Restaurants
        # create tables if they don't exist
        await db.gino.create_all()

    @cron_app.listener('after_server_start')
    async def after_server_start(sanic_app, loop) -> None:
        import crons
        # init event-client
        from utils.rabbitmq import connect_broker
        await connect_broker(loop)
        # init crons
        await asyncio.gather(*crons.init_crons())

    @cron_app.listener('before_server_stop')
    async def before_server_stop(sanic_app, loop) -> None:
        from utils.rabbitmq import close_conn_broker
        await close_conn_broker()


    @cron_app.route('/health', methods=['GET'])
    async def get_restaurant_data(request):
        return response.json('healthy route')

    cron_app.run(host='0.0.0.0', port=8080, access_log=False)