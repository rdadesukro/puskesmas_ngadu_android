package goid.kotajambi.puskesmas_ngadu.model.notif;

import com.google.gson.annotations.SerializedName;

public class IsiItem_notif {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("konten")
	private String konten;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("body")
	private String body;

	@SerializedName("user_status_read")
	private String userStatusRead;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setKonten(String konten){
		this.konten = konten;
	}

	public String getKonten(){
		return konten;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getBody(){
		return body;
	}

	public void setUserStatusRead(String userStatusRead){
		this.userStatusRead = userStatusRead;
	}

	public String getUserStatusRead(){
		return userStatusRead;
	}

	@Override
 	public String toString(){
		return 
			"IsiItem{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",konten = '" + konten + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",body = '" + body + '\'' + 
			",user_status_read = '" + userStatusRead + '\'' + 
			"}";
		}
}