#This file contains all the route functions
from flask import request, jsonify, session
from reportal import app, db
from sqlalchemy import *
from sqlalchemy_pagination import paginate


# Instead of Users table, all other DB tables can also be imported as and when required
from models import Users, Priority, Category,Notification
from werkzeug.utils import secure_filename
import os
import boto.ses
from random import randint

ALLOWED_EXTENSIONS = set(['jpg','jpeg','png'])

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
		if 'user_type' in request.json:
			inputdata =     Users.query.filter_by(Email = email, Password = password, user_type = request.json['user_type']).first()
		else:
			inputdata = 	Users.query.filter_by(Email = email, Password = password).first()
		priorities = 	Priority.query.all()
		categories =  Category.query.all()
		output    = 	[]
		categories_output    = 	[]
		priorities_output    = 	[]

		if inputdata:
			for pr in priorities:
				priorities_output.append(dict(Priority_name = pr.Priority_name, Priority_id = pr.Priority_id, Priority_status  = pr.Priority_status))
			for cat in categories:
				categories_output.append(dict(Category_name = cat.Category_name, Category_id = cat.Category_id, Category_status  = cat.Category_status))
	
			output.append(dict(UserID = inputdata.UID, FirstName = inputdata.Fname, LastName = inputdata.Lname,Email = inputdata.Email))
			message = 'Correct credentials!!'
			status_code = 200
		else:
			message = 'Incorrect credentials!!'
			status_code = 400
	return jsonify({'message':message,'status_code':status_code,'result' : output, 'categories':categories_output,'priorities':priorities_output})


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
        #token 		= request.json['token']#mobile app will send -1 
        status 		= request.json['status']#mobile app will send 0 i.e. deactivated
        category 	= request.json['category']#mobile app will send category of end user and CMS will send accordingly
        
        output = []

        # Check if new user details are already present in the database
        checkUser = Users.query.filter_by(Email = email).first()
                
        #print 'First Name:',checkUser.Fname
        #print 'Email:', checkUser.Email
        if checkUser:
        	if email == checkUser.Email:
        		message = 'User already registered.'
        		status_code = 400
        elif email and password and firstname and lastname: #IFF all the mandatory values are present then register the candidate
        	new_user = Users(Fname = firstname, Lname = lastname, Email = email, Password = password, user_type = user_type,  Status = status, Category = category)
        	db.session.add(new_user)
        	db.session.commit()
        	output.append(dict(Ufname = firstname))
        	message = 'Correct credentials!!'
        	status_code = 200
        else:
        	message = 'User not registered, please try again.'
        	status_code = 400

	return jsonify({'result' : output,'message':message, 'status_code':status_code})

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

        output = []

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
        			 output.append(dict(Fname = firstname))
        			 message = 'User successfully updated.'
        			 status_code = 200

        else:
        	message = 'User not found'
        	status_code = 400

	return jsonify({'result' : output, 'message':message, 'status_code':status_code })


@app.route('/api/get_user_list/<int:page>', methods = ['GET','POST'])
def getUsersList(page):
	print 'TEST: Inside getUsersList Route.'
	print page
	per_page = 20
	error = None
	if request.method == 'GET':
		print 'TEST: Inside GET block for getUsersList.'
		inputdata = 	Users.query.paginate(1, per_page, False).items

		output    = 	[]
		status = ''
		message = 'Users List sent successfully!!!'
		status_code = 200
		if inputdata:
			for user in inputdata:
				print user.UID
				output.append(dict(user_id = user.UID, user_fname = user.Fname, user_lname = user.Lname, user_email = user.Email,  user_status = user.Status,  user_type = user.user_type, user_category = user.Category))
		else:
			message = 'No user found'
			status_code = 400
		return jsonify({'result' : output, 'message':message,'status_code':status_code})
    	else:
        	output = []
        	status = ''
        	message = 'Users list sent successfully!!!'
        	status_code = 200
        	if request.method == 'POST':
	            	print 'TEST: Inside POST block for getUsersList'
        	   	user_type = request.json['user_type']
            		inputdata = Users.query.filter_by(user_type = user_type).paginate(1, per_page, False).items
            		if inputdata:
                		for user in inputdata:
                    			output.append(dict(user_id = user.UID, user_fname = user.Fname, user_lname = user.Lname, user_email = user.Email,  user_status = user.Status,  user_type = user.user_type, user_category = user.Category))
           		else:
                		message = 'No users found'
                		status_code = 400
		return jsonify({'result': output, 'message':message, 'status_code':status_code})


# Register Route
@app.route('/api/get_user/<int:user_id>', methods = ['GET'])
def getUser(user_id):
	print 'TEST: Inside getUsers Route.'
	error = None
	if request.method == 'GET':
		print 'TEST: Inside GET block for getUser.'
		inputdata = 	Users.query.filter_by(UID = user_id).first()
		output    = 	[]
		status 	  = 	''
		message   = 	'User details sent successfully!!!'
		status_code = 200
		if inputdata:
				output.append(dict(user_id = inputdata.UID, user_fname = inputdata.Fname, user_lname = inputdata.Lname, user_email = inputdata.Email,  user_status = inputdata.Status,  user_type = inputdata.user_type, user_category = inputdata.Category))
		else:
			message = 'No user found'
			status_code = 400
		return jsonify({'result' : output, 'message':message,'status_code':status_code})


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

                if checkPriority and checkPriority.Priority_name:
                        output.append(dict(Msg = 'Priority already present.'))
                elif priority_name and priority_status:
                        new_priority = Priority(Priority_name = priority_name, Priority_status = priority_status)
                        db.session.add(new_priority)
                        db.session.commit()
                        output.append(dict(Msg = 'Priority added successfully.'))
                        status_code = 200
                else:
                        status_code = 400
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
                        status_code = 200

                else:
                        status_code = 400
                        output.append(dict(Msg = 'Priority not present.'))

        return jsonify({'result' : output, 'status_code':status_code })

# Add / Update Category route
@app.route('/api/category', methods = ['POST', 'PUT'])
def add_update_Category():

        print 'TEST: Inside Category Route.'
        error = None
        if request.method == 'POST':
                print 'TEST: Inside POST block for Add Category.'
                category_name = request.json['category_name']
                category_status = request.json['category_status']

                output = []

                checkCategory = Category.query.filter_by(Category_name = category_name).first()

                if checkCategory and checkPriority.Category_name:
                        output.append(dict(Msg = 'Category already present.'))
                elif category_name and category_status:
                        new_category = Category(Category_name = category_name, Category_status = category_status)
                        db.session.add(new_category)
                        db.session.commit()
                        output.append(dict(Msg = 'Category added successfully.'))
                        status_code = 200
                else:
                        status_code = 400
                        output.append(dict(Msg = 'Category not added, please try again.'))

        if request.method == 'PUT':
                print 'TEST: Inside PUT block for Update Category.'
                category_id = request.json['category_id']
                category_name = request.json['category_name']
                category_status = request.json['category_status']

                output = []
                updateThisCategory = Category.query.filter_by(Category_id = category_id).first()

                if updateThisCategory:
                        updateThisCategory.Category_name = category_name
                        updateThisCategory.Category_status = category_status
                        db.session.commit()
                        output.append(dict(Msg = 'Category updated successfully.'))
                        status_code = 200
                else:
                        output.append(dict(Msg = 'Category not present.'))
                        status_code = 400

        return jsonify({'result' : output, 'status_code' : status_code})


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1] in ALLOWED_EXTENSIONS

# Post Issue route
@app.route('/api/post_issue', methods=['POST'])
def upload_image():
    output = "hello error"
    try:
        print 'Inside POST ISSUE Block.'
        if request.method == 'POST':
            # check if the post request has the file part
            if 'file' not in request.files:
                print 'No file part'
            image = request.files['file']
            # if user does not select file, browser also
            # submit a empty part without filename
            if image.filename == '':
                print 'No selected file'
            if image and allowed_file(image.filename.lower()):
                filename = secure_filename(image.filename)
                image.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
                output = 'Executed'
    except Exception as error:
        output = str(error)
    return output

# LOGS
@app.route('/api/logs', methods = ['GET'])
def log():
	path_error_log = os.path.realpath('/var/log/nginx/error.log')
	dir_path = os.path.dirname(path_error_log)
	print 'Error Log path:', dir_path
	with open('/var/log/nginx/error.log') as f:
		file_content = f.read()
	return file_content

@app.route('/api/get_notification/<int:user_id>', methods = ['GET'])
def get_notification(user_id):
	print 'TEST: Inside get_notification Route.'
	error = None
	if request.method == 'GET':
		print 'TEST: Inside GET block for get_notification.'
		output    = 	[]
		status = ''
		
		notification = Notification.query.filter_by(User_id = user_id).first()
		message = 'Notifications sent successfully!!!'
		status_code = 200
		if notification:
				output.append(dict(User_id = notification.User_id,Notification_id = notification.Notification_id, Notification_title = notification.Notification_title,Notification_description = notification.Notification_description))
		else:
			message = 'No Notifications found'
			status_code = 400
		return jsonify({'notification' : output, 'message':message,'status_code':status_code})

@app.route('/api/delete_notification',methods = ['DELETE'])
def delete_notification():
	print 'TEST: Inside delete_notification Route.'
	error = None
	print 'TEST: Inside  block for delete_notification.'
	notification_ids = request.json['notification id']

	for notification_id in notification_ids:
		notification = Notification.query.filter_by(Notification_id=notification_id)
		if notification:
			notification.delete()
		else:
			message = 'Notification(s) does not exist'
			status_code = 400
	db.session.commit()
	
	return jsonify({'message':message,'status_code':status_code})


def random_with_N_digits(n):
    range_start = 10**(n-1)
    range_end = (10**n)-1
    return randint(range_start, range_end)


@app.route('/api/forgot_password', methods = ['POST'])
def forgot_password():
	print 'TEST: Inside forgot_password Route.'
	error = None
	if request.method == 'POST':
		print 'TEST: Inside POST block for forgot_password.'
		print request
		email 		= request.json['email']
		print email
		checkUser = Users.query.filter_by(Email = email).first()
		if checkUser:
			if email == checkUser.Email:
				message = 'User Available'
				random_number = random_with_N_digits ( 10 )
				number = str(random_number)
				checkUser.Token = number
				db.session.commit()
				message = 'User token successfully updated and email sent to user'
				status_code = 200
				url = 'http://ec2-52-37-224-72.us-west-2.compute.amazonaws.com/api/reset_password/'+number
				email_body = "Click below link to reset your password.\n\n\n" + url
				connection = boto.ses.connect_to_region('us-west-2', aws_access_key_id='AKIAJN4MYUQTGOXF2TTA',
                                                    aws_secret_access_key='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')
				connection.send_email ('akshya672222@gmail.com' , 'Reset Password' , email_body ,[ email ] )
		else:
			message = 'User does not exist'
			status_code = 400

		return jsonify({'message':message,'status_code':status_code})


@app.route('/api/reset_password/<int:user_id>/<string:token>', methods = ['POST'])
def reset_password(user_id,token):
	print 'TEST: Inside reset_password Route.'
	error = None
	if request.method == 'POST':
		print 'TEST: Inside POST block for reset_password.'
		password = request.json['password']
		checkUser = Users.query.filter_by(UID = user_id).first()
		if checkUser:
			if token == checkUser.Token:
				checkUser.Password = password
				db.session.commit()
				message = 'User password reset successful.'
				status_code = 200
			else:
				message = 'Token did not match.'
				status_code = 400

		else:
			message = 'User does not exist'
			status_code = 400

		return jsonify({'message':message,'status_code':status_code})

