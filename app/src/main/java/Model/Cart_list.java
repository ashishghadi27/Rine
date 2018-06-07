package Model;

public class Cart_list {
    private String title;
    private String id;
    private String img;
    private String count;
    private String index;



    public Cart_list(String title, String id, String img, String count, String index) {
        this.title = title;
        this.id = id;
        this.img = img;
        this.count = count;
        this.index = index;

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

    public String getIndex() {
        return index;
    }
}

