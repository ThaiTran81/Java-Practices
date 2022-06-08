package data;

import ui.AppControl;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileObserve implements Runnable {

    private WatchService watchService;

    private Path path;

    private List<Listener> eventManager = new ArrayList<>();



    public FileObserve(String pathfile) throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
        path = Paths.get(pathfile);
        path.getParent().register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
    }

    public void subcribe(Listener listener){
        eventManager.add(listener);
    }

    public void unscribe(Listener listener){
        eventManager.remove(listener);
    }

    public void notifyToSubcriber(){
        for (Listener listener : eventManager) {
            listener.update();
        }
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
//                    appControl.importFile(DataAccess.getInstance().getPath());
                    notifyToSubcriber();
                }
            }
//            key.reset();
        }
    }
}
