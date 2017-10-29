#This file contains all the route functions
from flask import request, jsonify, session
from reportal import app, db
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
			output.append(dict(Msg = 'Correct credentials!!', FirstName = inputdata.Fname, LastName = inputdata.Lname,Email = inputdata.Email))
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
		email 		= request.json['email']
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


# Priority Route
@app.route('/Priority', methods=['POST', 'PUT'])
def addPriority():
	print 'TEST: Inside Priority Route.'
	error = None
	if request.method == 'POST':
		print 'TEST: Inside POST block for Priority.'

		# Below data is coming from Admin portal JSON request
		#priority_id       = 	request.json['priority_id'] # Auto-Increment field in DB
		priority_name     = 	request.json['priority_name']
		priority_status   = 	request.json['priority_status']
		output            = 	[]
		
		checkPriority     =     Users.query.filter_by(priority_name = priority_name).first()

		if checkPriority && priority_name = checkPriority.priority_name:
			output.append(dict(Msg = 'Priority already present.'))
		elif priority_name && priority_status:
			new_priority = Priority(priority_name = priority_name, priority_status = priority_status)
			db.session.add(new_priority)
			db.session.commit()
			output.append(dict(Msg = 'Priority added successfully.'))
		else:
			output.append(dict(Msg = 'Priority not added, please try again.'))

	return jsonify({'result' : output})