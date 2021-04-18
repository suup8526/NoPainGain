from sanic import Sanic, response
from sanic_auth import Auth, User
from user_db import insert_user, select_user
import os, ssl


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
    return response.json({'message':'You are secured!'})

@app.route('/login', methods=['POST'])
async def login(request):
    #username = request.form.get('username')
    #password = request.form.get('password')
    username = request.json.get('username')
    password = request.json.get('password')
    print("Try to login with: ", username, password)
    # fetch user from database
    userinfo = select_user(username)
    if userinfo is not None:
        if password.strip() == userinfo[3].strip():
            user = User(id=userinfo[0], name=username)
            auth.login_user(request, user)
            resp = {
                "ID": userinfo[0],
                "NAME": userinfo[2].strip(),
                "EMAIL": userinfo[4].strip()
            }
            return response.json(resp)
    
    return response.json({'message':'Invalid username or password!'})

@app.route('/logout')
@auth.login_required
async def logout(request):
    auth.logout_user(request)
    return response.redirect('/login')

@app.route('/signup', methods=['POST'])
async def signup(request):
    #username = request.form.get('username')
    #password = request.form.get('password')
    #email = request.form.get('email')
    #name = request.form.get('name')
    username = request.json.get('username')
    password = request.json.get('password')
    email = request.json.get('email')
    name = request.json.get('name')
    print("Try to signup with: ", username, name, password, email)
    id = insert_user(username, name, password, email)
    print("ID: ", id)
    if id is None:
        return response.json({'message':'Duplicate username or email!'})
    resp = {
        "ID": id
    }
    return response.json(resp)

@app.route('/reset', methods=['POST'])
async def reset(request):
    #username = request.form.get('username')
    #password = request.form.get('password')
    username = request.json.get('username')
    password = request.json.get('password')
    print("Try to update password with: ", username, password)
    id = update_password(username, password)
    return response.json({'message':'Password updated!'})

@app.route('/update', methods=['POST'])
@auth.login_required
async def update(request):
    #old_username = request.form.get('old_username')
    #username = request.form.get('username')
    #password = request.form.get('password')
    #email = request.form.get('email')
    #name = request.form.get('name')
    old_username = request.json.get('old_username')
    username = request.json.get('username')
    password = request.json.get('password')
    email = request.json.get('email')
    name = request.json.get('name')
    print("Try to update user with: ", old_username, username, name, password, email)
    id = update_user(old_username, username, name, password, email)
    return response.json({'message':'User updated!'})

if __name__ == "__main__":
    #context = ssl.create_default_context(purpose=ssl.Purpose.CLIENT_AUTH)
    #context.load_cert_chain("selfsigned.crt", keyfile="private.key")
    #app.go_fast(host="0.0.0.0", port=os.environ.get('PORT') or 8080, ssl=context, workers=os.cpu_count())
    app.run(host="0.0.0.0", port=os.environ.get('PORT') or 8080)