from reportal import db

class Users(db.Model):
	__tablename__ = 'Users'

	UID      = 	db.Column('UID', db.Integer, primary_key = True)
	Fname    = 	db.Column('Ufname', db.Unicode)
	Lname    =  db.Column('Ulname', db.Unicode)
	Email    = 	db.Column('Uemail', db.Unicode)
	Password = 	db.Column('Upassword', db.Unicode)
	Status   =  db.Column('status', db.Unicode)