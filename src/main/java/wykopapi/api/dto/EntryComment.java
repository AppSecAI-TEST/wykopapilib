package wykopapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class EntryComment {
    private Integer id;

    private String author;
    private String authorAvatar;
    private String authorAvatarBig;
    private String authorAvatarMed;
    private String authorAvatarLo;
    private Integer authorGroup;
    private String authorSex;

    private LocalDateTime date;
    private String body;
    private String source;
    private Integer entryId;

    private Integer voteCount;
    private Integer userVote;
    private List<Dig> voters;
    private Embed embed;
    private Entry entry;

    private String type;
    private String app;
    private String violationUrl;

    private Boolean blocked;
    private Boolean deleted;
}
