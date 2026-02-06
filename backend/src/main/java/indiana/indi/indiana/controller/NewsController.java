package indiana.indi.indiana.controller;

import indiana.indi.indiana.dto.news.NewsDto;
import indiana.indi.indiana.service.news.CRUDNewsService;
import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    private final CRUDNewsService service;

    @GetMapping("/{newsId}")
    public NewsDto getNews(@PathVariable("newsId") Long newsId,
                           @AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails != null ? userDetails.getId() : null;
        return service.getNews(newsId);
    }

    @PostMapping("/newNews")
    public NewsDto createNews(@Valid String title,
                              @Valid String content,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        return service.createNews(title, content, imageFile, userId);
    }

    @PostMapping("/edit/{newsId}")
    public NewsDto editNews(
            @PathVariable("newsId") Long newsId,
            @Valid String title,
            @Valid String content,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails != null ? userDetails.getId() : null;
        return service.updatedNews(
                newsId,
                title,
                content,
                imageFile,
                userId
        );
    }

    @DeleteMapping("/delete/{newsId}")
    public void deleteNews(
            @PathVariable("newsId") Long newsId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        service.deleteNews(newsId, userId);
    }
}
