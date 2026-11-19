package org.crudfeed.cli.network;

import lombok.Getter;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateClient {

    @Getter
    private static final UpdateClient INSTANCE = new UpdateClient();

    public void checkAndDownload() {
        String targetAppImagePath = System.getenv("APPIMAGE");
        if (targetAppImagePath == null || targetAppImagePath.isBlank()) {
            return;
        }
        checkAndDownload(getClass().getPackage().getImplementationVersion(), targetAppImagePath);
    }

    public void checkAndDownload(String currentVersion, String targetAppImagePath) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://github.com/CrudFeed/CrudFeed-CLI/releases/latest"))
                    .header("Accept", "application/vnd.github+json")
                    .header("User-Agent", "CrudFeed-Client")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.out.println("Update check failed with status: " + response.statusCode());
                return;
            }

            String json = response.body();

            Matcher tagMatcher = Pattern.compile("\"tag_name\":\\s*\"([^\"]+)\"").matcher(json);
            if (!tagMatcher.find()) return;
            String latestVersion = tagMatcher.group(1);

            if (latestVersion.equals(currentVersion)) {
                System.out.println("App is up to date.");
                return;
            }

            System.out.println("New version found: " + latestVersion);

            Matcher urlMatcher = Pattern.compile("\"browser_download_url\":\\s*\"([^\"]+\\.AppImage)\"").matcher(json);
            if (!urlMatcher.find()) return;
            String downloadUrl = urlMatcher.group(1);

            HttpRequest downloadReq = HttpRequest.newBuilder()
                    .uri(URI.create(downloadUrl))
                    .header("User-Agent", "CrudFeed-Client")
                    .build();

            HttpResponse<InputStream> dlResponse = client.send(downloadReq, HttpResponse.BodyHandlers.ofInputStream());
            if (dlResponse.statusCode() == 200) {
                Path tempFile = Files.createTempFile("crudfeed-update", ".AppImage");

                try (InputStream in = dlResponse.body()) {
                    Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
                }

                tempFile.toFile().setExecutable(true);

                Path target = Path.of(targetAppImagePath);
                Files.move(tempFile, target, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Update downloaded and applied successfully.");
            }

        } catch (Exception e) {
            System.out.println("Update error: " + e.getMessage());
        }
    }
}