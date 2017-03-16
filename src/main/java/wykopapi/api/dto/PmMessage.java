package wykopapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// TODO check json
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class PmMessage {
    private String authorAvatar;
    private String authorAvatarBig;
    private String authorAvatarMed;
    private String authorAvatarLo;
    private Integer authorGroup;
    private String authorSex;
    private LocalDateTime date;
    private String body;
    private String status;
    private String direction;
}
