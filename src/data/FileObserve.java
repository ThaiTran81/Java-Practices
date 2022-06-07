package data;

import ui.AppControl;

import java.io.IOException;
import java.nio.file.*;

public class FileObserve implements Runnable {

    private WatchService watchService;

    private Path path;
    private AppControl appControl;

    public FileObserve(String pathfile, AppControl appControl) throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
        this.appControl = appControl;
        path = Paths.get(pathfile);
        path.getParent().register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
    }

    @Override
    public void run() {
        WatchKey key = null;
        try {
            key = watchService.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (key != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.context().equals(path.getFileName())) {
                    appControl.importFile(DataAccess.getInstance().getPath());
                }
            }
//            key.reset();
        }
    }
}
