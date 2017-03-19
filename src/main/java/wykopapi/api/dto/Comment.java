package wykopapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Comment {
    private Integer id;
    private LocalDateTime date;
    private String author;
    private String authorAvatar;
    private String authorAvatarBig;
    private String authorAvatarMed;
    private String authorAvatarLo;
    private Integer authorGroup;
    private String authorSex;
    private Integer voteCount;
    private String body;
    private Integer parentId;
    private String status;
    private Embed embed;
    private Link link;
}
