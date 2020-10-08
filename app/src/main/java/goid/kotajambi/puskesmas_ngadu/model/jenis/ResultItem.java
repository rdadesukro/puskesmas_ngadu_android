package goid.kotajambi.puskesmas_ngadu.model.jenis;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("nama_jenis")
	private String namaJenis;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
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

	public void setNamaJenis(String namaJenis){
		this.namaJenis = namaJenis;
	}

	public String getNamaJenis(){
		return namaJenis;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",nama_jenis = '" + namaJenis + '\'' + 
			"}";
		}
}