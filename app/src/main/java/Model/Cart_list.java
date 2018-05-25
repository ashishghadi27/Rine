package Model;

public class Cart_list {
    private String title;
    private String id;
    private String img;
    private String count;



    public Cart_list(String title, String id, String img, String count) {
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
}

