#This file contains all the route functions
from flask import request, jsonify, session
from MyApp import app, db
from sqlalchemy import *

# Instead of Users table, all other DB tables can also be imported as and when required
from models import Users

# Login Route
@app.route('/Login', methods=['POST'])
def login():
	print 'TEST: Inside Login Route.'
	error = None
	if request.method == 'POST':
		print 'TEST: Inside POST block for Login.'

		# Below data is coming from Android JSON request
		email     = 	request.json['email']
		password  = 	request.json['password']
		inputdata = 	Users.query.filter_by(Email = email, Password = password).first()
		output    = 	[]

		if inputdata:
			output.append(dict(Msg = 'Correct credentials!!', UserID = inputdata.Uid, FirstName = inputdata.Fname, LastName = inputdata.Lname,Email = inputdata.Email))
		else:
			output.append(dict(Msg = 'Incorrect credentials!!'))

	return jsonify({'result' : output})


# Register Route
@app.route('/Registration', methods = ['POST'])
def registerUser():
	print 'TEST: Inside Registration Route.'
	error = None
	if request.method == 'POST':
		print 'TEST: Inside POST block for Registration.'
		
		# Below data is coming from Android JSON request
		email 		= request.json['username']
        password 	= request.json['password']
        firstname 	= request.json['firstname']
        lastname 	= request.json['lastname']
        
        output = []

        # Check if new user details are already present in the database
        checkUser = Users.query.filter_by(Email = email).first()
                
        #print 'First Name:',checkUser.Fname
        #print 'Email:', checkUser.Email
        if checkUser:
        	if email == checkUser.Email:
        		output.append(dict(Msg = 'User already registered.'))
        elif email and password and firstname and lastname: #IFF all the mandatory values are present then register the candidate
        	new_user = Users(Fname = firstname, Lname = lastname, Email = email, Password = password)
        	db.session.add(new_user)
        	db.session.commit()
        	output.append(dict(Msg = 'User successfully registered.', Ufname = firstname))
        else:
        	output.append(dict(Msg = 'User not registered, please try again.'))

	return jsonify({'result' : output})