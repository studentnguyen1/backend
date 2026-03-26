package vn.khanguyen.backend.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.khanguyen.backend.domain.res.file.ResUploadFileDTO;
import vn.khanguyen.backend.service.FileService;
import vn.khanguyen.backend.util.annotation.ApiMessage;

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
    public ResponseEntity<ResUploadFileDTO> uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder) throws URISyntaxException, IOException {

        // check folder exist

        // tao folder
        this.fileService.createDirectory(baseURI + folder);

        // upload file
        String fileName = this.fileService.store(file, folder);

        ResUploadFileDTO res = new ResUploadFileDTO(fileName, Instant.now());

        return ResponseEntity.ok().body(res);

    }

}
