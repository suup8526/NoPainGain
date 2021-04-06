from sanic import Sanic, response
from sanic_auth import Auth, User
from user_db import insert_user, select_user
import os


# user authentication
app = Sanic("server")
app.config.AUTH_LOGIN_ENDPOINT = 'login'
auth = Auth(app)
session = {}


@app.middleware('request')
async def add_session_to_request(request):
    # setup session
    request.ctx.session = session

@app.route('/auth_test')
@auth.login_required
async def auth_test(request):
    return response.text("You are secured!")

@app.route('/login', methods=['POST'])
async def login(request):
    username = request.form.get('username')
    password = request.form.get('password')
    print("Try to login with: ", username, password)
    # fetch user from database
    userinfo = select_user(username)
    if userinfo is not None:
        if password.strip() == userinfo[2].strip():
            user = User(id=userinfo[0], name=username)
            auth.login_user(request, user)
            return response.redirect('/auth_test')
    
    return response.text("Invalid username or password!")

@app.route('/logout')
@auth.login_required
async def logout(request):
    auth.logout_user(request)
    return response.redirect('/login')

@app.route('/signup', methods=['POST'])
async def signup(request):
    username = request.form.get('username')
    password = request.form.get('password')
    email = request.form.get('email')
    print("Try to signup with: ", username, password, email)
    id = insert_user(username, password, email)
    print("ID: ", id)
    if id is None:
        return response.text("Duplicate username or email!")
    resp = {
        "ID": id
    }
    return response.json(resp)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=os.environ.get('PORT') or 80)