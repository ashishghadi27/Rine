package Model;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by ashish on 1/3/18.
 */






public class RSSObject
    {
        public String status;
        public Feed feed;
        public List<Item> items;



        public RSSObject(String status, Feed feed, List<Item> items) {
            this.status = status;
            this.feed = feed;
            this.items = items;


        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Feed getFeed() {
            return feed;
        }

        public void setFeed(Feed feed) {
            this.feed = feed;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }


    }

