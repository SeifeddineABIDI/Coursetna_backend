package tn.esprit.pidev.services.googledrive;

import com.google.api.services.drive.model.File;
import com.google.common.io.ByteStreams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.dtos.GoogleDriveFolderDTO;
import tn.esprit.pidev.utils.PermissionDetails;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class GoogleDriveFolderService {

    private final GoogleDriveManager googleDriveManager;

    public List<GoogleDriveFolderDTO> findAll() {
        List<File> folders = googleDriveManager.findAllFilesInFolderById("root");
        List<GoogleDriveFolderDTO> googleDriveFolderDTOS = new ArrayList<>();

        if (folders == null) return googleDriveFolderDTOS;

        folders.forEach(folder -> {
            if (folder.getSize() == null) {
                GoogleDriveFolderDTO dto = new GoogleDriveFolderDTO();
                dto.setId(folder.getId());
                dto.setName(folder.getName());
                dto.setLink("https://drive.google.com/drive/u/0/folders/" + folder.getId());
                googleDriveFolderDTOS.add(dto);
            }
        });

        return googleDriveFolderDTOS;
    }

    public String create(String folderName, String parentId) {
        return googleDriveManager.createFolder(folderName, parentId);
    }

    public String getFolderId(String folderName) {
        return googleDriveManager.getFolderId(folderName);
    }

    public void delete(String id) {
        googleDriveManager.deleteFileOrFolderById(id);
    }

    public byte[] download(String folderId) {
        List<File> files = googleDriveManager.findAllFilesInFolderById(folderId);
        return zipFiles(files);
    }

    private byte[] zipFiles(List<File> files) {

        byte[] result;

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            for (File file : files) {
                try (InputStream fileInputStream = googleDriveManager.getFileAsInputStream(file.getId())) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOutputStream.putNextEntry(zipEntry);
                    ByteStreams.copy(fileInputStream, zipOutputStream);
                }
            }

            zipOutputStream.close();
            byteArrayOutputStream.close();
            result = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void moveFolderToAnother(String fromFolderId, String toFolderId) {
        googleDriveManager.move(fromFolderId, toFolderId);
    }

    public void copyFolderToAnother(String fromId, String toId) {
        File fromFolder = googleDriveManager.findFolderById(fromId);

        File newFolder = new File();
        newFolder.setName(fromFolder.getName());
        newFolder.setParents(List.of(toId));
        newFolder.setMimeType("application/vnd.google-apps.folder");

        List<File> folders = googleDriveManager.findAllFoldersInFolderById(fromId);
        List<File> files = googleDriveManager.findAllFilesInFolderById(fromId);
        files.forEach(file -> googleDriveManager.copy(file.getId(), newFolder.getId()));
        folders.forEach(folder -> copyFolderToAnother(folder.getId(), newFolder.getId()));
    }

    public void shareFolder(String folderId, String gmail) {
        PermissionDetails permissionDetails = PermissionDetails
                .builder()
                .emailAddress(gmail)
                .type("user")
                .role("reader")
                .build();

        googleDriveManager.createPermissionForEmail(folderId, permissionDetails);
    }
}
