from models import db


class Restaurants(db.Model):
    __tablename__ = 'restaurants'

    name = db.Column(db.String())
    alias = db.Column(db.String())
    id = db.Column(db.BigInteger(), primary_key=True)
    external_id = db.Column(db.String())
    image_url = db.Column(db.String())
    review_count = db.Column(db.Integer())
    rating = db.Column(db.Float())
    address = db.Column(db.String())
    phone = db.Column(db.String())
    categories = db.Column(db.ARRAY(db.String()))
    price = db.Column(db.String())