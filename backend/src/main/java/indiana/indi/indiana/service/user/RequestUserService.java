package indiana.indi.indiana.service.user;

import indiana.indi.indiana.repository.RequestUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestUserService {

    private final RequestUsersRepository repository;

}
