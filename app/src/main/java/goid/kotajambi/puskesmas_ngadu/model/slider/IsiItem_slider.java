package goid.kotajambi.puskesmas_ngadu.model.slider;

import com.google.gson.annotations.SerializedName;

public class IsiItem_slider {

	@SerializedName("image")
	private String image;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("konten")
	private String konten;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("judul")
	private String judul;

	public String getImage(){
		return image;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getKonten(){
		return konten;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getJudul(){
		return judul;
	}
}