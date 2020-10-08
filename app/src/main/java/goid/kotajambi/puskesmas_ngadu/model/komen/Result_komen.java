package goid.kotajambi.puskesmas_ngadu.model.komen;

import com.google.gson.annotations.SerializedName;

public class Result_komen {

	@SerializedName("foto")
	private String foto;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("konten")
	private String konten;

	@SerializedName("lapor_id")
	private int laporId;

	@SerializedName("name")
	private String name;

	@SerializedName("users_id")
	private int usersId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setKonten(String konten){
		this.konten = konten;
	}

	public String getKonten(){
		return konten;
	}

	public void setLaporId(int laporId){
		this.laporId = laporId;
	}

	public int getLaporId(){
		return laporId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setUsersId(int usersId){
		this.usersId = usersId;
	}

	public int getUsersId(){
		return usersId;
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

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"foto = '" + foto + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",konten = '" + konten + '\'' + 
			",lapor_id = '" + laporId + '\'' + 
			",name = '" + name + '\'' + 
			",users_id = '" + usersId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}