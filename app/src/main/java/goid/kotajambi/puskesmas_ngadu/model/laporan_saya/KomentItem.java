package goid.kotajambi.puskesmas_ngadu.model.laporan_saya;

import com.google.gson.annotations.SerializedName;

public class KomentItem{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("konten")
	private String konten;

	@SerializedName("lapor_id")
	private int laporId;

	@SerializedName("users_id")
	private int usersId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

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

	public void setLaporId(int laporId){
		this.laporId = laporId;
	}

	public int getLaporId(){
		return laporId;
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

	@Override
 	public String toString(){
		return 
			"KomentItem{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",konten = '" + konten + '\'' + 
			",lapor_id = '" + laporId + '\'' + 
			",users_id = '" + usersId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}