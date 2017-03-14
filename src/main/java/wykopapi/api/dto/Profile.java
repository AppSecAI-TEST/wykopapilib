package wykopapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Profile {
    private String login;
    private String email;
    private String publicEmail;
    private String name;
    private String sex;
    private String www;
    private String url;
    private String violationUrl;
    private String userkey;
    private String jabber;
    private String gg;
    private String city;
    private String about;
    private Integer authorGroup;
    private Integer linksAdded;
    private Integer linksPublished;
    private Integer comments;
    private Integer rank;
    private Integer followers;
    private Integer following;
    private Integer entries;
    private Integer entriesComments;
    private Integer diggs;
    private Integer buries;
    private Integer relatedLinks;
    private Integer groups;
    private String avatar;
    private String avatarBig;
    private String avatarMed;
    private String avatarLo;
    private Boolean isObserved;
    private Boolean isBlocked;
    private LocalDateTime signupDate;
}
