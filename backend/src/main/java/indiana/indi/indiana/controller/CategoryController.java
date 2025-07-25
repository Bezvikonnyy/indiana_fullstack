package indiana.indi.indiana.controller;

import indiana.indi.indiana.dto.CategoryDto;

import indiana.indi.indiana.service.categories.CategoryForControllerService;
import indiana.indi.indiana.service.categories.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryForControllerService service;

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return service.findAll();
    }
}
