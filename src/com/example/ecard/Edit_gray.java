package com.example.ecard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class Edit_gray extends ActionBarActivity {

	private TextView textView1;
	private Button button1, button2;
	private Bitmap bitmap, deal_image;
	private ImageView ImageView1;
	private String image_fullpath, image_path, image_name;
	private DisplayMetrics metrics;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_gray);
		
		Intent ori_intent = this.getIntent();
		image_fullpath = ori_intent.getStringExtra("image_fullpath");
		image_path = ori_intent.getStringExtra("image_path");
		image_name = ori_intent.getStringExtra("image_name");
		
		ImageView1 = (ImageView) findViewById(R.id.imageView1);
		textView1 = (TextView) findViewById(R.id.textView1);
		button1 = (Button)findViewById(R.id.button1);
		button2 = (Button)findViewById(R.id.button2);
		
		metrics = new DisplayMetrics();  
	    getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
	    //獲取值是Drawable  
        Drawable drawable = Drawable.createFromPath(image_fullpath);  
        //將Drawable轉為Bitmap  
        bitmap = ImageUtil.drawableToBitmap(drawable);  
        //縮放圖片  
        Bitmap zoomBitmap = ImageUtil.zoomBitmap(bitmap, metrics.heightPixels, metrics.widthPixels);
        deal_image = ImageUtil.getGrayBitmap(zoomBitmap);
		ImageView1.setImageBitmap(deal_image);
		textView1.setText("灰階處理完畢");
		
		button1.setOnClickListener(new Button.OnClickListener(){ 

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent();
				i.putExtra("type" , 0);
				setResult(RESULT_OK, i);
				finish();
			}         

        });
		
        button2.setOnClickListener(new Button.OnClickListener(){ 

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent();
				
				i.putExtra("image_fullpath", StoreImage(deal_image, image_path, image_name));
				i.putExtra("image_path", image_path);
				i.putExtra("image_name", image_name);
				i.putExtra("type", 3);
				
				setResult(RESULT_OK, i);
				finish();
			}         

        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_gray, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private String StoreImage(Bitmap bmp, String image_path, String image_name){
    	try{
			File file = new File(image_path, image_name);
			FileOutputStream out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		}catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace ();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace ();
		}
    	return image_path + image_name;
    }
}
