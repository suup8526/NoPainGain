import unittest, asyncio

from unittest import mock
from tests.utils.db_mock_objects import get_single_restaurant_resp, get_multi_restaurant_resp
from routes import init_routes
from sanic import Sanic


class TestDataAnalyser(unittest.TestCase):
    @classmethod
    def setUpClass(cls) -> None:
        cls.app = Sanic(cls.__name__)
        init_routes(cls.app)

    @mock.patch('routes.restaurant_route.get_restaurants_from_db', return_value=[])
    def test_empty_restaurant_list(self, mock_empty_resp):
        """
        Testing empty response for a restaurant list
        """
        import json
        request, response = self.app.test_client.get("/restaurants", params={"limit": 10, "offset": 0})
        self.assertEqual(response.status, 200)
        self.assertEqual(json.loads(response.body), {"restaurants": []})

    @mock.patch('routes.restaurant_route.get_restaurants_from_db', return_value=get_single_restaurant_resp())
    def test_single_resp_restaurant_list(self, mock_single_resp):
        """
        Testing single restaurant from restaurant list
        """
        import json
        request, response = self.app.test_client.get("/restaurants", params={"limit": 10, "offset": 0})
        self.assertEqual(response.status, 200)
        resp = json.loads(response.body)
        expected_return_value = mock_single_resp.return_value
        self.assertEqual(resp.get('restaurants')[0].get('name'), expected_return_value[0].name)
        self.assertEqual(resp.get('restaurants')[0].get('id'), expected_return_value[0].id)
        self.assertEqual(resp.get('restaurants')[0].get('external_id'), expected_return_value[0].external_id)
        self.assertEqual(resp.get('restaurants')[0].get('rating'), expected_return_value[0].rating)

    @mock.patch('routes.restaurant_route.get_restaurants_from_db', return_value=get_multi_restaurant_resp())
    def test_multi_resp_restaurant_list(self, mock_single_resp):
        """
        Testing single restaurant from restaurant list
        """
        import json
        request, response = self.app.test_client.get("/restaurants", params={"limit": 10, "offset": 0})
        self.assertEqual(response.status, 200)
        resp = json.loads(response.body)
        expected_return_value = mock_single_resp.return_value
        for i in range(len(resp.get('restaurants'))):
            self.assertEqual(resp.get('restaurants')[i].get('name'), expected_return_value[i].name)
            self.assertEqual(resp.get('restaurants')[i].get('id'), expected_return_value[i].id)
            self.assertEqual(resp.get('restaurants')[i].get('external_id'), expected_return_value[i].external_id)
            self.assertEqual(resp.get('restaurants')[i].get('rating'), expected_return_value[i].rating)


if __name__ == '__main__':
    unittest.main()
