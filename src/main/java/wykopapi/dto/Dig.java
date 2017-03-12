package wykopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Dig {
    private String author;
    private String authorAvatar;
    private String authorAvatarBig;
    private String authorAvatarMed;
    private String authorAvatarLo;
    private Integer authorGroup;
    private String authorSex;
    private LocalDateTime date;
}
