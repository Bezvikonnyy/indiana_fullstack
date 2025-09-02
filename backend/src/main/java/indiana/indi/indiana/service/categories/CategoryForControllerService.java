package indiana.indi.indiana.service.categories;

import indiana.indi.indiana.dto.categories.CategoryDto;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.mapper.categories.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryForControllerService {

    private final CategoryServiceImpl service;

    private final CategoryMapper mapper;

    public List<CategoryDto> findAll(User user) {
        return service.findAll().stream()
                .map(cat -> mapper.toDto(cat, user))
                .collect(Collectors.toList());
    }
}
