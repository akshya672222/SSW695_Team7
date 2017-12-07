from flask import Flask, render_template, request, url_for, redirect, flash, session
from flask_googlemaps import GoogleMaps
from wtforms import Form, BooleanField, TextField, PasswordField, validators
from flask_principal import Principal, Permission, RoleNeed
import sqlite3 as sql 
from functools import wraps
from passlib.hash import sha256_crypt
import time
import datetime
import os
import gc
import json
try:
    import urllib.request as urllib2
except ImportError:
    import urllib2


app = Flask(__name__)
app.config['SECRET_KEY'] = os.getenv('SECRET_KEY') or \
    'e5ac358c-f0bf-11e5-9e39-d3b532c10a28'
app.config['GOOGLEMAPS_KEY'] = "AIzaSyDHyTgvQ2NzRpUubu_WyG7q5HMKYoL6wbc"


api_url = 'http://ec2-34-207-75-73.compute-1.amazonaws.com/api/'
GoogleMaps(app)
#GoogleMaps(app, key="8JZ7i18MjFuM35dJHq70n3Hx4")


def connection():
	try:
		con = sql.connect("Reportal.db")
		con.row_factory = sql.Row
		cur = con.cursor()
		return con, cur
	except Exception as e:
		return "leeesh"

# handling errors
@app.errorhandler(404)
def page_not_found(e):
    return render_template("404.html")

@app.errorhandler(500)
def method_not_found(e):
    return render_template("500.html")

#login_required
def login_required(f):
    @wraps(f)
    def wrap(*args, **kwargs):
        print(session['user_type'])
        if ('logged_in' in session) and session['user_type'] == 3  :
            return f(*args, **kwargs)
        else:
            
            return redirect(url_for('no_permission'))
    return wrap



#login
@app.route('/',methods=["GET","POST"])
def login():
    """verify user credintials and redirects to dashboard"""
    if request.method == "POST":
        
        url_get_people_list = api_url + 'login'
        payload = {'email': request.form['email'], 'password': request.form['password'], "user_type":3} #sha256_crypt.hash(request.form['password'])
        json_data = json.dumps(payload).encode('utf8') #encoding variables to utf8
        url_req = urllib2.Request(url_get_people_list, headers={ 'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'} , method='POST', data=json_data)
        
        #call api 
        response = urllib2.urlopen(url_req).read().decode('utf8') 
        response_json = json.loads(response) #convert string to dictionary
        print(response_json)
        if response_json['status_code'] == 200 and response_json['result'][0]['UserType'] != 1 : #if response is successful
            session['logged_in'] = True
            session['name'] = str(response_json['result'][0]['FirstName']) + ' ' + str(response_json['result'][0]['LastName']) 
            session['id'] = response_json['result'][0]['UserID']
            session['user_type'] = response_json['result'][0]['UserType']
            print(session['user_type'])
            if response_json['result'][0]['UserType'] == 3:
                return redirect(url_for("dashboard"))
            elif response_json['result'][0]['UserType'] == 2:
                return redirect(url_for("dashboard_maintenance"))

        else:
            error = "Invalid Credintials"
            flash(error)
    return render_template("login.html")

#logout
@app.route("/logout/")
@login_required
def logout():
    session.clear()
    flash("You have been logged out!")
    return redirect(url_for('login'))

#forgot password
@app.route('/forgotPassword/')
def resetPassword():
    url_get_people_list = api_url + 'reset_password'
    url_req = urllib2.Request(url_get_people_list, headers={ 'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='POST')
    response = urllib2.urlopen(url_req).read().decode('utf8')
    response_json = json.loads(response)
    if response_json['status_code'] == 200:
        return render_template("login.html")  
    else:
        flash("Please log in with your new password")
        return redirect(url_for('login'))

#display profile
@app.route('/profile/')
@login_required
def profile():
    #Fetch admin info from database 
    url_get_people_list = api_url + 'get_user/' + str(session['id'])
    url_req = urllib2.Request(url_get_people_list, headers={ 'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='GET')
    response = urllib2.urlopen(url_req).read().decode('utf8')
    response_json = json.loads(response)
    if response_json['status_code'] == 200:
        return render_template("profile.html", rows = response_json['result'])  
    else:
        flash(message)
        return redirect(url_for('profile'))

#Update profile
@app.route('/updateProfile/',methods=["POST"])
@login_required
def updateProfile():
    try:
        if request.method == "POST":
            url_get_people_list = api_url + 'update_user_settings'
            payload = {'firstname': request.form['fname'], 'lastname': request.form['lname'], 'password': request.form['password'], 'user_id': session['id'], 'email': request.form['email']}
            json_data = json.dumps(payload).encode('utf8')
            url_req = urllib2.Request(url_get_people_list, headers={'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='POST', data=json_data)
            response = urllib2.urlopen(url_req).read().decode('utf8')
            response_json = json.loads(response)
            message = ''
            if response_json['status_code'] == 200:
                message = 'Profile Updated successfuly!'
            else:
                message = response_json['message']
            flash(message)
            return redirect(url_for('profile'))
        
    except Exception as e:
        return render_template("404.html")  
    return redirect(url_for('profile'))


#display users
@app.route('/users/')
@login_required
def users():
    #Fetch admin from database -> Fetch admin list from server
    page_number = 1
    payload = {"user_type": 1}
    json_data = json.dumps(payload).encode('utf8')
    url_get_people_list = api_url + 'get_user_list/' + str(page_number) 
    url_req = urllib2.Request(url_get_people_list, headers={ 'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='POST', data=json_data)
    response = urllib2.urlopen(url_req).read().decode('utf8')
    response_json = json.loads(response)
    if response_json['status_code'] == 200:
        return render_template("users.html", rows = response_json['result'])  
    else:
        flash(message)
        return redirect(url_for('users'))

@app.route('/addUser/',methods=["POST"])
@login_required
def addUser():
    try:
        if request.method == "POST":
            url_get_people_list = api_url + 'register'
            payload = {
                'email': request.form['email'],
                'password': request.form['password'], #sha256_crypt.hash(request.form['password']),
                'firstname': request.form['fname'],
                'lastname': request.form['lname'],
                'category': 0,
                'type': 1,
                'status':0,
                'token':0
            }
            json_data = json.dumps(payload).encode('utf8')
            url_req = urllib2.Request(url_get_people_list, headers={
                'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='POST', data=json_data)
            response = urllib2.urlopen(url_req).read().decode('utf8')
            response_json = json.loads(response)
            message = ''
            if response_json['status_code'] == 200:
            	message = 'User added successfuly!'
            else:
            	message = response_json['message']
            flash(message)
            return redirect(url_for('users'))
    except Exception as e:
        return render_template("404.html")  
    return redirect(url_for('users'))

@app.route('/updateUser/',methods=["POST"])
@login_required
def updateUser():
    try:
        if request.method == "POST":
            url_get_people_list = api_url + 'update_user_settings'
            payload = {'email': request.form['email'], 'firstname': request.form['fname'], 'lastname': request.form['lname']}
            json_data = json.dumps(payload).encode('utf8')
            url_req = urllib2.Request(url_get_people_list, headers={'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='POST', data=json_data)
            response = urllib2.urlopen(url_req).read().decode('utf8')
            response_json = json.loads(response)
            message = ''
            if response_json['status_code'] == 200:
                message = 'User Updated successfuly!'
            else:
                message = response_json['message']
            flash(message)
            return redirect(url_for('users'))
        
    except Exception as e:
        return render_template("404.html")  
    return redirect(url_for('users'))


#display admins
@app.route('/admin/',methods=["GET"])
@login_required
def admin():
    #Fetch admin from database -> Fetch admin list from server
    page_number = 1
    payload = {"user_type": 3}
    json_data = json.dumps(payload).encode('utf8')
    url_get_people_list = api_url + 'get_user_list/' + str(page_number) 
    url_req = urllib2.Request(url_get_people_list, headers={ 'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='POST', data=json_data)
    response = urllib2.urlopen(url_req).read().decode('utf8')
    response_json = json.loads(response)
    if response_json['status_code'] == 200:
        return render_template("admin.html", rows = response_json['result'])  
    else:
        flash(message)
        return redirect(url_for('admin'))

@app.route('/addAdmin/',methods=["POST"])
@login_required
def addAdmin():
    try:
        if request.method == "POST":
            url_get_people_list = api_url + 'register'
            payload = {
                'email': request.form['email'],
                'password': request.form['password'], #sha256_crypt.hash(request.form['password']),
                'firstname': request.form['fname'],
                'lastname': request.form['lname'],
                'category': 0,
                'type': 3,
                'status':0,
                'token':0
            }
            json_data = json.dumps(payload).encode('utf8')
            url_req = urllib2.Request(url_get_people_list, headers={
                'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='POST', data=json_data)
            response = urllib2.urlopen(url_req).read().decode('utf8')
            response_json = json.loads(response)
            message = ''
            if response_json['status_code'] == 200:
            	message = 'Admin added successfuly!'
            else:
            	message = response_json['message']
            flash(message)
            return redirect(url_for('admin'))
    except Exception as e:
        return render_template("404.html")  
    return redirect(url_for('admin'))
    


#display events
@app.route('/issues/')
@login_required
def issues():
    url_get_people_list = api_url + 'get_issue' 
    url_req = urllib2.Request(url_get_people_list, headers={ 'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='GET')
    response = urllib2.urlopen(url_req).read().decode('utf8')
    response_json = json.loads(response)
    if response_json['status_code'] == 200:
        print(response_json['issues'])
        return render_template("issues.html", rows = response_json['issues'])  
    else:
        flash(message)
        return redirect(url_for('issues'))

@app.route('/updateIssue/',methods=["POST"])
@login_required
def updateIssue():
    con, cur = connection()
    try:
            cur.execute("UPDATE Issues SET priority = ? , description = ? , location = ?, status = ? WHERE IssID = ?", 
            (request.form['priority'],request.form['description'], request.form['location'],request.form['status'], request.form['id']))
            con.commit()
            flash("Updated Successfuly!")
            return redirect(url_for('issues'))

    except Exception as e:
        return render_template("404.html")  
    return redirect(url_for('issues'))


#display categories
@app.route('/category/')
@login_required
def category():
     #Fetch category from server using API
    url_get_people_list = api_url + 'get_categories'
    url_req = urllib2.Request(url_get_people_list, headers={ 'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='GET')
    response = urllib2.urlopen(url_req).read().decode('utf8')
    response_json = json.loads(response)
    if response_json['status_code'] == 200:
        return render_template("category.html", rows = response_json['categories'])  
    else:
        flash(message)
        return redirect(url_for('category'))


#display categories
@app.route('/addCategory/',methods=["POST"])
@login_required
def addCategory():
    try:
        if request.method == "POST":
            url_get_people_list = api_url + 'category'
            payload = {
                'category_name': request.form['category'],
                'category_status': 1

            }
            json_data = json.dumps(payload).encode('utf8')
            url_req = urllib2.Request(url_get_people_list, headers={
                'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='POST', data=json_data)
            response = urllib2.urlopen(url_req).read().decode('utf8')
            response_json = json.loads(response)
            message = ''
            if response_json['status_code'] == 200:
            	message = 'Category added successfuly!'
            else:
            	message = response_json['message']
            flash(message)
            return redirect(url_for('category'))
    except Exception as e:
        return render_template("404.html")  
    return redirect(url_for('category'))

#display categories
@app.route('/updateCategory/',methods=["POST"])
@login_required
def updateCategory():

    try:
        if request.method == "POST":
            url_get_people_list = api_url + 'category'
            payload = {
                'category_id': request.form['id'],
                'category_name': request.form['category'],
                'category_status': 1
            }
            json_data = json.dumps(payload).encode('utf8')
            url_req = urllib2.Request(url_get_people_list, headers={
                'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method="PUT", data=json_data)
            response = urllib2.urlopen(url_req).read().decode('utf8')
            response_json = json.loads(response)
            message = ''
            if response_json['status_code'] == 200:
            	message = response_json['message']
            else:
            	message = response_json['message']
            flash(message)
            return redirect(url_for('category'))
    except Exception as e:
        print('exception = ', e)
        return render_template("404.html")  
    return redirect(url_for('category'))

#display categories
@app.route('/priority/')
@login_required
def priority():
    #Fetch priority from server using API
    url_get_people_list = api_url + 'get_priorities'
    print(url_get_people_list)
    url_req = urllib2.Request(url_get_people_list, headers={ 'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='GET')
    response = urllib2.urlopen(url_req).read().decode('utf8')
    response_json = json.loads(response)
    if response_json['status_code'] == 200:
        return render_template("priority.html", rows = response_json['priorities'])  
    else:
        flash(message)
        return redirect(url_for('priority'))

#display categories
@app.route('/addPriority/',methods=["POST"])
@login_required
def addPriority():
    try:
        if request.method == "POST":
            url_get_people_list = api_url + 'priority'
            payload = {
                'priority_name': request.form['priority'],
                'priority_status': 1

            }
            json_data = json.dumps(payload).encode('utf8')
            url_req = urllib2.Request(url_get_people_list, headers={
                'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='POST', data=json_data)
            response = urllib2.urlopen(url_req).read().decode('utf8')
            response_json = json.loads(response)
            message = ''
            if response_json['status_code'] == 200:
            	message = 'Priority added successfuly!'
            else:
            	message = response_json['message']
            flash(message)
            return redirect(url_for('priority'))
    except Exception as e:
        print('exception = ', e)
        return render_template("404.html")  
    return redirect(url_for('priority'))


#display categories
@app.route('/updatePriority/',methods=["POST"])
@login_required
def updatePriority():

    try:
        if request.method == "POST":
            url_get_people_list = api_url + 'priority'
            try:
                payload = {
                    'priority_id': request.form['id'],
                    'priority_name': request.form['priority'],
                    'priority_status': 1
                }
            except Exception as e: 
                print('e:',e)

            print(payload)
            json_data = json.dumps(payload).encode('utf8')
            url_req = urllib2.Request(url_get_people_list, headers={
                'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method="PUT", data=json_data)
            response = urllib2.urlopen(url_req).read().decode('utf8')
            response_json = json.loads(response)
            message = ''
            if response_json['status_code'] == 200:
            	message = "response_json['message']"
            else:
            	message = response_json['message']
            flash(message)
            return redirect(url_for('priority'))
    except Exception as e:
        return render_template("404.html")  
    return redirect(url_for('priority'))


@app.route('/maintenance/',methods=["GET"])
@login_required
def maintenance():
    #Fetch maintenance list using the api from server
    page_number = 1
    payload = {"user_type": 2}
    json_data = json.dumps(payload).encode('utf8')
    url_get_people_list = api_url + 'get_user_list/' + str(page_number) 
    url_req = urllib2.Request(url_get_people_list, headers={ 'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='POST', data=json_data)
    response = urllib2.urlopen(url_req).read().decode('utf8')
    response_json = json.loads(response)
    if response_json['status_code'] == 200:
        return render_template("maintenance.html", rows = response_json['result'])  
    else:
        flash(message)
        return redirect(url_for('maintenance'))


@app.route('/dashboard/')
@login_required
def dashboard():
    return render_template("dashboard.html")

@app.route('/forgotPassword/')
def forgotPassword():
    return render_template("forgotPassword.html")


@app.route('/blank-page/')
def blankpage():
    return render_template("blank-page.html")


@app.route('/dashboard_main/')
def dashboard_main():
    return redirect(url_for('dashboard_maintenance'))


@app.route('/dashboard_maintenance/')
def dashboard_maintenance():
    url_get_people_list = api_url + 'get_issue_list/' + str(session['id'])
    print(url_get_people_list )
    url_req = urllib2.Request(url_get_people_list, headers={ 'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='GET')
    response = urllib2.urlopen(url_req).read().decode('utf8')
    response_json = json.loads(response)
    if response_json['status_code'] == 200:
        print(response_json['issues'])
        return render_template("dashboard_maintenance.html", rows = response_json['issues'])  
    else:
        messgae = response_json['message']
        flash(message)
        return redirect(url_for('dashboard_maintenance'))



@app.route('/no_permission/')
def no_permission():
    return render_template("no_permission.html")

#display TimeStamp
@app.template_filter('ctime')
def timectime(s):
    unix_timestamp = float(s)
    return datetime.datetime.fromtimestamp(unix_timestamp)

if __name__ == "__main__":
    #app.secret_key = '39ie94884ur4yr75yr57py'
    app.run()