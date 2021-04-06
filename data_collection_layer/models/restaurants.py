from models import db

class Restaurants(db.Model):
    __tablename__ = 'restaurants'

    name = db.Column(db.String())
    alias = db.Column(db.String())
    id = db.Column(db.BigInteger(), primary_key=True)
    # review_count = db.Column(db.Integer())
    # rating = db.Column(db.Float())
    # phone = db.Column(db.String())
    # location = db.Column(db.Json())