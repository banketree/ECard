package com.example.ecard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class edit_activity extends Activity {

    private Button button1, button2, button3, button4, button5, button6;
    private ImageView ImageView1;
    private Bitmap edit_image, deal_image;
    private String image_path, image_name;
    private DisplayMetrics metrics;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        
        metrics = new DisplayMetrics();  
	    getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
        ImageView1 = (ImageView) findViewById(R.id.imageView1);
        image_path = getIntent().getExtras().getString("image_path");
        image_name = getIntent().getExtras().getString("image_name");
        try{
        	Drawable drawable = Drawable.createFromPath(image_path + image_name);  
            //將Drawable轉為Bitmap  
            Bitmap bitmap = ImageUtil.drawableToBitmap(drawable);  
            //縮放圖片  
            Bitmap zoomBitmap = ImageUtil.zoomBitmap(bitmap, metrics.heightPixels, metrics.widthPixels);
        	edit_image = zoomBitmap;
        	deal_image = zoomBitmap;
        	ImageView1.setImageBitmap(edit_image);
        }  
        catch (Exception e){  
        	e.printStackTrace();
        }  
        
        button1 = (Button)findViewById(R.id.button1);

        button1.setOnClickListener(new Button.OnClickListener(){ 

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
          	    intent.putExtra( "image_fullpath", StoreImage(deal_image, image_path, "deal_" + image_name));
          	    intent.putExtra( "image_path", image_path);
          	    intent.putExtra( "image_name", "deal_" + image_name);
          	    intent.setClass(edit_activity.this , Edit_reflection.class);
          	    startActivityForResult(intent, 3);

			}         

        });
        
        button2 = (Button)findViewById(R.id.button2);

        button2.setOnClickListener(new Button.OnClickListener(){ 

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
          	    intent.putExtra( "image_fullpath", StoreImage(deal_image, image_path, "deal_" + image_name));
          	    intent.putExtra( "image_path", image_path);
          	    intent.putExtra( "image_name", "deal_" + image_name);
          	    intent.setClass(edit_activity.this , Edit_rotate.class);
          	    startActivityForResult(intent, 3);

			}         

        });
        
        button3 = (Button)findViewById(R.id.button3);

        button3.setOnClickListener(new Button.OnClickListener(){ 

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
          	    intent.putExtra( "image_fullpath", StoreImage(deal_image, image_path, "deal_" + image_name));
          	    intent.putExtra( "image_path", image_path);
          	    intent.putExtra( "image_name", "deal_" + image_name);
          	    intent.setClass(edit_activity.this , edit_Round.class);
          	    startActivityForResult(intent, 3);

			}         

        });
        
        button4 = (Button)findViewById(R.id.button4);

        button4.setOnClickListener(new Button.OnClickListener(){ 

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
          	    intent.putExtra( "image_fullpath", StoreImage(deal_image, image_path, "deal_" + image_name));
          	    intent.putExtra( "image_path", image_path);
          	    intent.putExtra( "image_name", "deal_" + image_name);
          	    intent.setClass(edit_activity.this , Edit_gray.class);
          	    startActivityForResult(intent, 3);
			}         

        }); 
        
        button5 = (Button)findViewById(R.id.button5);

        button5.setOnClickListener(new Button.OnClickListener(){ 

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ImageView1.setImageBitmap(edit_image);
				StoreImage(edit_image, image_path, "deal_" + image_name);
				deal_image = edit_image;
			}         

        });
        
        button6 = (Button)findViewById(R.id.button6);

        button6.setOnClickListener(new Button.OnClickListener(){ 

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ImageView1.setImageBitmap(edit_image);
				StoreImage(deal_image, image_path, "deal_" + image_name);
				StoreImage(deal_image, image_path, image_name);
				deal_image = edit_image;
				setResult(RESULT_OK, new Intent());
				finish();
			}         

        }); 
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        super.onActivityResult(requestCode, resultCode, data);
		Log.e("Edit", "--- onActivityResult  ---");
        if (resultCode == RESULT_OK) {
        	int type = data.getExtras().getInt("type");
        	switch(type)
        	{
        		case 0:
        			break;
        		case 3:
	            	deal_image = BitmapFactory.decodeFile(data.getStringExtra("image_fullpath"));
		            ImageView1.setImageBitmap(deal_image);
		            break;
	            default: ;
        	}
        }
    }
    
    private String StoreImage(Bitmap bmp, String image_path, String image_name){
    	try{
			File file = new File(image_path, image_name);
			FileOutputStream out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
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

