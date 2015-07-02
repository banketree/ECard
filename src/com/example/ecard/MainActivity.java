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
				//設定檔名
				
				File sd = Environment.getExternalStorageDirectory(); 
		        path = sd.getPath() + "/ecard"; 
		        File file = new File(path); 
		        if(!file.exists()) file.mkdir();
				
				File tmpFile = new File( path, "temp_image.png");
				Uri outputFileUri = Uri.fromFile(tmpFile);
				
				Intent intent =  new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);    //利用intent去開啟android本身的照相介面 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri); 
				startActivityForResult(intent, 0);
			}         

        }); 
        
      //顯示SDCard圖片的ImageView與進行讀取SDCard圖片的Button
        ImageView1 = (ImageView) findViewById(R.id.imageView1);
        button2 = (Button) findViewById(R.id.button2);
        
        button2.setOnClickListener(new Button.OnClickListener()
        {
           @Override
           public void onClick(View arg0) 
           {
              //確認是否有插入SDCard
              if(checkSDCard())
              {
                 //帶入SDCard內的圖片路徑(SDCard: DCIM資料夾/100MEDIA資料夾/001圖片)
            	  ImageView1.setImageBitmap(getBitmapFromSDCard("ecard/temp_image.png"));
            	  isset_image = true;
              }
              else Toast.makeText(MainActivity.this, 
                                  "尚未插入SDCard", 
                                  Toast.LENGTH_SHORT).show();
           }
        });
        
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new Button.OnClickListener()
        {
           @Override
           public void onClick(View arg0) 
           {
              //確認載入圖片
              if(isset_image)
              {
                 //將圖片的路徑傳入 edit_activity
            	  Intent intent = new Intent();
            	  Bundle bundle = new Bundle();
            	  bundle.putString( "image_path", Environment.getExternalStorageDirectory() + "/ecard/");
            	  bundle.putString( "image_name", "temp_image.png");
            	  intent.putExtras(bundle);
            	  intent.setClass(MainActivity.this , edit_activity.class);
            	  startActivityForResult(intent, 1);	
              }
              else Toast.makeText(MainActivity.this, 
                                  "尚未載入任何圖片，請先照相或載入圖片。", 
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
	                //帶入SDCard內的圖片路徑(SDCard: DCIM資料夾/100MEDIA資料夾/001圖片)
	          	    ImageView1.setImageBitmap(getBitmapFromSDCard("ecard/temp_image.png"));
	          	    isset_image = true;
                }
                else Toast.makeText(MainActivity.this, 
                                    "尚未插入SDCard", 
                                    Toast.LENGTH_SHORT).show();
        	}
        	else if(requestCode == 0){
	            String img_address = Environment.getExternalStorageDirectory() + "/ecard/temp_image.png";
	            Bitmap bmp = BitmapFactory.decodeFile(img_address); //利用BitmapFactory去取得剛剛拍照的圖像
	            ImageView ivTest = (ImageView)findViewById(R.id.imageView1);
	            ivTest.setImageBitmap(bmp);
	            isset_image = true;
        	}
        }
    }
    
  //確認是否有插入SDCard
    private static boolean checkSDCard() 
    {            
       if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
       {                
          return true;            
       }             
       return false;         
    } 
         
    //讀取SDCard圖片，型態為Bitmap
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
