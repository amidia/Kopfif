package kopfif.faisal.pkp.kopfif.Model;

/**
 * Created by Faisal on 3/8/2017.
 */

public class DummyModel {
    public String name;
    public String description;
    public String text1;
    public String text2;
    public String text3;
    public String photoUrl;

    public DummyModel(String name,
                      String description,
                      String text1,
                      String text2,
                      String photoUrl) {
        this.name = name;
        this.description = description;
        this.text1 = text1;
        this.text2 = text2;
        this.photoUrl = photoUrl;
    }

    public DummyModel(String name,
                      String text1,
                      String photoUrl) {
        this.name = name;
        this.text1 = text1;
        this.photoUrl = photoUrl;
    }

}
