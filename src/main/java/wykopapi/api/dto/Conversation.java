package wykopapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// TODO check json
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
    private LocalDateTime lastUpdate;
    private String authorAvatar;
    private String authorAvatarBig;
    private String authorAvatarMed;
    private String authorAvatarLo;
    private Integer authorGroup;
    private String authorSex;
    private String status;
}
