package indiana.indi.indiana.controller;

import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.dto.news.NewsDto;
import indiana.indi.indiana.service.home.DefaultHomeService;
import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

    private final DefaultHomeService homeService;

    @GetMapping("/newGame")
    public Set<CardItemDto> getNewGame(@AuthenticationPrincipal CustomUserDetails user){
        Long userId = user != null ? user.getId() : null;
        return homeService.gamesLatestArrivals(user.getId());
    }

    @GetMapping("/newNews")
    public Set<NewsDto> getNewNews(){
        return homeService.newsLatestArrivals();
    }

    @GetMapping("/gameDiscounts")
    public Set<CardItemDto> getGamesDiscounts(@AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user != null ? user.getId() : null;
        return homeService.gamesDiscounts(user.getId());
    }
}
