package het.alaska.image;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("ImageService")
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	ImageDaoImpl imageDao;
	
	@Override
	public List<Image> getImageList(String viewId) {
		return imageDao.findImagesByViewId(viewId);
	}
	
	@Override
	public void saveImageLinks(List<Image> imageUrlList) {
		for (Image image : imageUrlList) {
			imageDao.persist(image);
		}
	}
}
