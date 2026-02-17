package indiana.indi.indiana.service.categories;

import indiana.indi.indiana.dto.categories.CategoriesPageDto;
import indiana.indi.indiana.dto.categories.CategoryDto;
import indiana.indi.indiana.dto.categories.CategoryForGameDto;
import indiana.indi.indiana.dto.categories.CategoryWithGamesDto;
import indiana.indi.indiana.dto.games.GameWithCategoryDto;
import indiana.indi.indiana.mapper.categories.CategoryMapper;
import indiana.indi.indiana.mapperInterface.games.GameWithCategoryMapper;
import indiana.indi.indiana.repository.categories.CategoryRepository;
import indiana.indi.indiana.repository.games.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryForControllerService {

    private final CategoryRepository repository;
    private final GameRepository gameRepository;
    private final CategoryServiceImpl service;
    private final CategoryMapper mapper;
    private final GameWithCategoryMapper gameMapper;

    public CategoriesPageDto getCategoriesPage(int page, int size, Long userId) {

        Page<CategoryDto> categoriesPage =
                repository.findCategoriesForPage(
                        PageRequest.of(page, size, Sort.by("title"))
                ).map(mapper::toDto);

        List<Long> categoriesIds = categoriesPage.stream().map(CategoryDto::id).toList();

        List<GameWithCategoryDto> games =
                gameRepository.findGamesByCategory(userId, categoriesIds)
                        .stream().map(gameMapper::toDto).toList();
        Map<Long, List<GameWithCategoryDto>> gamesByCategory = games.stream()
                .collect(Collectors.groupingBy(GameWithCategoryDto::categoryId));

        List<CategoryWithGamesDto> result = categoriesPage.getContent()
                .stream()
                .map(category -> new CategoryWithGamesDto(
                            category.id(),
                            category.title(),
                            gamesByCategory.getOrDefault(category.id(), List.of())
                    ))
                .toList();

        return new CategoriesPageDto(
                result,
                categoriesPage.getNumber(),
                categoriesPage.getTotalPages(),
                categoriesPage.getTotalElements()
        );
    }

    public Set<CategoryForGameDto> findCategoryForGame(){
        return service.findCategoryForGame();
    }
}
