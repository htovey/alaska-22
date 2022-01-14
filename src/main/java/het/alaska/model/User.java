package het.alaska.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")

@NamedQueries({
    @NamedQuery(name="User.authenticate", query="SELECT u FROM User u WHERE u.userName = :user_name and u.password = :password"),
    @NamedQuery(name="User.findUser", query="SELECT u FROM User u WHERE u.userName = :user_name"),
    @NamedQuery(name="User.updatePassword", query="UPDATE User u SET u.password = :password WHERE u.userName = :user_name"),
    @NamedQuery(name="User.updateUser", query="UPDATE User u SET u.userName = :user_name, u.role = :role WHERE u.id = :id")
})

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name="create_dt")
	private Date createDt;

	@Column(name="role")
	private String role;

	@Column(name="save_dt")
	private Date saveDt;

	@Column(name="user_name")
	private String userName;
	
	@Column(name="password")
	private String password;

	public User() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getSaveDt() {
		return this.saveDt;
	}

	public void setSaveDt(Date saveDt) {
		this.saveDt = saveDt;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}