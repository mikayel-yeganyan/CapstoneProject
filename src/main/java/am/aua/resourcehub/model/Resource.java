package am.aua.resourcehub.model;

import java.util.List;

public class Resource {
    private int id;
    private String title;
    private String type;
    private String developer;
    private String region;
    private String language;
    private List<String> keywords;
    private String url;
    private String description;
    private List<String> target;
    private List<String> domain;

    public Resource() {}

    public Resource(int id, String type, String title, String developer, List<String> target, String region, String language, List<String> keywords, String url) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.developer = developer;
        this.target = target;
        this.region = region;
        this.language = language;
        this.keywords = keywords;
        this.url = url;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDeveloper() { return developer; }
    public void setDeveloper(String developer) { this.developer = developer; }

    public List<String> getTarget() { return target; }
    public void setTarget(List<String> target) { this.target = target; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public List<String> getDomain() { return domain; }
    public void setDomain(List<String> domain) { this.domain = domain; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
