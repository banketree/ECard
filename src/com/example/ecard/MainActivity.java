package com.example.ecard;


import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View;

import java.io.*;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.content.Intent;
import android.graphics.*;

public class MainActivity extends ActionBarActivity {

    private Button button1, button2, button3;
    private final String tag = "Camera";
    private ImageView ImageView1;
    private boolean isset_image;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        button1 = (Button)findViewById(R.id.button1);

        button1.setOnClickListener(new Button.OnClickListener(){ 

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String path = "";
				//�]�w�ɦW
				
				File sd = Environment.getExternalStorageDirectory(); 
		        path = sd.getPath() + "/ecard"; 
		        File file = new File(path); 
		        if(!file.exists()) file.mkdir();
				
				File tmpFile = new File( path, "temp_image.png");
				Uri outputFileUri = Uri.fromFile(tmpFile);
				
				Intent intent =  new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);    //�Q��intent�h�}��android�������Ӭۤ��� 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri); 
				startActivityForResult(intent, 0);
			}         

        }); 
        
      //���SDCard�Ϥ���ImageView�P�i��Ū��SDCard�Ϥ���Button
        ImageView1 = (ImageView) findViewById(R.id.imageView1);
        button2 = (Button) findViewById(R.id.button2);
        
        button2.setOnClickListener(new Button.OnClickListener()
        {
           @Override
           public void onClick(View arg0) 
           {
              //�T�{�O�_�����JSDCard
              if(checkSDCard())
              {
                 //�a�JSDCard�����Ϥ����|(SDCard: DCIM��Ƨ�/100MEDIA��Ƨ�/001�Ϥ�)
            	  ImageView1.setImageBitmap(getBitmapFromSDCard("ecard/temp_image.png"));
            	  isset_image = true;
              }
              else Toast.makeText(MainActivity.this, 
                                  "�|�����JSDCard", 
                                  Toast.LENGTH_SHORT).show();
           }
        });
        
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new Button.OnClickListener()
        {
           @Override
           public void onClick(View arg0) 
           {
              //�T�{���J�Ϥ�
              if(isset_image)
              {
                 //�N�Ϥ������|�ǤJ edit_activity
            	  Intent intent = new Intent();
            	  Bundle bundle = new Bundle();
            	  bundle.putString( "image_path", Environment.getExternalStorageDirectory() + "/ecard/");
            	  bundle.putString( "image_name", "temp_image.png");
            	  intent.putExtras(bundle);
            	  intent.setClass(MainActivity.this , edit_activity.class);
            	  startActivityForResult(intent, 1);	
              }
              else Toast.makeText(MainActivity.this, 
                                  "�|�����J����Ϥ��A�Х��Ӭ۩θ��J�Ϥ��C", 
                                  Toast.LENGTH_SHORT).show();
           }
        });
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        super.onActivityResult(requestCode, resultCode, data);
		Log.e(tag, "--- onActivityResult  ---" + requestCode);
        if (resultCode == RESULT_OK) {
        	if(requestCode == 1){
        		if(checkSDCard())
                {
	                //�a�JSDCard�����Ϥ����|(SDCard: DCIM��Ƨ�/100MEDIA��Ƨ�/001�Ϥ�)
	          	    ImageView1.setImageBitmap(getBitmapFromSDCard("ecard/temp_image.png"));
	          	    isset_image = true;
                }
                else Toast.makeText(MainActivity.this, 
                                    "�|�����JSDCard", 
                                    Toast.LENGTH_SHORT).show();
        	}
        	else if(requestCode == 0){
	            String img_address = Environment.getExternalStorageDirectory() + "/ecard/temp_image.png";
	            Bitmap bmp = BitmapFactory.decodeFile(img_address); //�Q��BitmapFactory�h���o����Ӫ��Ϲ�
	            ImageView ivTest = (ImageView)findViewById(R.id.imageView1);
	            ivTest.setImageBitmap(bmp);
	            isset_image = true;
        	}
        }
    }
    
  //�T�{�O�_�����JSDCard
    private static boolean checkSDCard() 
    {            
       if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
       {                
          return true;            
       }             
       return false;         
    } 
         
    //Ū��SDCard�Ϥ��A���A��Bitmap
    private static Bitmap getBitmapFromSDCard(String file) 
    {
       try  
       {
          String sd = Environment.getExternalStorageDirectory().toString();
          Bitmap bitmap = BitmapFactory.decodeFile(sd + "/" + file);
          return bitmap;
       }  
       catch (Exception e)  
       {  
          e.printStackTrace();  
          return null;  
       }  
    }
}
