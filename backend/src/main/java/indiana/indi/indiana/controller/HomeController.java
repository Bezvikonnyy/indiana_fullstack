package indiana.indi.indiana.controller;

import indiana.indi.indiana.dto.CategoryDto;
import indiana.indi.indiana.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public List<CategoryDto> getCategoriesList(){
        return homeService.getCategoriesGamesDto();
    }
}
