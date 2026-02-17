package indiana.indi.indiana.controller;

import indiana.indi.indiana.dto.categories.CategoriesPageDto;
import indiana.indi.indiana.dto.categories.CategoryForGameDto;
import indiana.indi.indiana.service.categories.CategoryForControllerService;
import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryForControllerService service;

    @GetMapping
    public CategoriesPageDto getCategories(
            @RequestParam int page,
            @RequestParam int size,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return service.getCategoriesPage(page, size, user.getId());
    }

    @GetMapping("/forGame")
    public Set<CategoryForGameDto> getCategoryForGame(){
        return service.findCategoryForGame();
    }
}
