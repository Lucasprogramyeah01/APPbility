package com.example.APPbility.files.controller;

import com.example.APPbility.files.dto.FileResponse;
import com.example.APPbility.files.model.FileMetadata;
import com.example.APPbility.files.service.StorageService;
import com.example.APPbility.files.util.MimeTypeDetector;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final StorageService storageService;
    private final MimeTypeDetector mimeTypeDetector;

    @Operation(summary = "Permite subir múltiples archivos.",
        description = "Sube varios archivos al servidor y retorna sus metadatos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Archivos subidos correctamente.",
            content = { @Content(mediaType = "multipart/form-data",
                array = @ArraySchema(schema = @Schema(implementation = FileResponse.class)),
                examples = {@ExampleObject(
                    value = """
                            [
                                {
                                    "id": "550e8400-e29b-41d4-a716-446655440000",
                                    "name": "documento.pdf",
                                    "size": 1024,
                                    "type": "application/pdf",
                                    "uri": "http://localhost:8080/download/550e8400-e29b-41d4-a716-446655440000"
                                },
                                {
                                    "id": "550e8400-e29b-41d4-a716-446655440001",
                                    "name": "imagen.jpg",
                                    "size": 2048,
                                    "type": "image/jpeg",
                                    "uri": "http://localhost:8080/download/550e8400-e29b-41d4-a716-446655440001"
                                }
                            ]
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Archivos no válidos.",
            content = @Content),
        @ApiResponse(responseCode = "413",
            description = "El tamaño total de archivos excede el límite permitido.",
            content = @Content),
        @ApiResponse(responseCode = "415",
            description = "Tipo de archivo no soportado.",
            content = @Content)
    })
    @PostMapping("/upload/files")
    public ResponseEntity<?> upload(@RequestPart("files") MultipartFile[] files) {
        List<FileResponse> result = Arrays.stream(files).map(this::uploadFile).toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Permite subir un archivo.",
        description = "Sube un archivo al servidor y retorna sus metadatos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Archivo subido correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = FileResponse.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "id": "550e8400-e29b-41d4-a716-446655440000",
                                "name": "documento.pdf",
                                "size": 1024,
                                "type": "application/pdf",
                                "uri": "http://localhost:8080/download/550e8400-e29b-41d4-a716-446655440000"
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Archivo no válido.",
            content = @Content),
        @ApiResponse(responseCode = "413",
            description = "El tamaño de archivo excede el límite permitido.",
            content = @Content),
        @ApiResponse(responseCode = "415",
            description = "Tipo de archivo no soportado.",
            content = @Content)
    })
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file) {
        FileResponse response = uploadFile(file);

        return ResponseEntity.created(URI.create(response.uri())).body(response);
    }

    private FileResponse uploadFile(MultipartFile multipartFile) {
        FileMetadata fileMetadata = storageService.store(multipartFile);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/download/")
            .path(fileMetadata.getId())
            .toUriString();

        fileMetadata.setURL(uri);

        return FileResponse.builder()
            .id(fileMetadata.getId())
            .name(fileMetadata.getFilename())
            .size(multipartFile.getSize())
            .type(multipartFile.getContentType())
            .uri(uri)
            .build();
    }

    @Operation(summary = "Permite Descargar un archivo.",
        description = "Descarga un archivo buscado por ID del servidor.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Archivo descargado correctamente.",
            content = { @Content(mediaType = MediaType.ALL_VALUE,
                    schema = @Schema(type = "string", format = "binary"))
            }),
        @ApiResponse(responseCode = "404",
            description = "Archivo no encontrado.",
            content = @Content)
    })
    @GetMapping("/download/{id:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String id) {
        Resource resource = storageService.loadAsResource(id);

        String mimeType = mimeTypeDetector.getMimeType(resource);

        return ResponseEntity.status(HttpStatus.OK)
            .header("Content-Type", mimeType)
            .body(resource);
    }

}
