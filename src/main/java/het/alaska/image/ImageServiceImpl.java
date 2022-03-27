package het.alaska.image;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("ImageService")
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	ImageDaoImpl imageDao;
	
	@Override
	public List<Image> getImageList(String viewId) {
		return imageDao.findImagesByViewId(viewId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void saveImageLinks(List<Image> imageUrlList) {
		for (Image image : imageUrlList) {
			imageDao.persist(image);
		}
	}
}
