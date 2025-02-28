from flask import Flask 
from public import public
from admin import admin

from factory import factory



from api import api

app=Flask(__name__)

app.secret_key='key'

app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(factory,url_prefix='/factory')

app.register_blueprint(api,url_prefix='/api')


app.run(debug=True,port=5003,host="0.0.0.0")