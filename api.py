from flask import * 
from database import* 
import uuid

api=Blueprint('api',__name__)

@api.route('/login')
def login():
	data={}
	u=request.args['username']
	p=request.args['password']
	q1="select * from login where username='%s' and `password`='%s'"%(u,p)
	print(q1)
	res=select(q1)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return str(data)

@api.route('/devilieryboyregistration')
def devilieryboyregistration():
	data={}
	f=request.args['fname']

	
	pl=request.args['place']
	
	ph=request.args['phone']
	e=request.args['email']
	

	u=request.args['username']
	p=request.args['password']
	la=request.args['latitude']
	lo=request.args['longitude']
	
	q="select * from login where username='%s' and password='%s'"%(u,p)
	res=select(q)
	if res:
		data['status']='already'
	else:
		q="insert into login values(NULL,'%s','%s','pending')"%(u,p)
		lid=insert(q)
		r="insert into deliveryboy values(NULL,'%s','%s','%s','%s','%s','%s','%s','available')"%(lid,f,pl,ph,e,la,lo)
		insert(r)
		data['status']="success"
	return str(data)



@api.route('/userregister')
def userregister():
	data={}
	f=request.args['fname']

	
	pl=request.args['place']
	
	ph=request.args['phone']
	e=request.args['email']
	

	u=request.args['username']
	p=request.args['password']
	la=request.args['latitude']
	lo=request.args['longitude']
	
	q="select * from login where username='%s' and password='%s'"%(u,p)
	res=select(q)
	if res:
		data['status']='already'
	else:
		q="insert into login values(NULL,'%s','%s','pending')"%(u,p)
		lid=insert(q)
		r="insert into user values(NULL,'%s','%s','%s','%s','%s','%s','%s')"%(lid,f,pl,ph,e,la,lo)
		insert(r)
		data['status']="success"
	return str(data)


@api.route('/Searchfactory')	
def Searchfactory():
	data={}
	search='%'+request.args['search']+'%'
	q="SELECT * FROM `factory` where fplace like '%s'"%(search)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
		data['method']='search'
	else:
		data['status']="failed"
		
	return str(data)


@api.route('/viewfactory')	
def viewfactory():
	data={}
	q="select * from factory "
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	return str(data)


@api.route('/viewtype')	
def viewtype():
	data={}
	fid=request.args['fid']
	q="select * from type  where factory_id='%s' "%(fid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	return str(data)



@api.route('/send_request')
def send_request():
	data={}
	l=request.args['login_id']

	
	qu=request.args['quantity']
	
	tid=request.args['tid']
	
	
	q="insert into request values(NULL,'%s','%s','%s','pending')"%(l,tid,qu)
	lid=insert(q)
	data['status']="success"
		
	return str(data)



@api.route('/addvehicle',methods=['get','post'])
def addvehicle():
	data={}
	l=request.form['lid']

	
	qu=request.form['type']
	
	tid=request.form['capacity']
	i=request.files['image']
	path="static/image/"+str(uuid.uuid4())+i.filename
	i.save(path)
	
	
	q="insert into vehicle values(NULL,(select deliveryboy_id from deliveryboy where login_id='%s'),'%s','%s','%s')"%(l,qu,tid,path)
	insert(q)
	data['status']="success"
		
	return str(data)



@api.route('/viewassign',methods=['get','post'])
def viewassign():
	data={}
	l=request.args['log_id']
	
	q="SELECT *,CONCAT(`assign`.`status`) AS ass FROM `assign` INNER JOIN `request` USING (`request_id`) INNER JOIN `user` ON `user`.login_id=`request`.`login_id`  INNER JOIN `type` USING (type_id)  WHERE `assign`.`deliveryboy_id`=(SELECT `deliveryboy_id` FROM `deliveryboy` WHERE `login_id`='%s')"%(l)
	res=select(q)
	if res:
		data['status']="success"
		
		data['data']=res
	data['method']="viewassign"
		
	return str(data)


@api.route('/tracking',methods=['get','post'])
def tracking():
	data={}
	l=request.args['log_id']
	
	q="SELECT *,CONCAT(`assign`.`status`) AS ass FROM `assign` INNER JOIN `request` USING (`request_id`) INNER JOIN `user` ON `user`.login_id=`request`.`login_id`  INNER JOIN `type` USING (type_id)  WHERE `user`.login_id='%s' ORDER BY `assign`.`assing_id` DESC     "%(l)
	res=select(q)
	if res:
		data['status']="success"

		data['data']=res
		data['ass']=res
		data['method']="tracking"
		
	return str(data)


@api.route('/collecting',methods=['get','post'])
def collecting():
	data={}
	l=request.args['log_id']
	aid=request.args['aid']	
	q="update assign set status='collecting'  where assing_id='%s'"%(aid)
	update(q)
	data['status']="success"
	data['method']="collecting"
		
	return str(data)


@api.route('/pickup',methods=['get','post'])
def pickup():
	data={}
	l=request.args['log_id']
	aid=request.args['aid']	
	q="update assign set status='pickup'  where assing_id='%s'"%(aid)
	update(q)
	data['status']="success"
	data['method']="pickup"
		
	return str(data)


@api.route('/delivered',methods=['get','post'])
def delivered():
	data={}
	l=request.args['log_id']
	aid=request.args['aid']	
	q="update assign set status='delivered'  where assing_id='%s'"%(aid)
	update(q)
	data['status']="success"
	data['method']="delivered"
		
	return str(data)



@api.route('/viewrequest',methods=['get','post'])
def viewrequest():
	data={}
	l=request.args['log_id']
	
	q="SELECT * from request inner join type USING (type_id) where login_id='%s'"%(l)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
		
	return str(data)


@api.route('/viewpayment',methods=['get','post'])
def viewpayment():
	data={}
	l=request.args['log_id']
	
	q="select  *,CONCAT(payment.status) ass from payment inner join assign using (assing_id) inner join deliveryboy using(deliveryboy_id)   where login_id=(select deliveryboy_id from deliveryboy where login_id='%s')"%(l)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
		
	return str(data)
