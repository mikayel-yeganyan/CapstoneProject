package am.aua.resourcehub.model;

public class Resource {
    private int id;
    private String type;
    private String title;
    private String developer;
    private String target;
    private String region;
    private String language;
    private String keywords;
    private String link;

    public Resource() {}

    public Resource(String type, String title, String developer, String target, String region, String language, String keywords, String link) {
        this.type = type;
        this.title = title;
        this.developer = developer;
        this.target = target;
        this.region = region;
        this.language = language;
        this.keywords = keywords;
        this.link = link;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDeveloper() { return developer; }
    public void setDeveloper(String developer) { this.developer = developer; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}
