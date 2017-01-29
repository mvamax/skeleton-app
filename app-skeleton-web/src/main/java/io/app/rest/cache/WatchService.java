package io.app.rest.cache;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WatchService implements CommandLineRunner {

    private static final String folder = "D:/workspace/app-skeleton/app-skeleton-web/";

    @Override
    public void run(String... args) throws Exception {

	final Path path = FileSystems.getDefault().getPath(folder);
	try (final java.nio.file.WatchService watchService = FileSystems.getDefault().newWatchService()) {
	    final WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
	    while (true) {
		final WatchKey wk = watchService.take();
		for (final WatchEvent<?> event : wk.pollEvents()) {
		    final Path changed = (Path) event.context();
		    System.out.println(changed);
		}
		// reset the key
		final boolean valid = wk.reset();
		if (!valid) {
		    System.out.println("Key has been unregisterede");
		}
	    }
	}

    }
}
