import unittest, asyncio

from unittest import mock
from sanic import Sanic, response, Blueprint
from main import user_bp, login, logout, signup, reset, update, auth_test


blueprints = [user_bp]


def init_routes(app):
    # Register blueprints
    for blueprint in blueprints:
        app.blueprint(blueprint)


def mock_login_resp(username, password):
    if username=='test' and password=='test':
        resp = {
                "ID": 1,
                "NAME": "John",
                "EMAIL": "test@test"
        }
        return response.json(resp)
    return response.json({'message':'Invalid username or password!'})


def mock_auth_test_resp(valid):
    if valid:
        return response.json({'message':'You are secured!'})
    return response.json({'message':'unauthorized'})


def mock_invalid_signup_resp():
    return response.json({'message':'Duplicate username or email!'})


class TestUser(unittest.TestCase):
    @classmethod
    def setUpClass(cls) -> None:
        cls.app = Sanic(cls.__name__)
        init_routes(cls.app)

    @classmethod
    def tearDownClass(cls) -> None:
        cls.app.test_client.post("/logout")

    @mock.patch('main.login', return_value=mock_login_resp("test","test"))
    def test_valid_login(self, mock_login_resp):
        """
        Testing successful login response
        """
        import json
        request, response = self.app.test_client.post("/login", data=json.dumps({"username": "test", "password": "test"}))
        resp = json.loads(response.body)
        expt = json.loads(mock_login_resp.return_value.body)
        self.assertEqual(response.status, 200)
        self.assertEqual(resp.get('ID'), expt.get('ID'))
        self.assertEqual(resp.get('NAME'), expt.get('NAME'))
        self.assertEqual(resp.get('EMAIL'), expt.get('EMAIL'))

    @mock.patch('main.login', return_value=mock_login_resp("test","wrong"))
    def test_invalid_login(self, mock_login_resp):
        """
        Testing incorrect login response
        """
        import json
        request, response = self.app.test_client.post("/login", data=json.dumps({"username": "test", "password": "wrong"}))
        resp = json.loads(response.body)
        expt = json.loads(mock_login_resp.return_value.body)
        self.assertEqual(response.status, 200)
        self.assertEqual(resp.get('message'), expt.get('message'))

    @mock.patch('main.auth_test', return_value=mock_auth_test_resp(True))
    def test_valid_auth(self,mock_auth_test_resp):
        """
        Testing valid authentication after successful login
        """
        import json
        request, response = self.app.test_client.post("/login", data=json.dumps({"username": "test", "password": "test"}))
        request, response = self.app.test_client.get("/auth_test")
        resp = json.loads(response.body)
        expt = json.loads(mock_auth_test_resp.return_value.body)
        self.assertEqual(response.status, 200)
        self.assertEqual(resp.get('message'), expt.get('message'))
    
    @mock.patch('main.auth_test', return_value=mock_auth_test_resp(False))
    def test_invalid_auth(self,mock_auth_test_resp):
        """
        Testing valid authentication without login
        """
        import json
        #request, response = self.app.test_client.post("/login", data=json.dumps({"username": "test", "password": "wrong"}))
        request, response = self.app.test_client.get("/auth_test")
        resp = json.loads(response.body)
        expt = json.loads(mock_auth_test_resp.return_value.body)
        self.assertEqual(response.status, 401)
        self.assertEqual(resp.get('message'), expt.get('message'))

    @mock.patch('main.signup', return_value=mock_invalid_signup_resp())
    def test_signup(self,mock_invalid_signup_resp):
        """
        Testing valid signup
        """
        import json
        import time
        ts = time.time()
        request, response = self.app.test_client.post("/signup", data=json.dumps(
            {"username": ts, "name": ts, "password": "test", "email": str(ts) + "@test"}))
        resp = json.loads(response.body)
        expt = json.loads(mock_invalid_signup_resp.return_value.body)
        self.assertEqual(response.status, 200)
        self.assertTrue(resp.get('ID'))

    @mock.patch('main.signup', return_value=mock_invalid_signup_resp())
    def test_duplicated_signup(self,mock_invalid_signup_resp):
        """
        Testing duplicated signup
        """
        import json
        request, response = self.app.test_client.post("/signup", data=json.dumps(
            {"username": "test", "name": "test", "password": "test", "email": "test@test"}))
        resp = json.loads(response.body)
        expt = json.loads(mock_invalid_signup_resp.return_value.body)
        self.assertEqual(response.status, 200)
        self.assertEqual(resp.get('message'), expt.get('message'))

if __name__ == '__main__':
    unittest.main()