package indiana.indi.indiana.service.categories;

import indiana.indi.indiana.dto.categories.*;
import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.dtoInterface.games.CardItemDtoInter;
import indiana.indi.indiana.mapper.categories.CategoryMapper;
import indiana.indi.indiana.mapper.games.CardItemMapper;
import indiana.indi.indiana.repository.categories.CategoryRepository;
import indiana.indi.indiana.repository.games.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final CardItemMapper cardMapper;

    public CategoriesPageDto getCategoriesPage(int page, int size, Long userId) {

        Page<CategoryDto> categoriesPage =
                repository.findCategoriesForPage(
                        PageRequest.of(page, size, Sort.by("title"))
                ).map(mapper::toDto);

        List<Long> categoriesIds = categoriesPage.stream().map(CategoryDto::id).toList();

        List<CardItemDto> games =
                gameRepository.findGamesByCategory(userId, categoriesIds, LocalDateTime.now())
                        .stream().map(cardMapper::toDto).toList();
        Map<Long, List<CardItemDto>> gamesByCategory = games.stream()
                .collect(Collectors.groupingBy(CardItemDto::categoryId));

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

    public CategoryPageDto getCategoryPage(Long categoryId, int page, int size, Long userId) {
        Page<CardItemDtoInter> games = gameRepository.findByCategoryId(
                userId,
                categoryId,
                LocalDateTime.now(),
                PageRequest.of(page, size));

        List<CardItemDto> cardItems = games.stream()
                .map(cardMapper::toDto)
                .toList();

        return new CategoryPageDto(
                cardItems,
                games.getNumber(),
                games.getTotalPages(),
                games.getTotalElements()
        );
    }

    public Set<CategoryDto> findCategoryForGame(){
        return service.findCategoryForGame();
    }
}
