package fr.dams4k.rmm.configs;

public class WordConfig {
    private String wordReplaced = "";
    private String wordReplacer = "";
    private boolean inPlayerList = true;
    private boolean inChat = true;

    public String getWordReplaced() {
        return wordReplaced;
    }

    public String getWordReplacer() {
        return wordReplacer;
    }

    public boolean getInPlayerList() {
        return inPlayerList;
    }

    public boolean getInChat() {
        return inChat;
    }

    public void setWordReplaced(String wordReplaced) {
        this.wordReplaced = wordReplaced;
    }

    public void setWordReplacer(String wordReplacer) {
        this.wordReplacer = wordReplacer;
    }

    public void setInPlayerList(boolean inPlayerList) {
        this.inPlayerList = inPlayerList;
    }

    public void setInChat(boolean inChat) {
        this.inChat = inChat;
    }

    @Override
    public String toString() {
        return "<RenameWordConfig [wordReplacer=" + wordReplacer + ", wordReplaced=" + wordReplaced + ", inChat=" + inChat + ", inPlayerList=" + inPlayerList + "]>";
    }
}
