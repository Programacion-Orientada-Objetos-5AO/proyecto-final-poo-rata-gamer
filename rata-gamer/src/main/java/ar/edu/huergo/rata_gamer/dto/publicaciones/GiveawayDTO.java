package ar.edu.huergo.rata_gamer.dto.publicaciones;

public class GiveawayDTO {

    private Long id;
    private String title;
    private String worth;
    private String thumbnail;
    private String image;
    private String description;
    private String instructions;
    private String open_giveaway_url;
    private String published_date;
    private String type;
    private String platforms;
    private String end_date;
    private Integer users;
    private String status;
    private String gamerpower_url;
    private String open_giveaway;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getWorth() { return worth; }
    public void setWorth(String worth) { this.worth = worth; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getOpen_giveaway_url() { return open_giveaway_url; }
    public void setOpen_giveaway_url(String open_giveaway_url) { this.open_giveaway_url = open_giveaway_url; }

    public String getPublished_date() { return published_date; }
    public void setPublished_date(String published_date) { this.published_date = published_date; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPlatforms() { return platforms; }
    public void setPlatforms(String platforms) { this.platforms = platforms; }

    public String getEnd_date() { return end_date; }
    public void setEnd_date(String end_date) { this.end_date = end_date; }

    public Integer getUsers() { return users; }
    public void setUsers(Integer users) { this.users = users; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGamerpower_url() { return gamerpower_url; }
    public void setGamerpower_url(String gamerpower_url) { this.gamerpower_url = gamerpower_url; }

    public String getOpen_giveaway() { return open_giveaway; }
    public void setOpen_giveaway(String open_giveaway) { this.open_giveaway = open_giveaway; }
}
