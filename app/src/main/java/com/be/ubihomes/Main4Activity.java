package com.be.ubihomes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.R.attr.name;

public class Main4Activity extends AppCompatActivity implements View.OnTouchListener {
    ImageView imageView;
    ImageView product;
    Button btn, btnplus,btnminus,btnclock,btnanticlock;
    String finalurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        //Intent intent = new Intent(Main4Activity.this,Main3Activity.class);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        Bundle bundle = getIntent().getExtras();
//        String path = bundle.getString("PATH");
        btnminus=(Button)findViewById(R.id.minus);
        btnplus=(Button)findViewById(R.id.plus);
        btnclock=(Button)findViewById(R.id.clock);
        btnanticlock=(Button)findViewById(R.id.anticlock);
        btn= (Button) findViewById(R.id.buyproduct);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main4Activity.this,Main5Activity.class);
                intent.putExtra("LOAD4URL",finalurl);
                startActivity(intent);
            }
        });


        String fileName = "stored_image.jpg";
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String pathDir = baseDir + "/Android/data/com.be.ubihomes/";

        String fileName2 = "stored_image.jpg";
        String baseDir2 = Environment.getExternalStorageDirectory().getAbsolutePath();
        String pathDir2 = baseDir + "/Android/data/com.be.ubihomes/";

        product=(ImageView)findViewById(R.id.product);
        //product.setImageResource(R.drawable.tv1);
        Uri imgUri= Uri.parse("file:///sdcard/download/success.png");
        String mainu=imgUri.toString();
        Toast.makeText(getApplicationContext(),mainu,Toast.LENGTH_SHORT).show();
        product.setImageURI(imgUri);
        product.setOnTouchListener(this);

        Bundle bundle = getIntent().getExtras();
        String receiver = bundle.getString("PATH");
        Double dlen=bundle.getDouble("DLEN");
        Double dhei=bundle.getDouble("DHEI");
        finalurl=bundle.getString("BUYU");
        final String imgUrl ="file:///sdcard/android/data/com.be.ubihomes/files/"+receiver;
        imageView = (ImageView) findViewById(R.id.productBack);

        product.getLayoutParams().height=400;
        product.getLayoutParams().width=400;
        Log.d("data","received "+name+" data\n"+"DLength:"+dlen+"\n"+"Height:"+dhei);
        final Context context=getApplicationContext();

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            int oheight=product.getLayoutParams().height;
            int owidth=product.getLayoutParams().width;
            product.getLayoutParams().height=oheight+10;
            product.getLayoutParams().width=owidth+10;
            Glide.with(context).load(imgUrl)
                        .thumbnail(0.5f)
                        .into(imageView);

            }
        });

        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mheight=product.getLayoutParams().height;
                int mwidth=product.getLayoutParams().width;
                product.getLayoutParams().height=mheight-10;
                product.getLayoutParams().width=mwidth-10;
                Glide.with(context).load(imgUrl)
                        .thumbnail(0.5f)
                        .into(imageView);

            }
        });

        btnclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rot = product.getRotation();
                product.setRotation(rot + 2);
                Glide.with(context).load(imgUrl)
                        .thumbnail(0.5f)
                        .into(imageView); //clockwise rotation

            }
        });


        btnanticlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rot = product.getRotation();
                product.setRotation(rot - 2);
                Glide.with(context).load(imgUrl)
                        .thumbnail(0.5f)
                        .into(imageView);  //anticlockwise rotation

            }
        });


        Context mContext;

        Glide.with(this).load(imgUrl)
                .thumbnail(0.5f)
                .into(imageView);

Button sharebutton = (Button)(findViewById(R.id.sharebutton));
sharebutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Bitmap bitmap = takeScreenshot();
        saveBitmap(bitmap);
        shareIt();
    }
});
    }
    float x,y=0.0f;
    boolean moving = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                moving=true;
                break;

            case MotionEvent.ACTION_MOVE :
                if (moving){
                    x=event.getRawX()-product.getWidth()/2;
                    y=event.getRawY()-product.getHeight()/2;
                    product.setX(x);
                    product.setY(y);

                }
                break;

            case MotionEvent.ACTION_UP :
                moving=false;
                break;
        }
        return true;
    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(R.id.mainrelativelayout);
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    File imagePath;
    public void saveBitmap(Bitmap bitmap) {
         imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
        }
    private void shareIt() {
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "Always 'try' before you 'buy' Install TryB4Shop Now: https://play.google.com/store/apps/details?id=com.be.ubihomes";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "TtyB4Shop Invite");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

}
