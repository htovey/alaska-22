package het.alaska.image;

import java.util.List;

public interface ImageService {

	List<Image> getImageList(String viewId);

	void saveImageLinks(List<Image> imageUrlList);
	
}
