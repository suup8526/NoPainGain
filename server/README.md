# Server

## User DB
* Using Postgres on Heroku.
* Initial table _users_ can be created by _postgre_init.sql_

## User Handling
* 3 APIs provided
* Currently uses POST form submit
* _signup_: Requires username, password, email (username and email should be unique. password should be hashed to 128 CHAR size)
* _login_: Requires username, password.
* _logout_: 
* Every method which has @auth.login_required needs user login.

## Demo
* Demo version is deployed on http://user-handling.herokuapp.com

```
> curl http://user-handling.herokuapp.com/auth_test

> curl -X POST -d "username=test2&password=test&email=test2@test" http://user-handling.herokuapp.com/signup
{"ID":4}
> curl -X POST -d "username=test&password=test" http://user-handling.herokuapp.com/login

> curl http://user-handling.herokuapp.com/auth_test
You are secured!
> curl http://user-handling.herokuapp.com/logout

> curl http://user-handling.herokuapp.com/auth_test

```
