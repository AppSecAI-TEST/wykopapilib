package wykopapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

// TODO check json
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    private Integer id;
    private String title;
    private String description;
    private String tags;
    private String url;
    private String sourceUrl;
    private Integer voteCount;
    private Integer commentCount;
    private Integer reportCount;
    private LocalDateTime date;
    private String author;
    private String authorAvatar;
    private String authorAvatarBig;
    private String authorAvatarMed;
    private String authorAvatarLo;
    private Integer authorGroup;
    private String preview;
    private List<Integer> userLists;
    private Boolean plus18;
    private String status; // ?
    private Boolean canVote;
    private Boolean hasOwnContent;
    private String category;
    private String userVote;
    private Boolean userObserve;
    private Boolean userFavorite;
}
