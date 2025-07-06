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

    public String saveFile(MultipartFile file, String folder) {
        try {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File is empty.");
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.contains("..")) {
                throw new IllegalArgumentException("Invalid file name.");
            }

            String folderPath = UPLOAD_DIR + folder;
            File directory = new File(folderPath);
            if (!directory.exists() && !directory.mkdirs()) {
                throw new IOException("Failed to create directory for file upload.");
            }

            File destination = new File(folderPath + "/" + originalFileName);
            file.transferTo(destination);

            return "/uploads/" + folder + "/" + originalFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Error while saving.", ex);
        }
    }

    public void deleteFileIfExists(String path) {
        if (path == null || path.isBlank()) return;

        File file = new File(System.getProperty("user.dir") + path);
        if (file.exists()) {
            if (!file.delete()) {
                System.err.println("Failed to delete file: " + file.getAbsolutePath());
            }
        }
    }
}
