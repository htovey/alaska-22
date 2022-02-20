package het.alaska.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="img_url")
@NamedQuery(name="Image.findByViewId", query="select i from Image i where i.viewId = :view_id")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private String id;
	
	@Column(name = "view_id") 
	private String viewId;
	
	@Column
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getUrl() {
		return url;
	}

	public void setImg_url(String url) {
		this.url = url;
	}
}
