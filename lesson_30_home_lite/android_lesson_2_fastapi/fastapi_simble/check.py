import requests
import json

url = 'http://127.0.0.1:5000/api/get_answer'
data = {"text": "привет"}

response = requests.post(url, data=json.dumps(data))

print(response.text)