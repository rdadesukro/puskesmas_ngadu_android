package goid.kotajambi.puskesmas_ngadu.model.bener;

import com.google.gson.annotations.SerializedName;

public class ResultItem_bener {

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("id_slide")
	private String idSlide;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("nama_slide")
	private String namaSlide;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("status_aktif")
	private String statusAktif;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("urutan")
	private String urutan;

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setIdSlide(String idSlide){
		this.idSlide = idSlide;
	}

	public String getIdSlide(){
		return idSlide;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setNamaSlide(String namaSlide){
		this.namaSlide = namaSlide;
	}

	public String getNamaSlide(){
		return namaSlide;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setStatusAktif(String statusAktif){
		this.statusAktif = statusAktif;
	}

	public String getStatusAktif(){
		return statusAktif;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	public void setUrutan(String urutan){
		this.urutan = urutan;
	}

	public String getUrutan(){
		return urutan;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"keterangan = '" + keterangan + '\'' + 
			",id_slide = '" + idSlide + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",nama_slide = '" + namaSlide + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",status_aktif = '" + statusAktif + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",gambar = '" + gambar + '\'' + 
			",urutan = '" + urutan + '\'' + 
			"}";
		}
}