package at.technikum.restserver;

import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    @GetMapping(value = "/{image}" ,produces = "image/jpg")
    public @ResponseBody byte[] getImage(@PathVariable String image){
        if(image.contains("..")){
            throw new IllegalArgumentException("bad user tries to hack me :-(");
        }
        try {
            // Retrieve image from the classpath.

            File is = new File("pics/"+image);

            // Prepare buffered image.
            BufferedImage img = ImageIO.read(is);

            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream();

            // Write to output stream
            ImageIO.write(img, "jpg", bao);

            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
