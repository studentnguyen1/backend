package vn.khanguyen.backend.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.khanguyen.backend.domain.res.file.ResUploadFileDTO;
import vn.khanguyen.backend.service.FileService;
import vn.khanguyen.backend.util.annotation.ApiMessage;
import vn.khanguyen.backend.util.error.ResourceNotFoundException;

@RestController
public class FileController {
    @Value("${jobhunter.upload-file.base-uri}")
    private String baseURI;

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/files")
    @ApiMessage("Upload single file")
    public ResponseEntity<ResUploadFileDTO> uploadFile(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam("folder") String folder) throws URISyntaxException, IOException, ResourceNotFoundException {
        // validate file
        if (file == null || file.isEmpty()) {
            throw new ResourceNotFoundException("File is empty. Please upload a file");
        }
        // check type of file
        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
        boolean isValid = allowedExtensions.stream().anyMatch(item -> fileName.toLowerCase().endsWith(item));
        if (!isValid) {
            throw new ResourceNotFoundException("Invalid file extension only allow: " +
                    allowedExtensions.toString());
        }

        // tao folder
        this.fileService.createDirectory(baseURI + folder);

        // upload file
        String fileUpload = this.fileService.store(file, folder);

        ResUploadFileDTO res = new ResUploadFileDTO(fileUpload, Instant.now());

        return ResponseEntity.ok().body(res);

    }

}
