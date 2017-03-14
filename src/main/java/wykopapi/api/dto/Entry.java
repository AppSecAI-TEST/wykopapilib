package wykopapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Entry {
    private Integer id;

    private String author;
    private String authorAvatar;
    private String authorAvatarBig;
    private String authorAvatarMed;
    private String authorAvatarLo;
    private Integer authorGroup;
    private String authorSex;

    private String receiver;
    private String receiverAvatar;
    private String receiverAvatarBig;
    private String receiverAvatarMed;
    private String receiverAvatarLo;
    private Integer receiverGroup;
    private String receiverSex;

    private LocalDateTime date;
    private String body;
    private String source;
    private String url;
    private String type;
    private String violationUrl;
    private String app;
    private Embed embed;

    private Integer commentCount;
    private List<EntryComment> comments;

    private Integer userVote;
    private Integer voteCount;
    private List<Dig> voters;

    private Boolean blocked;
    private String canComment;
    private Boolean deleted;
    private Boolean userFavorite;
}
