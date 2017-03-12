package wykopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Embed {
    private String type;
    private String preview;
    private String url;
    private String source;
    private Boolean plus18;
}
