import psycopg2
import os

# postgres
DATABASE_URL = os.environ['DATABASE_URL']
conn = psycopg2.connect(DATABASE_URL, sslmode='require')
conn.autocommit = True


def insert_user(username, password, email):
    print("Try to insert into DB")
    id = None
    try:
        cur = conn.cursor()

        sql = """INSERT INTO USERS (USERNAME, PASSWORD, EMAIL)
                VALUES (%s, %s, %s)
                RETURNING ID;"""

        cur.execute(sql, (username, password, email))

        id = cur.fetchone()[0]
        print(id)
        #conn.commit()
    except Exception as error:
        #conn.rollback()
        print(error)
    finally:
        if cur.closed is False:
            cur.close()

    return id

def select_user(username):
    print("Try to select from DB")
    row = None
    try:
        cur = conn.cursor()

        sql = """SELECT ID, USERNAME, PASSWORD, EMAIL
                 FROM USERS
                 WHERE USERNAME=%s;"""

        cur.execute(sql, (username,))

        # result should be one or zero
        row = cur.fetchone()
        print(row)
    except Exception as error:
        #conn.rollback()
        print(error)
    finally:
        if cur.closed is False:
            cur.close()
    
    return row
