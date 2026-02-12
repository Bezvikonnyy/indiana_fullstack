package indiana.indi.indiana.service.news;

import indiana.indi.indiana.dto.news.NewsDto;
import indiana.indi.indiana.mapperInterface.news.NewsMapper;
import indiana.indi.indiana.repository.news.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class NewsForControllerService {

    private final CRUDNewsService service;
    private final NewsRepository repository;
    private final NewsMapper mapper;

    public NewsDto getNews(Long newsId) {
        return service.getNews(newsId);
    }

    public NewsDto createNews(String title, String content, MultipartFile imageFile, Long userId) {
        return service.createNews(title, content, imageFile, userId);
    }

    public NewsDto editNews(Long newsId, String title, String content, MultipartFile imageFile, Long userId) {
        return service.updatedNews(newsId, title, content, imageFile, userId);
    }

    public void deleteNews(Long newsId, Long userId) {
        service.deleteNews(newsId, userId);
    }

    public Page<NewsDto> getAllNews(Integer page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        return repository.getAllNews(pageable).map(mapper::toDto);
    }
}
