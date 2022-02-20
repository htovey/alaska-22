package het.alaska.image;

import java.util.List;

import javax.persistence.EntityManager;
import org.hibernate.query.Query;
import org.hibernate.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("imageDao")
public class ImageDaoImpl implements ImageDao {
	
	@Autowired
	private EntityManager manager;

	@Override
	public List<Image> findImagesByViewId(String viewId) {
		Session session = getSession();
		Query<Image> query = session.getNamedQuery("Image.findByViewId");
		query.setParameter("view_id", viewId);
		List<Image> imageList = query.list();
		return imageList;
	}
	
	@Override
	public void persist(Image image) {
		Session session = getSession();
		session.persist(image);
	}
	
	private Session getSession() {
		return manager.unwrap(Session.class);
	}
}
