package Model;

public class Cart_list {
    private String title;
    private String id;
    private String img;
    private String count;


    public Cart_list() {



    }

    public Cart_list( String id,String title, String img, String count) {
        this.title = title;
        this.id = id;
        this.img = img;
        this.count = count;


    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getCount() {
        return count;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

