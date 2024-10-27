from flask import *
from database import *
import uuid
admin=Blueprint('admin',__name__)
@admin.route('/adminhome',methods=['get','post'])
def adminhome():
	return render_template('adminhome.html')


@admin.route('/admin_viewuser')
def admin_viewuser():
	data={}
	q="select * from user inner join login using (login_id)"
	res=select(q)
	data['user']=res

	if "action" in request.args:
		action=request.args['action']
		cid=request.args['cid']

	else:
		action=None

	if action=='accept':
		
		q="update login set usertype='user' where login_id='%s'"%(cid)
		update(q)
		flash('successfully')
		return redirect(url_for('admin.admin_viewuser'))

	if action=='reject':
		
		q="update login set usertype='reject' where login_id='%s'"%(cid)
		update(q)
		flash('successfully')
		return redirect(url_for('admin.admin_viewuser'))
				

			
		
	return render_template('admin_viewuser.html',data=data)



@admin.route('/admin_viewdeliveryboy')
def admin_viewdeliveryboy():
	data={}
	q="select * from deliveryboy inner join login using (login_id)"
	res=select(q)
	data['deliveryboy']=res

	if "action" in request.args:
		action=request.args['action']
		cid=request.args['cid']

	else:
		action=None

	if action=='accept':
		
		q="update login set usertype='deliveryboy' where login_id='%s'"%(cid)
		update(q)
		flash('successfully')
		return redirect(url_for('admin.admin_viewdeliveryboy'))

	if action=='reject':
		
		q="update login set usertype='reject' where login_id='%s'"%(cid)
		update(q)
		flash('successfully')
		return redirect(url_for('admin.admin_viewdeliveryboy'))
				

			
		
	return render_template('admin_viewdeliveryboy.html',data=data)



@admin.route('/admin_viewfactory')
def admin_viewfactory():
	data={}
	q="select * from factory inner join login using (login_id)"
	res=select(q)
	data['factory']=res

	if "action" in request.args:
		action=request.args['action']
		cid=request.args['cid']

	else:
		action=None

	if action=='accept':
		
		q="update login set usertype='factory' where login_id='%s'"%(cid)
		update(q)
		flash('successfully')
		return redirect(url_for('admin.admin_viewfactory'))

	if action=='reject':
		
		q="update login set usertype='reject' where login_id='%s'"%(cid)
		update(q)
		flash('successfully')
		return redirect(url_for('admin.admin_viewfactory'))
				

			
		
	return render_template('admin_viewfactory.html',data=data)




