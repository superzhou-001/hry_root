package hry.platform.newuser.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 左侧菜单
 * @author CHINA_LSL
 *
 */
public class LeftMenu implements Serializable{

	private String name; //菜单名

	private String url;//链接

	private String type;//类型

	private String mkey;

	private String pkey;

	private String icon;

	private List<LeftMenu> sonmenus;//子菜单项


	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<LeftMenu> getSonmenus() {
		return sonmenus;
	}

	public void setSonmenus(List<LeftMenu> sonmenus) {
		this.sonmenus = sonmenus;
	}

	public String getMkey() {
		return mkey;
	}

	public void setMkey(String mkey) {
		this.mkey = mkey;
	}

	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}




}
