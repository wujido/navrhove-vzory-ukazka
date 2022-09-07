package cz.wujido.shottrack.entities;

public class Request {
    private String route = null;
    private String action = null;
    private boolean saveModel = false;
    private int[] params;


    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int[] getParams() {
        return params;
    }

    public void setParams(int... params) {
        this.params = params;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean shouldSaveModel() {
        return saveModel;
    }

    public void saveModel() {
        saveModel = true;
    }
}
