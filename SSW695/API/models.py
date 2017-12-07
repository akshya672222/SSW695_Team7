from reportal import db

class Users(db.Model):
<<<<<<< Updated upstream
    __tablename__ = 'Users'
    __table_args__ = {'extend_existing': True}
    UID      =  db.Column('user_id', db.Integer, primary_key = True)
    Fname    =  db.Column('user_fname', db.Unicode)
    Lname    =  db.Column('user_lname', db.Unicode)
    Email    =  db.Column('user_email', db.Unicode)
    Password =  db.Column('user_password', db.Unicode)
    Status   =  db.Column('user_status', db.Integer)
    Token    =  db.Column('user_token', db.Unicode)
    user_type    =  db.Column('user_type', db.Integer)
    Category     =  db.Column('user_category', db.Integer)
=======
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
>>>>>>> Stashed changes

    
class Priority(db.Model):
    __tablename__ = 'Priority'
    __table_args__ = {'extend_existing': True}
    Priority_id         = db.Column('priority_id', db.Integer, primary_key = True)
    Priority_name       = db.Column('priority_name', db.Unicode)
    Priority_status     = db.Column('priority_status', db.Integer)

class Category(db.Model):
    _tablename_ = 'Category'
    __table_args__ = {'extend_existing': True}

    Category_id             = db.Column('category_id', db.Integer, primary_key = True)
    Category_name           = db.Column('category_name', db.Unicode)
    Category_status         = db.Column('category_status', db.Integer)


class Notification(db.Model):
    _tablename_ = 'Notification'
    __table_args__ = {'extend_existing': True}
    User_id                     = db.Column('user_id', db.Integer)
    Notification_id             = db.Column('notification_id', db.Integer, primary_key = True)
    Notification_title          = db.Column('notification_title', db.Unicode)
    Notification_description        = db.Column('notification_description', db.Unicode)

<<<<<<< Updated upstream
class Issues(db.Model):
    __tablename__ = 'Issues'
    __table_args__ = {'extend_existing': True} 

    Issue_id        = db.Column('issue_id', db.Integer, primary_key = True)
    Iuid            = db.Column('user_id', db.Integer)
    Icategory_id    = db.Column('issue_category_id', db.Integer) # Change this
    Istatus_id      = db.Column('status_id', db.Integer)
    Ipicpath        = db.Column('issue_picpath', db.Unicode)
    Ipriority       = db.Column('issue_priority_id', db.Integer) # Change this
    Idescription    = db.Column('issue_description', db.Integer)
    Itime           = db.Column('issue_time', db.Unicode)
    Ilat            = db.Column('issue_lat', db.Float)
    Ilon            = db.Column('issue_lon', db.Float)
    IassignedTo     = db.Column('issue_assignedTo', db.Integer)

class Status(db.Model):
    __tablename__ = 'Status'
    __table_args__ = {'extend_existing': True} 

    Status_id               = db.Column('status_id', db.Integer, primary_key = True)
    SIssue_id               = db.Column('issue_id', db.Integer)
    Status                  = db.Column('status', db.Integer)
    Status_updateTime       = db.Column('status_updateTime', db.Unicode)
    Status_updatedby        = db.Column('status_updatedby', db.Integer)
=======
	User_id                     = db.Column('user_id', db.Integer)
	Notification_id 			= db.Column('notification_id', db.Integer, primary_key = True)
	Notification_title 			= db.Column('notification_title', db.Unicode)
	Notification_description 		= db.Column('notification_description', db.Unicode)

class Issues(db.Model):
	__tablename__ = 'Issues'
	__table_args__ = {'extend_existing': True} 

	Issue_id		= db.Column('issue_id', db.Integer, primary_key = True)
	Iuid 			= db.Column('user_id', db.Integer)
	Icategory_id	= db.Column('issue_category_id', db.Integer)
	Istatus_id		= db.Column('status_id', db.Integer)
	Ipicpath		= db.Column('issue_picpath', db.Unicode)
	Ipriority		= db.Column('issue_priority_id', db.Integer)
	Idescription	= db.Column('issue_description', db.Integer)
	Itime			= db.Column('issue_time', db.Unicode)
	Ilat			= db.Column('issue_lat', db.Float)
	Ilon			= db.Column('issue_lon', db.Float)
	IassignedTo		= db.Column('issue_assignedTo', db.Integer)

class Status(db.Model):
	__tablename__ = 'Status'
	__table_args__ = {'extend_existing': True} 

	Status_id   			= db.Column('status_id', db.Integer, primary_key = True)
	SIssue_id     			= db.Column('issue_id', db.Unicode)
	Status 					= db.Column('status', db.Integer)
	Status_updateTime		= db.Column('status_updateTime', db.Unicode)
	Status_updatedby		= db.Column('status_updatedby', db.Integer)
	Category_status 		= db.Column('category_status', db.Unicode)
>>>>>>> Stashed changes
