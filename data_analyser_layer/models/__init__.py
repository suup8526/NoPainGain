from gino import Gino
from config import DBConstants

# pylint: disable=invalid-name
# Connect to the database
db = None

async def connect_db():
    import ssl

    ctx = ssl.create_default_context(cafile="")
    ctx.check_hostname = False
    ctx.verify_mode = ssl.CERT_NONE
    global db
    db = Gino()
    await db.set_bind(DBConstants.DB_address, echo=True)
    print(db)