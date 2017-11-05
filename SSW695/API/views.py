#This file contains all the route functions
from flask import request, jsonify, session
from reportal import app, db
from sqlalchemy import *


# Instead of Users table, all other DB tables can also be imported as and when required
from models import Users, Priority

# Login Route
@app.route('/api/login', methods=['POST'])
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
			output.append(dict(Msg = 'Correct credentials!!', UserID = inputdata.UID, FirstName = inputdata.Fname, LastName = inputdata.Lname,Email = inputdata.Email))
		else:
			output.append(dict(Msg = 'Incorrect credentials!!'))

	return jsonify({'result' : output})


# Register Route
@app.route('/api/register', methods = ['POST'])
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
        user_type 	= request.json['type']#mobile app will send 1 i.e. end user
        token 		= request.json['token']#mobile app will send -1 
        status 		= request.json['status']#mobile app will send 0 i.e. deactivated
        category 	= request.json['category']#mobile app will send category of end user and CMS will send accordingly
        
        output = []

        # Check if new user details are already present in the database
        checkUser = Users.query.filter_by(Email = email).first()
                
        #print 'First Name:',checkUser.Fname
        #print 'Email:', checkUser.Email
        if checkUser:
        	if email == checkUser.Email:
        		output.append(dict(Msg = 'User already registered.'))
        elif email and password and firstname and lastname: #IFF all the mandatory values are present then register the candidate
        	new_user = Users(Fname = firstname, Lname = lastname, Email = email, Password = password, user_type = user_type, Token = token, Status = status, Category = category)
        	db.session.add(new_user)
        	db.session.commit()
        	output.append(dict(Msg = 'User successfully registered.', Ufname = firstname))
        else:
        	output.append(dict(Msg = 'User not registered, please try again.'))

	return jsonify({'result' : output})


# Add / Update Priority route
@app.route('/api/priority', methods = ['POST', 'PUT'])
def add_update_Priority():

        print 'TEST: Inside Priority Route.'
        error = None
        if request.method == 'POST':
                print 'TEST: Inside POST block for Add Priority.'
                priority_name = request.json['priority_name']
                priority_status = request.json['priority_status']

                output = []

                checkPriority = Priority.query.filter_by(Priority_name = priority_name).first()

                if checkPriority and priority_name.Priority_name:
                        output.append(dict(Msg = 'Priority already present.'))
                elif priority_name and priority_status:
                        new_priority = Priority(Priority_name = priority_name, Priority_status = priority_status)
                        db.session.add(new_priority)
                        db.session.commit()
                        output.append(dict(Msg = 'Priority added successfully.'))
                else:
                        output.append(dict(Msg = 'Priority not added, please try again.'))

        if request.method == 'PUT':
                print 'TEST: Inside PUT block for Update Priority.'
                priority_id = request.json['priority_id']
                priority_name = request.json['priority_name']
                priority_status = request.json['priority_status']

                output = []
                updateThisPriority = Priority.query.filter_by(Priority_id = priority_id).first()

                if updateThisPriority:
                        updateThisPriority.Priority_name = priority_name
                        updateThisPriority.Priority_status = priority_status
                        db.session.commit()
                        output.append(dict(Msg = 'Priority updated successfully.'))
                else:
                     output.append(dict(Msg = 'Priority not present.'))

        return jsonify({'result' : output})

# Update user Route
@app.route('/api/update_user_settings', methods = ['POST'])
def update_user_settings():
	print 'TEST: Inside update_user_settings Route.'
	error = None
	if request.method == 'POST':
		
		# Below data is coming from Android JSON request
		email 		= request.json['email']
        password 	= request.json['password']
        firstname 	= request.json['firstname']
        lastname 	= request.json['lastname']

        #output = []

        # Check if user details are already present in the database
        checkUser = Users.query.filter_by(Email = email).first()
                
        print email,password
        if checkUser:
        	if email == checkUser.Email:
        		if email and password and firstname and lastname: #IFF all the mandatory values are present then register the candidate
        			 checkUser.Email = email
        			 checkUser.Password = password
        			 checkUser.Fname = firstname
        			 checkUser.Lname = lastname

        			 if 'type' in request.json and 'status' in request.json and 'category' in request.json: 
        				user_type 	= request.json['type']
        				status 		= request.json['status']
        				category 	= request.json['category']
        				checkUser.user_type = user_type
        				checkUser.Status = status
        				checkUser.Category = category
        			 else:
        				print "Update Request is from Mobile"
        			 #if user_type and status and category: #this will be true for API call from CMS/web
        			    
        			 db.session.commit()
        			 output.append(dict(Msg = 'User successfully updated.', Fname = firstname))
        else:
        	output.append(dict(Msg = 'User not found.'))

	return jsonify({'result' : output})