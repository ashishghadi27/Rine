package com.asg.ashish.rine;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Fade;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import Database.DBHandler;
import Model.Cart_list;

public class blueberry extends AppCompatActivity  {
    private DrawerLayout mDrawerLayout;
    TextView counter1, titletext, info1, info4;
    ImageView img, img2, img3, img4;
    int count1=1;
    Button subscribe, arrowleft, arrowright;
    private String productid, texttitle, link1, link2, link3, link4, description, TAG = "CHECK:", jsonur = "https://www.rinebars.com/wp-json/wp/v2/product/", info, para, price;
    DBHandler dbHandler;
    CardView cv;
    FloatingActionButton cart;
    private Navi_drawer navi_drawer;
    private NavigationView navigationView;
    private HorizontalScrollView horizontalScrollView;
    int count = 1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blueberry);
        mDrawerLayout = findViewById(R.id.navi);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        counter1 = findViewById(R.id.bluecountertext);
        titletext = findViewById(R.id.bluetext);
        img = findViewById(R.id.imglay);
        subscribe = findViewById(R.id.subscribe);
        cv = findViewById(R.id.card_view);
        cart = findViewById(R.id.floatproduct1);

        Intent i = getIntent();
        productid = i.getStringExtra("productid");
        texttitle = i.getStringExtra("title");
        link1 = i.getStringExtra("link1");
        link2 = i.getStringExtra("link2");
        link3 = i.getStringExtra("link3");
        link4 = i.getStringExtra("link4");
        description = i.getStringExtra("description");

        titletext.setText(texttitle);
        Glide.with(this).load(link1).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
        jsonur = jsonur + productid;

        info1 = findViewById(R.id.info1);
        info4 = findViewById(R.id.info4);
        arrowleft = findViewById(R.id.arrowleft);
        arrowright = findViewById(R.id.arrowright);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);
        fade.excludeTarget(R.id.linear,true);

        dbHandler = new DBHandler(this, null, null, 2);
        if(texttitle.contains("Blueberry")){
            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1565c0")));
            subscribe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0d47a1")));
            cart.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0d47a1")));
        }
        else if(texttitle.contains("Strawberry")){

            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f50057")));
            subscribe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#c51162")));
            cart.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#c51162")));
        }
        else if(texttitle.contains("Peanut")){

            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffab00")));
            subscribe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff8f00")));
            cart.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff8f00")));
        }
        else if(texttitle.contains("Chocolate")){

            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4e342e")));
            subscribe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3e2723")));
            cart.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3e2723")));
        }
        else {

            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1de9b6")));
            subscribe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#28b180")));
            cart.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#28b180")));
        }
        Document document = Jsoup.parse(description);
        Elements element = document.select("h4");
        Log.v(TAG, document.toString());
        Log.v(TAG, element.toString());
        info = element.toString();
        element = document.select("p");
        para = element.toString();
        Log.v(TAG, element.toString());

        info = info.replaceAll("<b>","\n");
        info = info.replace("</b>","\b");
        info = info.replaceAll("\\<[^>]*>", "");
        para = para.replace("&nbsp;","");
        para = para.replaceAll("\\<[^>]*>","");
        info1.setText(info);
        price = info;
        info4.setText(para);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });

        img.setOnTouchListener(new OnSwipeTouchListener() {

            public boolean onSwipeRight() {
                if(count>1){
                    count--;
                }
                if(count == 2)
                    Glide.with(blueberry.this).load(link2).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
                else if(count == 3)
                    Glide.with(blueberry.this).load(link3).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
                else if(count == 1)
                    Glide.with(blueberry.this).load(link1).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
                return true;
            }
            public boolean onSwipeLeft() {
                if(count<4){
                    count++;
                }
                if(count == 2)
                    Glide.with(blueberry.this).load(link2).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
                else if(count == 3)
                    Glide.with(blueberry.this).load(link3).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
                else if(count == 4)
                    Glide.with(blueberry.this).load(link4).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
                return true;
            }

        });

        arrowleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>1){
                    count--;
                }
                if(count == 2)
                    Glide.with(blueberry.this).load(link2).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
                else if(count == 3)
                    Glide.with(blueberry.this).load(link3).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
                else if(count == 1)
                    Glide.with(blueberry.this).load(link1).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
            }
        });

        arrowright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<4){
                    count++;
                }
                if(count == 2)
                    Glide.with(blueberry.this).load(link2).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
                else if(count == 3)
                    Glide.with(blueberry.this).load(link3).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
                else if(count == 4)
                    Glide.with(blueberry.this).load(link4).placeholder(R.drawable.sampleprofile).override(1000,600).into(img);
            }
        });





    }
    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.RIGHT);

    }


    /*public void save_price(){

            //Log.v("INFO TEXT", price);
            /*String product = titletext.getText().toString();
            java.util.regex.Pattern p = java.util.regex.Pattern.compile("-?\\d+");
            java.util.regex.Matcher m = p.matcher(price);
            if(m.find())
            {
                String temp_= m.group();
                Log.v("PRICE CHECK", temp_);

            }
        String product = titletext.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("price",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(product, temp_);
        editor.apply();




    }*/

   /* public blueberry(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public void onSwipeLeft() {
        if(count>1){
            count--;
        }
        if(count == 2)
            Glide.with(this).load(link2).override(1000,700).into(img);
        else if(count == 3)
            Glide.with(this).load(link3).override(1000,700).into(img);
        else if(count == 4)
            Glide.with(this).load(link4).override(1000,700).into(img);

    }

    public void onSwipeRight() {
        if(count<4){
            count++;
        }
        if(count == 2)
            Glide.with(this).load(link2).override(1000,700).into(img);
        else if(count == 3)
            Glide.with(this).load(link3).override(1000,700).into(img);
        else if(count == 4)
            Glide.with(this).load(link4).override(1000,700).into(img);

    }

    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    onSwipeRight();
                else
                    onSwipeLeft();
                return true;
            }
            return false;
        }
    }*/

    public void increase_count1(View view){
        count1 = count1 + 1;
        counter1.setText(""+count1);
    }

    public void decrease_count1(View view){
        if(count1!=1)
        {
            count1 = count1 - 1;
            counter1.setText(""+count1);
        }
        else count1 = 1;

    }


    public void tocart(View view){
        Intent i = new Intent(this, Cart_Activity.class);
        startActivity(i);
    }

    public void subs_activity(View view){
        Intent intent = new Intent(this, Per_pro_subscription.class);
        intent.putExtra("link", link1);
        intent.putExtra("title", texttitle);
        startActivity(intent);
    }




    public void back(View view){
        this.onBackPressed();
    }

    public void add_to_cart(View view) {

        Cart_list item = new Cart_list(productid, texttitle, link1, (String) counter1.getText());
        dbHandler.addProduct(item);
        Toast.makeText(blueberry.this, "ITEM ADDED", Toast.LENGTH_SHORT).show();
    }
}
