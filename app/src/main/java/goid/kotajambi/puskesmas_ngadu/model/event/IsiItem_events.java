package goid.kotajambi.puskesmas_ngadu.model.event;

import com.google.gson.annotations.SerializedName;

public class IsiItem_events {

	@SerializedName("tgl_akhir")
	private String tglAkhir;

	@SerializedName("thumbnail")
	private String thumbnail;

	@SerializedName("nama")
	private String nama;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("konten")
	private String konten;

	@SerializedName("lokasi")
	private String lokasi;

	@SerializedName("waktu")
	private String waktu;

	@SerializedName("tgl_mulai")
	private String tglMulai;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("deleted_at")
	private String deletedAt;

	@SerializedName("status")
	private String status;

	public void setTglAkhir(String tglAkhir){
		this.tglAkhir = tglAkhir;
	}

	public String getTglAkhir(){
		return tglAkhir;
	}

	public void setThumbnail(String thumbnail){
		this.thumbnail = thumbnail;
	}

	public String getThumbnail(){
		return thumbnail;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

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

	public void setLokasi(String lokasi){
		this.lokasi = lokasi;
	}

	public String getLokasi(){
		return lokasi;
	}

	public void setWaktu(String waktu){
		this.waktu = waktu;
	}

	public String getWaktu(){
		return waktu;
	}

	public void setTglMulai(String tglMulai){
		this.tglMulai = tglMulai;
	}

	public String getTglMulai(){
		return tglMulai;
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

	public void setDeletedAt(String deletedAt){
		this.deletedAt = deletedAt;
	}

	public String getDeletedAt(){
		return deletedAt;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"IsiItem{" + 
			"tgl_akhir = '" + tglAkhir + '\'' + 
			",thumbnail = '" + thumbnail + '\'' + 
			",nama = '" + nama + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",konten = '" + konten + '\'' + 
			",lokasi = '" + lokasi + '\'' + 
			",waktu = '" + waktu + '\'' + 
			",tgl_mulai = '" + tglMulai + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}