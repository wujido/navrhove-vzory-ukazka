package cz.wujido.shottrack;

import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.controllers.HomeController;
import cz.wujido.shottrack.controllers.shooting.*;
import cz.wujido.shottrack.controllers.stats.*;
import cz.wujido.shottrack.entities.Request;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

import static java.util.Map.entry;

public class App {
    private final InputStream input;
    private final PrintStream output;
    private final ShotTrackModel model;
    private Controller activeRoute;
    private HashMap<String, Supplier<Controller>> routes;
    private final String dataFileName = "data";

    public App(InputStream input, PrintStream output) {
        ShotTrackModel newModel;
        this.input = input;
        this.output = output;

        try {
            File dataFile = new File(this.dataFileName);
            var in = new FileInputStream(dataFile);
            var inObj = new ObjectInputStream(in);

            newModel = (ShotTrackModel) inObj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            newModel = new ShotTrackModel();
        }

        model = newModel;
    }

    public void Run() {
        routes = getRoutingTable();
        activeRoute = routes.get("home").get();

        Thread keyboard = new Thread(new KeyboardListener());
        keyboard.start();

        executeAction(new Request());
    }

    private void executeAction(Request request) {
        activeRoute.execute(request);
        Request newRequest = activeRoute.provideRequest();

        if (newRequest == null) return;
        if (newRequest.shouldSaveModel()) saveModel();
        if (newRequest.getRoute() == null) return;
        if (!routes.containsKey(newRequest.getRoute()))
            throw new IndexOutOfBoundsException("404 Route not found (" + newRequest.getRoute() + ")");

        activeRoute = routes.get(newRequest.getRoute()).get();
        executeAction(newRequest);
    }

    private void saveModel() {
        try {
            FileOutputStream out = new FileOutputStream(dataFileName);
            ObjectOutputStream objOut = new ObjectOutputStream(out);

            objOut.writeObject(model);
            objOut.flush();
            objOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, Supplier<Controller>> getRoutingTable() {
        return new HashMap<>(Map.ofEntries(
                entry("home", () -> new HomeController(input, output, model)),
                entry("shooting", () -> new ShootingLobbyController(input, output, model)),
                entry("shooting/single", () -> new SingleShootingController(input, output, model)),
                entry("shooting/training", () -> new TrainingShootingController(input, output, model)),
                entry("shooting/training/list", () -> new ViewCreatedTrainingController(input, output, model)),
                entry("shooting/add/old", () -> new AddOldShootingController(input, output, model)),
                entry("shooting/add/shot", () -> new AddShotController(input, output, model)),
                entry("shooting/add/live", () -> new AddLiveShootingController(input, output, model)),
                entry("shooting/add/live/process", () -> new AddLiveShootingProcessController(input, output, model)),
                entry("shooting/view/created", () -> new ViewCreatedOldShootingController(input, output, model)),
                entry("stats", () -> new StatsLobbyController(input, output, model)),
                entry("stats/all", () -> new AllStatsController(input, output, model)),
                entry("stats/training", () -> new TrainingStatsController(input, output, model)),
                entry("stats/training/detail", () -> new TrainingDetailController(input, output, model)),
                entry("stats/solo", () -> new ShootingStatsController(input, output, model)),
                entry("stats/solo/detail", () -> new ShootingDetailController(input, output, model))
        ));
    }


    class KeyboardListener implements Runnable {

        @Override
        public void run() {
            var scanner = new Scanner(input);
            while (activeRoute.isLive()) {
                var res = scanner.nextLine();
                var request = new Request();
                request.setAction(res);
                executeAction(request);
            }

            scanner.close();
        }
    }
}
