package cn.itcast.oa.domain;

/**
 * 岗位
 * 
 * @author whp
 *
 */
public class Role {
	private Long id;
	private String name;
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
