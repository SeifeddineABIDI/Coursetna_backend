package tn.esprit.pidev.services.googledrive;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.config.GoogleDriveConfig;
import tn.esprit.pidev.utils.PermissionDetails;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleDriveManager {

    private final GoogleDriveConfig googleDriveConfig;

    public List<File> findAll() {
        try {
            FileList result = googleDriveConfig
                    .getDrive()
                    .files()
                    .list()
                    .setFields("nextPageToken, files(id, name, size, thumbnailLink, shared)")
                    .execute();
            return result.getFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<File> findAllFilesInFolderById(String folderId) {
        try {
            folderId = folderId == null ? "root" : folderId;
            String query = "'" + folderId + "' in parents";
            FileList result = googleDriveConfig
                    .getDrive()
                    .files()
                    .list()
                    .setQ(query)
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name, size, thumbnailLink, shared)")
                    .execute();
            return result.getFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<File> findAllFoldersInFolderById(String folderId) {
        try {

            String query = String.format("'%s' in parents and mimeType = 'application/vnd.google-apps.folder' and trashed = false", folderId);

            return googleDriveConfig.getDrive()
                    .files()
                    .list()
                    .setQ(query)
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name)")
                    .execute().getFiles();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void download(String fileId, OutputStream outputStream) {
        try {
            googleDriveConfig.getDrive().files().get(fileId).executeMediaAndDownloadTo(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Permission setPermission(PermissionDetails permissionDetails) {
        Permission permission = new Permission();

        if (!permissionDetails.getEmailAddress().isEmpty()) {
            permission.setEmailAddress(permissionDetails.getEmailAddress());
        }

        return permission
                .setType(permissionDetails.getType())
                .setRole(permissionDetails.getRole());
    }

    public void createPermissionForEmail(String id, PermissionDetails permissionDetails) {
        Permission permission = setPermission(permissionDetails);

        try {
            googleDriveConfig.getDrive()
                    .permissions()
                    .create(id, permission)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String uploadFile(MultipartFile multipartFile, String folderName, PermissionDetails permissionDetails) {
        if (multipartFile == null) return null;

        File file = new File();
        file.setParents(Collections.singletonList(getFolderId(folderName)));
        file.setName(multipartFile.getOriginalFilename());

        try {
            File uploadedFile = googleDriveConfig
                    .getDrive()
                    .files()
                    .create(file, new InputStreamContent(multipartFile.getContentType(), new ByteArrayInputStream(multipartFile.getBytes())))
                    .setFields("id")
                    .execute();

            if (!"private".equals(permissionDetails.getType()) && !"private".equals(permissionDetails.getRole())) {
                googleDriveConfig
                        .getDrive()
                        .permissions()
                        .create(uploadedFile.getId(), setPermission(permissionDetails));
            }

            return uploadedFile.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFolderId(String folderName) {
        String parentId = null;

        for (String name : folderName.split("/")) {
            parentId = findOrCreateFolder(parentId, name);
        }

        return parentId;
    }

    public File findFolderById(String folderId) {
        try {
            return googleDriveConfig.getDrive().files().get(folderId).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String findOrCreateFolder(String parentId, String folderName) {
        String folderId = findFolderById(parentId, folderName);

        if (folderId != null) {
            return folderId;
        }

        File folder = new File();
        folder.setMimeType("application/vnd.google-apps.folder");
        folder.setName(folderName);

        if (parentId != null) {
            folder.setParents(Collections.singletonList(parentId));
        }

        try {
            return googleDriveConfig.getDrive()
                    .files().create(folder).setFields("id").execute().getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String findFolderById(String parentId, String folderName) {
        String folderId = null;
        String pageToken = null;

        do {
            String query = " mimeType = 'application/vnd.google-apps.folder' ";

            query = parentId == null ? query + " and 'root' in parents" :
                    query + " and '" + parentId + "' in parents";

            try {
                FileList result = googleDriveConfig.getDrive()
                        .files()
                        .list()
                        .setQ(query)
                        .setSpaces("drive")
                        .setFields("nextPageToken, files(id, name)")
                        .setPageToken(pageToken)
                        .execute();
                for (File file : result.getFiles()) {
                    if (file.getName().equalsIgnoreCase(folderName)) {
                        folderId = file.getId();
                    }
                }
                pageToken = result.getNextPageToken();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } while (pageToken != null && folderName == null);

        return folderId;
    }

    public void deleteFileOrFolderById(String id) {
        try {
            googleDriveConfig.getDrive().files().delete(id).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream getFileAsInputStream(String fileID) {
        try {
            return googleDriveConfig.getDrive()
                    .files()
                    .get(fileID)
                    .executeMediaAsInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void copy(String fileId, String folderId) {

        if (folderId == null) {
            throw new RuntimeException("Folder not found.");
        }

        try {
            googleDriveConfig
                    .getDrive()
                    .files()
                    .copy(fileId, new File().setParents(List.of(folderId)))
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void move(String fileId, String folderId) {

        if (folderId == null) {
            throw new RuntimeException("Folder not found.");
        }

        try {
            File file = googleDriveConfig.getDrive()
                    .files()
                    .get(fileId)
                    .setFields("parents")
                    .execute();

            StringBuilder previousParents = new StringBuilder();
            file.getParents().forEach(parent -> {
                previousParents.append(parent);
                previousParents.append(',');
            });

            googleDriveConfig.getDrive().files().update(fileId, null)
                    .setAddParents(folderId)
                    .setRemoveParents(previousParents.toString())
                    .setFields("id, parents")
                    .execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String createFolder(String folderName, String parentId) {

        File fileMetadata = new File();

        if (parentId != null) {
            fileMetadata.setParents(List.of(parentId));
        }

        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        try {

            return googleDriveConfig
                    .getDrive()
                    .files()
                    .create(fileMetadata)
                    .setFields("id")
                    .execute()
                    .getId();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
