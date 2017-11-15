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

	Priority_id 		= db.Column('priority_id', db.Integer, primary_key = True)
	Priority_name 		= db.Column('priority_name', db.Unicode)
	Priority_status 	= db.Column('priority_status', db.Integer)

class Category(db.Model):
	__tablename__ = 'Category'

	Category_id 			= db.Column('category_id', db.Integer, primary_key = True)
	Category_name 			= db.Column('category_name', db.Unicode)
	Category_status 		= db.Column('category_status', db.Integer)

class Issues(db.Model):
	__tablename__ = 'Issues'

	Issue_id		= db.Column('issue_id', db.Integer, primary_key = True)
	Iuid 			= db.Column('user_id', db.Integer)
	Icategory_id	= db.Column('issue_category_id', db.Integer)
	Istatus_id		= db.Column('status_id', db.Integer)
	Ipicpath		= db.Column('issue_picpath', db.Unicode)
	Ipriority		= db.Column('issue_priority_id', db.Integer)
	Idescription	= db.Column('issue_description', db.Integer)
	Itime			= db.Column('issue_time', db.Unicode)
	Ilat			= db.Column('issue_lat', db.Float)
	Ilon			= db.Column('issue_lat', db.Float)
	IassignedTo		= db.Column('issue_assignedTo', db.Integer)