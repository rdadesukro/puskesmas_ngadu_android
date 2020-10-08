package goid.kotajambi.puskesmas_ngadu.model.laporan_saya;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("foto")
	private String foto;

	@SerializedName("no_hp")
	private Object noHp;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("email_verified_at")
	private Object emailVerifiedAt;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("alamat")
	private String alamat;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setNoHp(Object noHp){
		this.noHp = noHp;
	}

	public Object getNoHp(){
		return noHp;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setEmailVerifiedAt(Object emailVerifiedAt){
		this.emailVerifiedAt = emailVerifiedAt;
	}

	public Object getEmailVerifiedAt(){
		return emailVerifiedAt;
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

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",foto = '" + foto + '\'' + 
			",no_hp = '" + noHp + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",email_verified_at = '" + emailVerifiedAt + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}
}