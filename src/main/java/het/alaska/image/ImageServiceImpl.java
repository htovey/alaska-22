package het.alaska.image;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< Updated upstream
import org.springframework.transaction.annotation.Propagation;
=======
>>>>>>> Stashed changes
import org.springframework.transaction.annotation.Transactional;


@Service("ImageService")
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	ImageDaoImpl imageDao;
	
	@Transactional
	public List<Image> getImageList(String viewId) {
		return imageDao.findImagesByViewId(viewId);
	}
	
<<<<<<< Updated upstream
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
=======
	@Transactional
>>>>>>> Stashed changes
	public void saveImageLinks(List<Image> imageUrlList) {
		for (Image image : imageUrlList) {
			imageDao.persist(image);
		}
	}
}
