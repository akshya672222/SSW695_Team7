# This file contains all DB related settings
# SQLALCHEMY is used to connect to Sqlite3 database
DEBUG = True

# Change db location from local to one stored on EC2 instance
SQLALCHEMY_DATABASE_URI = 'sqlite:///C:/Stevens data/3rd SEM/SSW_Capstone/SSW695_Team7/SSW695/API/Reportal.db'

#SECRET_KEY = 'thisisasecret'
SQLALCHEMY_ECHO = True
