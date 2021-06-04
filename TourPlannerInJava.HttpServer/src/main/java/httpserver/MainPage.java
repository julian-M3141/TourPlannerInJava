package httpserver;

public class MainPage extends Webpage{
    @Override
    protected String getTitle() {
        return "TourPlanner";
    }

    @Override
    protected String getBody() {
        StringBuilder content = new StringBuilder();
        content.append("\t\t<div class=\"container\">\n");
        content.append("\t\t\t<div class=\"row\">\n");
        for(var tour : manager.getAll()){
            content.append("\t\t\t\t<div class=\"card col-sm-3 imagehover\"><a href=\"/tours/").append(tour.getId()).append("\">\n");
            content.append("\t\t\t\t\t<div class=\"card-body\">\n");
            content.append("\t\t\t\t\t\t<h4>"+tour.getName()+"</h4>\n");
            content.append("\t\t\t\t\t\t<img src=\"/pics/"+tour.getImage()+"\">\n");
            content.append("\t\t\t\t\t</div>\n");
            content.append("\t\t\t\t</a></div>\n");
        }
        content.append("\t\t\t</div>\n");
        content.append("\t\t</div>\n");
        return content.toString();
    }
}
