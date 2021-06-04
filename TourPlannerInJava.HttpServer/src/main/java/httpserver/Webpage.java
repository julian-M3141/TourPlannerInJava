package httpserver;

import at.technikum.businessLayer.manager.AppManagerFactory;
import at.technikum.businessLayer.manager.IAppManger;

public abstract class Webpage {
    protected IAppManger manager = AppManagerFactory.getManager();
    public final String create(){
        StringBuilder content = new StringBuilder();
        content.append(createHead());
        content.append(getBody());
        content.append(createEnd());
        return content.toString();
    }
    private String createHead(){
        StringBuilder content = new StringBuilder();
        content.append("<!DOCTYPE html>\n");
        content.append("<html>\n");
        content.append("\t<head>\n");
        content.append(String.format("\t\t<title>%s</title>\n", getTitle()));
        content.append("\t\t<meta charset=\"utf-8\">\n");
        content.append("\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
        content.append("\t\t<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">\n");
        content.append("\t\t<link rel=\"stylesheet\" href=\"/style/meinCSS\">\n");
        content.append("\t\t<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n");
        content.append("\t\t<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js\"></script>\n");
        content.append("\t\t<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js\"></script>\n");
        content.append("\t</head>\n");
        content.append("\t<body>\n");
        return content.toString();
    }
    private String createEnd(){
        StringBuilder content = new StringBuilder();
        content.append("\t</body>\n");
        content.append("</html>\n");
        return content.toString();
    }
    protected abstract String getTitle();
    protected abstract String getBody();

}
