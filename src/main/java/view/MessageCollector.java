package view;

public class MessageCollector {
    private final SceneGenerator sceneController;

    public MessageCollector(SceneGenerator sceneGenerator) {
        this.sceneController = sceneGenerator;
    }

    public void display(String s) {
        sceneController.addToTextArea(s + "\n");
    }
}
