import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> userAnswers;
    private transient OutputHandler outputHandler;

    public Response(OutputHandler outputHandler) {
        this.outputHandler = outputHandler;
        this.userAnswers = new ArrayList<>();
    }

    public void addResponse(String answer) {
        userAnswers.add(answer);
    }

    public String getResponseSummary() {
        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < userAnswers.size(); i++) {
            summary.append("Q").append(i + 1).append(": ").append(userAnswers.get(i)).append("\n");
        }
        return summary.toString();
    }
}
