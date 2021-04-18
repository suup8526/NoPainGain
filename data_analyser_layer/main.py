from sanic import Sanic, response
import os

app = Sanic(__name__)


if __name__ == "__main__":

    @app.listener('before_server_start')
    async def after_server_start(sanic_app, loop) -> None:

        # init event-client
        from events import connect_to_queue
        await connect_to_queue(loop)

        # connect to database
        from models import connect_db
        await connect_db()


    from routes import init_routes
    init_routes(app)

    app.run(host="0.0.0.0", port=os.environ.get('PORT') or 8080)
