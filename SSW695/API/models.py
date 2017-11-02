from reportal import db

class Users(db.Model):
	__tablename__ = 'Users'
	__table_args__ = {'extend_existing': True} 

	UID      = 	db.Column('user_id', db.Integer, primary_key = True)
	Fname    = 	db.Column('user_fname', db.Unicode)
	Lname    =  db.Column('user_lname', db.Unicode)
	Email    = 	db.Column('user_email', db.Unicode)
	Password = 	db.Column('user_password', db.Unicode)
	Status   =  db.Column('user_status', db.Unicode)
	Token   =  db.Column('user_token', db.Unicode)
	user_type	 =  db.Column('user_type', db.Unicode)
	Category	 =  db.Column('user_category', db.Unicode)