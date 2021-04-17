from routes.restaurant_route import data_analyser_bp
blueprints = [data_analyser_bp]


async def init_routes(app):
    # Register blueprints
    for blueprint in blueprints:
        app.blueprint(blueprint)