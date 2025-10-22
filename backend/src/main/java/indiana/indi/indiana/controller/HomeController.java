package indiana.indi.indiana.controller;

import indiana.indi.indiana.dto.categories.CategoryDto;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.service.home.DefaultHomeService;
import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

    private final DefaultHomeService homeService;

    @GetMapping
    public List<CategoryDto> getCategoriesList(@AuthenticationPrincipal CustomUserDetails user){
        return homeService.getCategoriesGamesDto(user.getId());
    }
}
