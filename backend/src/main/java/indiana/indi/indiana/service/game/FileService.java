package indiana.indi.indiana.service.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    public String saveFile(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Файл пустой или не передан");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.contains("..")) {
            throw new IllegalArgumentException("Недопустимое имя файла");
        }

        String folderPath = UPLOAD_DIR + folder;
        File directory = new File(folderPath);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Не удалось создать директорию для загрузки файлов");
        }

        File destination = new File(folderPath + "/" + originalFileName);
        file.transferTo(destination);

        return "/uploads/" + folder + "/" + originalFileName;
    }

    public void deleteFileIfExists(String path) {
        if (path == null || path.isBlank()) return;

        File file = new File(System.getProperty("user.dir") + path);
        if (file.exists()) {
            if (!file.delete()) {
                System.err.println("Не удалось удалить файл: " + file.getAbsolutePath());
            }
        }
    }
}

