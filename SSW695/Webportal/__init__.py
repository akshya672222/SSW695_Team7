from flask import Flask, render_template, request, url_for, redirect, flash, session
from wtforms import Form, BooleanField, TextField, PasswordField, validators
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

api_url = 'http://ec2-52-37-224-72.us-west-2.compute.amazonaws.com/api/'

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
        if 'logged_in' in session:
            return f(*args, **kwargs)
        else:
            flash("You need to login first")
            return redirect(url_for('login'))
    return wrap

#login
@app.route('/',methods=["GET","POST"])
def login():
    error = ''
    try:
        con, cur = connection()
        if request.method == "POST":
        	#Check for super admin
            if request.form['email'] == 'admin@admin.com' and request.form['password'] == 'password':
                session['logged_in'] = True
                session['name'] = 'ADMIN'
                return redirect(url_for('dashboard'))
            
            #Check for admin other than super admin
            url_get_people_list = api_url + 'login'
            payload = {
                'email': request.form['email'],
                'password': request.form['password'] #sha256_crypt.hash(request.form['password'])
            }
            json_data = json.dumps(payload).encode('utf8')
            url_req = urllib2.Request(url_get_people_list, headers={
                                      'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='POST', data=json_data)
            response = urllib2.urlopen(url_req).read().decode('utf8')
            response_json = json.loads(response)
            if response_json['status_code'] == 200:
            	session['logged_in'] = True
            	session['name'] = str(response_json['result'][0]['FirstName']) + ' ' + str(response_json['result'][0]['LastName']) 
            	return redirect(url_for("dashboard"))
            error = str(response_json['message'])
        return render_template("login.html", error=error)
    except Exception as e:
        #flash(e)
        error = "Invalid credentials, try again."
        return render_template("login.html", error = e)  

#logout
@app.route("/logout/")
@login_required
def logout():
    session.clear()
    flash("You have been logged out!")
    return redirect(url_for('login'))

#display users
@app.route('/users/')
@login_required
def users():
	con, cur = connection()
	cur.execute("select * from Users")
	rows = cur.fetchall()
	return render_template("users.html",rows = rows)

#display events
@app.route('/issues/')
@login_required
def issues():
	con, cur = connection()
	cur.execute("select * from Issues")
	rows = cur.fetchall()
	return render_template("issues.html",rows = rows)

#display admins
@app.route('/admin/',methods=["GET"])
@login_required
def admin():
    #Fetch admin from database -> Fetch admin list from server
    con, cur = connection()
    cur.execute("select * from Users where user_type = 3")
    rows = cur.fetchall()

    page_number = 1
    url_get_people_list = api_url + 'get_user_list/' + str(page_number)
    url_req = urllib2.Request(url_get_people_list, headers={
                                      'User-Agent': 'Safari/537.36', 'Content-Type': 'application/json'}, method='GET')
    response = urllib2.urlopen(url_req).read().decode('utf8')
    response_json = json.loads(response)
    if response_json['status_code'] == 200:
    	print(response_json['result'])
    else:
    	print(response_json['message'])
    return render_template("admin.html", rows = rows)  

@app.route('/addAdmin/',methods=["POST"])
@login_required
def addAdmin():
    con, cur = connection()
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
        print('exception = ', e)
        return render_template("404.html")  
    return redirect(url_for('admin'))
    

#display categories
@app.route('/category/')
@login_required
def category():
	con, cur = connection()
	cur.execute("select * from Category")   
	rows = cur.fetchall()
	return render_template("category.html",rows = rows)

#display TimeStamp
@app.route('/maintenance/')
@login_required
def maintenance():
    con, cur = connection()
    cur.execute("select * from Maintenance")   
    rows = cur.fetchall()
    return render_template("maintenance.html",rows = rows)

@app.route('/dashboard/')
@login_required
def dashboard():
    return render_template("dashboard.html")

@app.route('/registerm/', methods=["GET","POST"])
def register_pagew():
    try:
        con,cur = connection()
        return("okay")
    except Exception as e:
        return(str(e))

@app.route('/blank-page/')
@login_required
def blankpage():
    return render_template("blank-page.html")

@app.route('/h/')
def hpage():
    return render_template("tables.html")	

@app.template_filter('ctime')
def timectime(s):
    unix_timestamp = float(s)
    return datetime.datetime.fromtimestamp(unix_timestamp)

if __name__ == "__main__":
    #app.secret_key = '39ie94884ur4yr75yr57py'
    app.run()


