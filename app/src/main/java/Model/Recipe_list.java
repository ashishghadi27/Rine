package Model;


public class Recipe_list {
    private String title;
    private String desc;
    private String link;
    private String date;
    private String browser_link;




    public Recipe_list(String title, String desc, String link,  String date, String browser_link) {
        this.title = title;
        this.desc = desc;
        this.link = link;
        this.date = date;
        this.browser_link = browser_link;

    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    public String getBrowser_link() {
        return browser_link;
    }
}


