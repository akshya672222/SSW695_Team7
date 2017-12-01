# This file contains all DB related settings
# SQLALCHEMY is used to connect to Sqlite3 database
import os
DEBUG = True

#SQLALCHEMY_DATABASE_URI = 'sqlite:////home/ec2-user/Reportal.db'
dir_path = os.path.dirname(os.path.realpath('Reportal.db'))
SQLALCHEMY_DATABASE_URI = 'sqlite:///' + dir_path + '/Reportal.db'
UPLOAD_FOLDER = 'static/img'

#UPLOAD_FOLDER = os.path.dirname(os.path.abspath(__file__)) + '/Images'
#print 'UPLOAD FOLDER:', UPLOAD_FOLDER
SECRET_KEY = 'thisisasecret'
SQLALCHEMY_ECHO = True
#APPLICATION_ROOT="/api"
