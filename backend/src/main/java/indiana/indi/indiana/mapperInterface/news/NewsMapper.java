package indiana.indi.indiana.mapperInterface.news;

import indiana.indi.indiana.dto.news.NewsDto;
import indiana.indi.indiana.dtoInterface.news.NewsDtoInter;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {

    public NewsDto toDto(NewsDtoInter dtoInter){
        return new NewsDto(
                dtoInter.getId(),
                dtoInter.getAuthorId(),
                dtoInter.getTitle(),
                dtoInter.getContent(),
                dtoInter.getImageUrl(),
                dtoInter.getCreatedAt(),
                dtoInter.getUpdatedAt()
        );
    }
}
