package interview;

import java.net.http.*;
import java.net.URI;
import java.util.List;
import java.util.concurrent.*;

public class VirtualThreadDownloader {
    private static final HttpClient client = HttpClient.newHttpClient();

    public static CompletableFuture<Integer> downloadSize(String url) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApplyAsync(response -> response.body().length());
    }

    public static void main(String[] args) throws Exception {
        List<String> urls = List.of(
            "https://openjdk.org",
            "https://github.com",
            "https://stackoverflow.com"
        );

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<CompletableFuture<Integer>> futures = urls.stream()
                .map(url -> downloadSize(url).exceptionally(e -> 0))
                .toList();

            int total = futures.stream()
                .map(CompletableFuture::join)
                .reduce(0, Integer::sum);

            System.out.println("Total bytes: " + total);
        }
    }
}