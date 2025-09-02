package indiana.indi.indiana.dtoInterface;

import java.time.LocalDateTime;

public interface CommentDtoInter {
    Long getId();
    String getText();
    Long getAuthorId();
    String getAuthorName();
    LocalDateTime getTime();
    Long getGameId();
}
