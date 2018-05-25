package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.asg.ashish.rine.R;
import com.asg.ashish.rine.loadl_link;
import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.InputStream;
import Interface.ItemClickListener;
import Model.RSSObject;



class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView txtTitle, txtPubDate, txtContent, link;
    public ImageView feedimage;
    public String passlink, TAG;


    private ItemClickListener itemClickListener;

    public FeedViewHolder(View itemView) {
        super(itemView);

        txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
        txtPubDate = (TextView)itemView.findViewById(R.id.publishdate);
        txtContent = (TextView)itemView.findViewById(R.id.txtcontent);
        feedimage = (ImageView)itemView.findViewById(R.id.feedimage);
        link = (TextView)itemView.findViewById(R.id.linktext);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        //itemClickListener.onClick(v, getAdapterPosition(), false);
        passlink = link.getText().toString();
        Log.v(TAG, "inhere in item click listener "+passlink);
        Context c = v.getContext();
        Intent link = new Intent(c, loadl_link.class);
        link.putExtra("link",passlink);
        c.startActivity(link);
    }

    @Override
    public boolean onLongClick(View v) {
        //itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>{

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;


    public FeedAdapter(RSSObject rssObject, Context mContext, LayoutInflater inflater) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        this.inflater = inflater;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View itemView = inflater.inflate(R.layout.row, parent, false);
       return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.txtPubDate.setText(rssObject.getItems().get(position).getPubDate());
        String textset = rssObject.getItems().get(position).getDescription();
        textset = textset.replaceAll("</p>.*", "");
        textset = textset.replaceAll("The post.*", "");
        textset = textset.replaceAll("<p>", "");
        textset = textset.replaceAll("<img.*","");
        textset = textset.trim();
        holder.txtContent.setText(textset);
        holder.link.setText(rssObject.getItems().get(position).getLink());
        String imagelink = extractImageUrl(rssObject.getItems().get(position).getDescription());
        //new DownloadImageTask(holder.feedimage).execute(imagelink);
        holder.feedimage.setImageResource(R.drawable.ebars);
        Glide.with(mContext).load(imagelink).override(700,340).into(holder.feedimage);


    }


    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }

    private String extractImageUrl(String description) {
        String TAG="";
        //Log.v(TAG, "out of if");
        Document document = Jsoup.parse(description);
        Elements imgs = document.select("img");

        for (Element img : imgs) {
            //Log.v(TAG, "in image extracter");
            if (img.hasAttr("src")) {
                return img.attr("src");
            }
        }

        // no image URL
        return "";
    }





}

/*class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}*/
