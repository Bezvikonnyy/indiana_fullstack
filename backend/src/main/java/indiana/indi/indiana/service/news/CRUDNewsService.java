package indiana.indi.indiana.service.news;

import indiana.indi.indiana.dto.news.NewsDto;
import indiana.indi.indiana.entity.news.News;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.repository.news.NewsRepository;
import indiana.indi.indiana.repository.users.UserRepository;
import indiana.indi.indiana.service.game.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CRUDNewsService {

    private final FileService fileService;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    public NewsDto createNews(String title, String content, MultipartFile imageFile, Long userId) {
        String imageFileUrl = fileService.saveFile(imageFile, "newsImageFile");
        User author = userRepository.getReferenceById(userId);
        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        news.setImageUrl(imageFileUrl);
        news.setAuthor(author);
        newsRepository.save(news);
        return new NewsDto(
                news.getId(),
                news.getAuthor().getId(),
                news.getTitle(),
                news.getContent(),
                news.getImageUrl(),
                news.getCreatedAt(),
                news.getUpdatedAt());
    }

    public NewsDto updatedNews(Long newsId, String title, String content, MultipartFile imageFile, Long userId) {
        News news = newsRepository.findById(newsId).orElseThrow(() -> new EntityNotFoundException("News not found"));
        accessRight(userId, news);
        if (imageFile != null && !imageFile.isEmpty()) {
            fileService.deleteFileIfExists(news.getImageUrl());
            String newImageFileUrl = fileService.saveFile(imageFile, "newsImageFile");
            news.setImageUrl(newImageFileUrl);
        }
        news.setTitle(title);
        news.setContent(content);
        newsRepository.save(news);
        return new NewsDto(
                news.getId(),
                news.getAuthor().getId(),
                news.getTitle(),
                news.getContent(),
                news.getImageUrl(),
                news.getCreatedAt(),
                news.getUpdatedAt());
    }

    public NewsDto getNews(Long newsId) {
        News news = newsRepository.findById(newsId).orElseThrow(() -> new EntityNotFoundException("News not found."));
        return new NewsDto(
                news.getId(),
                news.getAuthor().getId(),
                news.getTitle(),
                news.getContent(),
                news.getImageUrl(),
                news.getCreatedAt(),
                news.getUpdatedAt());
    }

    public void deleteNews(Long newsId, Long userId) {
        News news = newsRepository.findById(newsId).orElseThrow(() -> new EntityNotFoundException("News not found."));
        accessRight(userId, news);
        newsRepository.delete(news);
    }

    public void accessRight(Long userId, News news) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        boolean isAdmin = user.getRole().toString().equals("ROLE_ADMIN");
        boolean isAuthor = Objects.equals(news.getAuthor().getId(), userId);
        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("Access denied.");
        }
    }
}
