package wykopapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class VoteEntry {
    private Integer vote;
    private List<Dig> voters;
}
