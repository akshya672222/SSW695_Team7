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

class Priority(db.Model):
	__tablename__ = 'Priority'
        __table_args__ = {'extend_existing': True}
	Priority_id 		= db.Column('priority_id', db.Integer, primary_key = True)
	Priority_name 		= db.Column('priority_name', db.Unicode)
	Priority_status 	= db.Column('priority_status', db.Unicode)

class Category(db.Model):
	_tablename_ = 'Category'
        __table_args__ = {'extend_existing': True}

	Category_id 			= db.Column('category_id', db.Integer, primary_key = True)
	Category_name 			= db.Column('category_name', db.Unicode)
	Category_status 		= db.Column('category_status', db.Unicode)


class Notification(db.Model):
	_tablename_ = 'Notification'
	__table_args__ = {'extend_existing': True} 

	User_id                     = db.Column('user_id', db.Integer)
	Notification_id 			= db.Column('notification_id', db.Integer, primary_key = True)
	Notification_title 			= db.Column('notification_title', db.Unicode)
	Notification_description 		= db.Column('notification_description', db.Unicode)
