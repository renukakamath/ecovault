from flask import *
from database import*
import uuid


public=Blueprint('public',__name__)

@public.route('/',methods=['post','get'])
def public_home():
	if "submit" in request.form:
		u=request.form['username']
		p=request.form['password']
		q="select * from login where username='%s' and password='%s'"%(u,p)
		res=select(q)
		if res:
			session['login_id']=res[0]['login_id']
			lid=session['login_id']

			if res[0]['usertype']=="admin":
				return redirect(url_for('admin.adminhome'))
			elif res[0]['usertype']=="factory":
				q="select * from factory where login_id='%s'"%(lid)
				res=select(q)
				if res:
					session['factory_id']=res[0]['factory_id']
					fid=session['factory_id']
				return redirect(url_for('factory.factoryhome'))

			
		


		else:
			flash('invalid username and password')
					
	
					

		
	

	return render_template('index.html')




@public.route('/signup',methods=['post','get'])	
def signup():
	if "submit" in request.form:
		f=request.form['fname']
	
		p=request.form['place']
	
		n=request.form['phone']
		e=request.form['email']
		u=request.form['uname']
		pa=request.form['pwd_confirm']
		la=request.form['latitude']
		lo=request.form['longitude']
		i=request.files['img']
		path="static/image/"+str(uuid.uuid4())+i.filename
		i.save(path)


		q="select * from login where username='%s' and password='%s'"%(u,pa)
		res=select(q)
		if res:

			flash('already exist')

		else:
			
			q="insert into login values(null,'%s','%s','pending')"%(u,pa)
			id=insert(q)
			q="insert into factory values(null,'%s','%s','%s','%s','%s','%s','%s','%s')"%(id,f,p,n,e,la,lo,path)
			insert(q)
			print(q)
			
			flash('successfully')
			return redirect(url_for('public.signup'))

	return render_template('signup.html')
