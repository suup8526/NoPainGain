import psycopg2
import os

# postgres
DATABASE_URL = os.environ['DATABASE_URL']
conn = psycopg2.connect(DATABASE_URL, sslmode='require')
conn.autocommit = True


def insert_user(username, name, password, email):
    print("Try to insert into DB")
    id = None
    try:
        cur = conn.cursor()

        sql = """INSERT INTO USERS (USERNAME, NAME, PASSWORD, EMAIL)
                VALUES (%s, %s, %s, %s)
                RETURNING ID;"""

        cur.execute(sql, (username, name, password, email))

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

        sql = """SELECT ID, USERNAME, NAME, PASSWORD, EMAIL
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

def update_user(old_username, username, name, password, email):
    print("Try to update DB")
    row = None
    try:
        cur = conn.cursor()

        sql = """UPDATE USERS SET
                 USERNAME=COALESCE(%s, USERNAME),
                 NAME=COALESCE(%s, NAME),
                 PASSWORD=COALESCE(%s, PASSWORD),
                 EMAIL=COALESCE(%s, EMAIL)
                 WHERE USERNAME=%s;"""

        cur.execute(sql, (old_username, username, name, password, email))
    except Exception as error:
        #conn.rollback()
        print(error)
    finally:
        if cur.closed is False:
            cur.close()

def update_password(username, password):
    print("Try to update DB")
    row = None
    try:
        cur = conn.cursor()

        sql = """UPDATE USERS SET
                 PASSWORD=%s
                 WHERE USERNAME=%s;"""

        cur.execute(sql, (username, password))
    except Exception as error:
        #conn.rollback()
        print(error)
    finally:
        if cur.closed is False:
            cur.close()