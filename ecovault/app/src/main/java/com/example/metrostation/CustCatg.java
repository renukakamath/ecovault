package com.example.metrostation;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustCatg extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;
	private String[] factory;
	private String[] phone;
	private String[] email;
	private String[] place;
	private String[] image;





	public CustCatg(Activity context, String[] factory, String[] phone , String[] email , String[] place, String[] image ) {
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.cust_design, image);
	        this.context = context;
	        this.factory = factory;
		this.phone = phone;
		this.email = email;
		this.place = place;

		this.image = image;

	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = context.getLayoutInflater();
			View listViewItem = inflater.inflate(R.layout.cust_design, null, true);
			//cust_list_view is xml file of layout created in step no.2

			ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
			TextView t1=(TextView)listViewItem.findViewById(R.id.textView3);


			t1.setText(factory[position]+"\n"+phone[position]+"\n"+email[position]+"\n"+place[position]);
//			t2.setText(caption[position]);
			sh=PreferenceManager.getDefaultSharedPreferences(getContext());

			String pth = "http://"+sh.getString("ip", "")+"/"+image[position];
			pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

			Log.d("-------------", pth);
			Picasso.with(context)
					.load(pth)
					.placeholder(R.drawable.ic_launcher_background)
					.error(R.drawable.ic_launcher_background).into(im);

			return  listViewItem;
		}

	private TextView setText(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}