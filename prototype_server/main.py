from sanic import Sanic, response
import os

app = Sanic("prototype_server")

@app.route('/greeting_message', methods=['POST'])
async def greeting_handler(request):
    name = request.json.get('name')
    resp = {
        "resp_message": "Hello " + name
    }
    return response.json(resp)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=os.environ.get('PORT') or 80)


