package at.spengergasse.cooking.recipes.service.image;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;

@AllArgsConstructor
@Service
public class ImageService {

    final private MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://minio:9000")
                    .credentials("minioadmin", "devPassword") //TODO: not use root credentials, would need to be generated in docker compose somehow so that it works for everyone
                    .build();

    public String uploadImage(MultipartFile image){
        try {
            // Generate a unique object name based on the current timestamp
            String objectName = Instant.now().toEpochMilli() + "_" + image.getOriginalFilename();
            String bucketName = "images";

            InputStream inputStream = image.getInputStream();
            long size = image.getSize();

            // Upload the image to MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, size, -1)
                            .build()
            );

            // Generate the URL for the uploaded object
            // TODO: Should of course not be localhost in production
            return "http://localhost:9000/images/" + objectName;
        } catch (Exception e) {
            // Handle the exception (e.g., log the error or throw a RuntimeException)
            e.printStackTrace();
            return null;
        }
    }
}
