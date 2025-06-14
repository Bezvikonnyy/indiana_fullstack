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
        String folderPath = UPLOAD_DIR + folder;
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            String fileUrl = folderPath + "/" + fileName;
            File destination = new File(fileUrl);
            file.transferTo(destination);
            return "/uploads/" + folder + "/" + fileName;
        }
        return null;
    }

    public void deleteFileIfExists(String path) {
        if (path == null) return; // Проверка на null
        File file = new File(System.getProperty("user.dir") + path);
        if (file.exists()) {
            file.delete();
        }
    }
}
