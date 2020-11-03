package goid.kotajambi.puskesmas_ngadu.model.laporan_komen;

import com.google.gson.annotations.SerializedName;

public class ResultItem_laporan_komen {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("kode")
	private String kode;

	@SerializedName("foto_laporan")
	private String fotoLaporan;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("isi_laporan")
	private String isiLaporan;

	@SerializedName("id")
	private int id;

	@SerializedName("jumlah_koment")
	private int jumlahKoment;

	@SerializedName("judul")
	private String judul;

	@SerializedName("user")
	private User user;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setKode(String kode){
		this.kode = kode;
	}

	public String getKode(){
		return kode;
	}

	public void setFotoLaporan(String fotoLaporan){
		this.fotoLaporan = fotoLaporan;
	}

	public String getFotoLaporan(){
		return fotoLaporan;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setIsiLaporan(String isiLaporan){
		this.isiLaporan = isiLaporan;
	}

	public String getIsiLaporan(){
		return isiLaporan;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setJumlahKoment(int jumlahKoment){
		this.jumlahKoment = jumlahKoment;
	}

	public int getJumlahKoment(){
		return jumlahKoment;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",kode = '" + kode + '\'' + 
			",foto_laporan = '" + fotoLaporan + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",isi_laporan = '" + isiLaporan + '\'' + 
			",id = '" + id + '\'' + 
			",jumlah_koment = '" + jumlahKoment + '\'' + 
			",judul = '" + judul + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}