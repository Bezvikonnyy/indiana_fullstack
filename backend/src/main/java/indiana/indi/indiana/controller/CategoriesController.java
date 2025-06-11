package indiana.indi.indiana.controller;

import indiana.indi.indiana.service.game.CRUDGameServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoriesController {

    private final CRUDGameServiceImpl gameService;
}
