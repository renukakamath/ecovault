from flask import *
from database import *
import uuid
from math import radians, sin, cos, sqrt, atan2

import math
factory=Blueprint('factory',__name__)
@factory.route('/factoryhome',methods=['get','post'])
def factoryhome():
	return render_template('factoryhome.html')


@factory.route('/factory_addtype',methods=['post','get'])
def factory_addtype():
	data={}
	fid=session['factory_id']
	q="select * from type  where factory_id='%s'"%(fid)
	res=select(q)
	data['types']=res
	if "addTypes" in request.form:
		p=request.form['types']
		i=request.files['img']
		path="static/image/"+str(uuid.uuid4())+i.filename
		i.save(path)

		q="insert into type values(null,'%s','%s','%s')"%(fid,p,path)
		insert(q)
		flash('successfully')
		return redirect(url_for('factory.factory_addtype'))


	if "action" in request.args:
		action=request.args['action']
		pid=request.args['pid']

	else:
		action=None


	if action=='delete':
		q="delete from type where type_id='%s' "%(pid)
		delete(q)
		flash('successfully')
		return redirect(url_for('factory.factory_addtype'))

	if action=='update':
		q="select * from type where type_id='%s'"%(pid)
		res=select(q)
		data['typesup']=res


	if "update" in request.form:
		p=request.form['types']
		i=request.files['img']
		path="static/image/"+str(uuid.uuid4())+i.filename
		i.save(path)

		q="update type set types='%s' ,image='%s' where type_id='%s'"%(p,path,pid)
		update(q)
		flash('successfully')
		return redirect(url_for('factory.factory_addtype'))
		
	return render_template('factory_addtype.html',data=data)



@factory.route('/factory_viewrequest',methods=['post','get'])
def factory_viewrequest():
	data={}
	fid=session['factory_id']
	q="select * from request inner join type using (type_id) inner join user on user.login_id=request.login_id  where factory_id='%s'"%(fid)
	res=select(q)
	data['types']=res



	if "action" in request.args:
		action=request.args['action']
		pid=request.args['pid']

	else:
		action=None


	if action=='accept':
		q="update request  set status='Accept' where request_id='%s'"%(pid)
		update(q)
		flash('successfully')
		return redirect(url_for('factory.factory_viewrequest'))


	if action=='reject':
		q="update request set status='Reject' where request_id='%s'"%(pid)
		update(q)
		flash('successfully')
		return redirect(url_for('factory.factory_viewrequest'))
	
		
	return render_template('factory_viewrequest.html',data=data)





@factory.route('/factory_viewdeliveryboy',methods=['get','post'])
def factory_viewdeliveryboy():
	data={}

    # Ensure the necessary URL parameters are present
	rid = request.args.get('rid')  # Request ID
	lat1 = request.args.get('lat')  # User latitude
	lon1 = request.args.get('lon')  # User longitude
	qty=request.args.get('qty')

	# Check if any of the required parameters are missing
	if not rid or not lat1 or not lon1:
	    flash('Missing necessary parameters!')
	    return redirect(url_for('factory.factory_viewrequest'))

	# Convert lat1 and lon1 to float (if they are not None)
	lat1 = float(lat1)
	lon1 = float(lon1)

	# Fetch available agents and their locations/capacities
	q = "SELECT * FROM vehicle INNER JOIN deliveryboy USING (deliveryboy_id)  WHERE capacity >= %s and status='available' " % (qty)
	res = select(q)  # Fetch the result from the database
	print(q)
	data['agents']=res

	agent_list = []

	for agent in res:
	    # Convert agent latitude and longitude from strings to floats
	    lat2 = float(agent['dlatitude'])
	    lon2 = float(agent['dlongitude'])

	    # Calculate the distance between user and agent using the Haversine formula
	    distance = haversine_distance(lat1, lon1, lat2, lon2)

	    # Add distance to agent's data
	    agent['distance'] = round(distance, 2)
	    agent_list.append(agent)

	# Sort agents by distance (nearest first)
	agent_list.sort(key=lambda x: x['distance'])

	data['agents'] = agent_list
	data['request_id'] = rid


	return render_template('factory_viewdeliveryboy.html', data=data)

def haversine_distance(lat1, lon1, lat2, lon2):
    R = 6371.0  # Earth's radius in kilometers

    # Convert lat/lon from degrees to radians
    dlat = radians(lat2 - lat1)
    dlon = radians(lon2 - lon1)

    # Haversine formula
    a = sin(dlat / 2)**2 + cos(radians(lat1)) * cos(radians(lat2)) * sin(dlon / 2)**2
    c = 2 * atan2(sqrt(a), sqrt(1 - a))

    # Distance in kilometers
    distance = R * c
    return distance



@factory.route('/assign',methods=['get','post'])
def assign():

	agent_id = request.args.get('agent_id')
	request_id = request.args.get('request_id')
	print('Agent ID:', agent_id)  # Debug
	print('Request ID:', request_id)  # Debug
	if agent_id and request_id:
	    q = "INSERT INTO assign VALUES (NULL, '%s', '%s', CURDATE(), 'assign')" % (agent_id, request_id)
	    print(q)  # Debug the query
	    insert(q)
	    q="update request set status='assign'  where request_id='%s'"%(request_id)
	    update(q)
	    q="update deliveryboy set status='not available' where deliveryboy_id='%s'"%(agent_id)
	    update(q)
	    flash('assign the deliveryboy!')
	    return redirect(url_for('factory.factory_viewrequest'))

	return render_template('factoryhome.html')






@factory.route('/factory_viewassign',methods=['get','post'])
def factory_viewassign():
	data={}
	fid=session['factory_id']

	q="SELECT *,CONCAT(assign.status) AS ass , CONCAT(type.image) as timage FROM `assign` INNER JOIN `deliveryboy` USING (`deliveryboy_id`)  INNER JOIN `request`  USING (`request_id`)    INNER JOIN `type` USING (type_id) INNER JOIN `factory`  USING (`factory_id`) INNER JOIN `user` ON `user`.`login_id`=`request`.`login_id`    where factory_id='%s'  "%(fid)
	res=select(q)
	data['assign']=res

	return render_template('factory_viewassign.html' ,data=data)



@factory.route('/factory_viewpayment',methods=['get','post'])
def factory_viewpayment():
	data={}
	fid=session['factory_id']

	q="select  *,CONCAT(payment.status) ass from payment inner join assign using (assing_id) inner join deliveryboy using(deliveryboy_id)    where factory_id='%s'  "%(fid)
	res=select(q)
	data['assign']=res

	return render_template('factory_viewpayment.html' ,data=data)



@factory.route('/payment',methods=['get','post'])
def payment():
	data={}
	


	deliveryboy_lat = float(request.args['dlatitude'])
	deliveryboy_lon = float(request.args['dlongitude'])
	user_lat = float(request.args['ulatitude'])
	user_lon = float(request.args['ulongitude'])
	factory_lat = float(request.args['flatitude'])
	factory_lon = float(request.args['flongitude'])

	# Calculate distances
	distance_to_user = calculate_distance(deliveryboy_lat, deliveryboy_lon, user_lat, user_lon)
	distance_to_factory = calculate_distance(user_lat, user_lon, factory_lat, factory_lon)
	total_distance = distance_to_user + distance_to_factory


	total_distances = round(total_distance)

	# Assume a rate of $2 per kilometer
	payment_rate = 15
	total_payment = total_distance * payment_rate

	total_payment_rounded = round(total_payment)

	data['total_payment']=total_payment_rounded

	if "submit" in request.form:
		amount=request.form['amount']
		aid=request.args['aid']
		fid=session['factory_id']
		did=request.args['did']
		q="insert into payment values (null ,'%s','%s','%s',curdate(),'paid','%s')"%(aid,fid,amount,total_distances)
		insert(q)
		q="update deliveryboy set status='available' where deliveryboy_id='%s'"%(did)
		update(q)

		q="update assign set status='paid' where assing_id='%s'"%(aid)
		update(q)
		flash('successfully')
		return redirect(url_for('factory.factory_viewassign'))





	return render_template('payment.html', total_distance=total_distance, total_payment_rounded=total_payment_rounded ,data=data)


def calculate_distance(lat1, lon1, lat2, lon2):
    # Haversine formula to calculate the distance between two points on the Earth
    R = 6371  # Radius of the Earth in kilometers
    dlat = math.radians(lat2 - lat1)
    dlon = math.radians(lon2 - lon1)
    a = math.sin(dlat / 2) ** 2 + math.cos(math.radians(lat1)) * math.cos(math.radians(lat2)) * math.sin(dlon / 2) ** 2
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    return R * c  # Distance in kilometers




	

	
	


