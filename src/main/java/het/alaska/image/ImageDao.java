package het.alaska.image;

import java.util.List;

public interface ImageDao {

	List<Image> findImagesByViewId(String viewId);

	void persist(Image image);

}
