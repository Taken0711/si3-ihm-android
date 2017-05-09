package si3.ihm.polytech.capsophia;

/**
 * Created by user on 08/05/2017.
 */

public class NewsModel {

    private String title;
    private String subtitle;
    private int imgResId;

    public NewsModel(String title, String subtitle, int imgResId) {
        this.title = title;
        this.subtitle = subtitle;
        this.imgResId = imgResId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getImgResId() {
        return imgResId;
    }
}
