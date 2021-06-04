package httpserver;

public class TestWebPage extends Webpage {
    @Override
    protected String getTitle() {
        return "test";
    }

    @Override
    protected String getBody() {
        return "\t\thello web\n";
    }
}
