from flask import Flask, render_template, request, url_for, redirect, flash, session
from wtforms import Form, BooleanField, TextField, PasswordField, validators
import sqlite3 as sql 
from functools import wraps
from passlib.hash import sha256_crypt
import time
import datetime
import os
import gc


app = Flask(__name__)
app.config['SECRET_KEY'] = os.getenv('SECRET_KEY') or \
    'e5ac358c-f0bf-11e5-9e39-d3b532c10a28'

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
            print('email: ', request.form['email'], ' password: ', request.form['password'])
            if request.form['email'] == 'admin@admin.com' and request.form['password'] == 'password':
                session['logged_in'] = True
                session['name'] = 'ADMIN'
                return redirect(url_for('dashboard'))
            
            data = cur.execute("SELECT * FROM Users WHERE user_email = '%s'" % request.form['email'])

            row = cur.fetchall()

            if sha256_crypt.verify(request.form['password'], row[0][2]) :
                session['logged_in'] = True
                session['name'] = row[0][3]

                #flash("You are now logged in")
                return redirect(url_for("dashboard"))

            else:
                error = "Invalid credentials, try again."

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
    con, cur = connection()
    cur.execute("select * from Users")
    rows = cur.fetchall()
    return render_template("admin.html", rows = rows)  

@app.route('/addAdmin/',methods=["POST"])
@login_required
def addAdmin():
    con, cur = connection()
    try:
        if request.method == "POST":
            cur.execute("INSERT INTO Admin (Aemail, Apassword, Afname,Alname) VALUES (?,?,?,?)", (request.form['email'],request.form['password'],request.form['fname'],request.form['lname']))
            con.commit()
            flash("Added Successfuly!")
            return redirect(url_for('admin'))

    except Exception as e:
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


