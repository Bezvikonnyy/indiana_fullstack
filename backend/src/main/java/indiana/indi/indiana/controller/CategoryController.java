package indiana.indi.indiana.controller;

import indiana.indi.indiana.dto.categories.CategoriesPageDto;
import indiana.indi.indiana.dto.categories.CategoryDto;
import indiana.indi.indiana.dto.categories.CategoryPageDto;
import indiana.indi.indiana.service.categories.CategoryForControllerService;
import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryForControllerService service;

    @GetMapping
    public CategoriesPageDto getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user != null ? user.getId() : null;
        return service.getCategoriesPage(page, size, userId);
    }

    @GetMapping("/{categoryId}")
    public CategoryPageDto getCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user != null ? user.getId() : null;
        return service.getCategoryPage(categoryId, page, size, userId);
    }

    @GetMapping("/forGame")
    public Set<CategoryDto> getCategoryForGame(){
        return service.findCategoryForGame();
    }
}
