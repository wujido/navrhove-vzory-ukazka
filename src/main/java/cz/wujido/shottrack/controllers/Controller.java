package cz.wujido.shottrack.controllers;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.entities.Notification;
import cz.wujido.shottrack.entities.Request;
import cz.wujido.shottrack.views.ConfirmationView;
import cz.wujido.shottrack.views.View;

import java.io.InputStream;
import java.io.PrintStream;
import java.text.MessageFormat;

public abstract class Controller {
    protected final PrintStream output;
    protected final InputStream input;
    protected final ShotTrackModel model;
    private View view;
    private boolean isLive = true;
    private Request nextRequest;
    private final ActionConfirmation confirmation = new ActionConfirmation();

    public Controller(InputStream input, PrintStream output, ShotTrackModel model) {
        this.input = input;
        this.output = output;
        this.model = model;
    }

    protected abstract View initializeView();

    protected void setView(View newView) {
        view.hide();
        view = newView;
        view.display();
    }

    protected abstract void executeAction(String action);

    public final void execute(Request request) {
        if (request.getParams() != null) bindParams(request.getParams());
        if (view == null) view = initializeView();

        if (request.getAction() == null) {
            view.display();
            return;
        }

        if (confirmation.isActive)
            confirmation.executeAction(request.getAction());
        else
            executeAction(request.getAction());
    }

    protected void bindParams(int[] params) {
    }

    public Request provideRequest() {
        var res = nextRequest;
        nextRequest = null;
        return res;
    }

    private Request getRequest() {
        if (nextRequest == null) return new Request();
        return nextRequest;
    }

    protected void setRoute(String requestedRoute) {
        var request = getRequest();
        request.setRoute(requestedRoute);
        nextRequest = request;
        view.hide();
    }

    protected void setRoute(String requestedRoute, int... params) {
        var request = getRequest();
        request.setRoute(requestedRoute);
        request.setParams(params);
        nextRequest = request;
        view.hide();
    }

    protected void saveModel() {
        var request = getRequest();
        request.saveModel();
        nextRequest = request;
    }

    public boolean isLive() {
        return isLive;
    }

    protected void die() {
        isLive = false;
        view.hide();
    }

    protected void invalidAction(String action) {
        var text = MessageFormat.format("`{0}` is not valid action", action);
        model.setNotification(text, Notification.Type.ERR);
        refreshView();
    }

    protected void setNotification(String content, Notification.Type type) {
        setNotification(content, type, false);
    }

    protected void setNotification(String content, Notification.Type type, boolean nextRoute) {
        model.setNotification(content, type);
        if (nextRoute) return;

        refreshView();
    }

    private void refreshView() {
        view.hide();
        view.display();
    }

    protected void confirmAction(String question, String onSuccessAction) {
        confirmation.startConfirmation(question, onSuccessAction);
    }

    private class ActionConfirmation {
        public boolean isActive;
        private View oldView;
        private String onSuccessActon;

        public void startConfirmation(String question, String onSuccessAction) {
            isActive = true;
            this.onSuccessActon = onSuccessAction;
            oldView = view;

            var confirmView = new ConfirmationView(output, model, question);
            setView(confirmView);
        }

        public void executeAction(String action) {
            switch (action) {
                case "y" -> {
                    isActive = false;
                    setView(oldView);
                    Controller.this.executeAction(onSuccessActon);
                }
                case "n" -> {
                    isActive = false;
                    setView(oldView);
                }
                default -> invalidAction(action);
            }

        }
    }
}
