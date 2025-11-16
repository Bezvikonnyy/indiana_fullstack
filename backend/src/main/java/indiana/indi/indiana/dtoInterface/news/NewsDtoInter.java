package indiana.indi.indiana.dtoInterface.news;

import java.time.LocalDateTime;

public interface NewsDtoInter {
    Long getId();
    String getTitle();
    String getContent();
    String getImageUrl();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}
