package het.alaska.image;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import het.alaska.controller.PersonController;
import het.alaska.image.ImageService;

@Controller
@RequestMapping(path="/image")
public class ImageController {
	
	public final Log log = LogFactory.getLog(ImageController.class);
	
	@Autowired
	ImageServiceImpl imageService;

	@RequestMapping(value = "/imageList", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Image> getImageList(@RequestParam int viewId) {
		List<Image> imageList = imageService.getImageList(String.valueOf(viewId));
		return imageList;
	};
	
	@RequestMapping(value="/saveLinks", method = RequestMethod.PUT)
<<<<<<< Updated upstream
	public @ResponseBody String saveImageLinks(@RequestBody List<Image> imageUrlList) {
=======
	public String saveImageLinks(@RequestBody List<Image> imageList) {
>>>>>>> Stashed changes
		String msg = "success";
		try {
			imageService.saveImageLinks(imageList);
		} catch (Exception ex) {
			log.error(ex);
		}
		return msg;
	}
}
