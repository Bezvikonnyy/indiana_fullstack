package indiana.indi.indiana.repository.news;

import indiana.indi.indiana.dtoInterface.news.NewsDtoInter;
import indiana.indi.indiana.entity.news.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("""
            SELECT 
                n.id as id,
                n.author.id as authorId,
                n.title as title,
                n.content as content,
                n.imageUrl as imageUrl,
                n.createdAt as createdAt,
                n.updatedAt as updatedAt
            FROM News n
            """)
    Page<NewsDtoInter> getNewsByHome(Pageable pageable);

    @Query("""
            SELECT 
                n.id as id,
                n.author.id as authorId,
                n.title as title,
                n.content as content,
                n.imageUrl as imageUrl,
                n.createdAt as createdAt,
                n.updatedAt as updatedAt
            FROM News n
            """)
    Page<NewsDtoInter> getNewsByNews(Pageable pageable);
}
