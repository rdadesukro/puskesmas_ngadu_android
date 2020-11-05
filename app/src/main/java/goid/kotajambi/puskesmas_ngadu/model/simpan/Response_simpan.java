package goid.kotajambi.puskesmas_ngadu.model.simpan;

import com.google.gson.annotations.SerializedName;

public class Response_simpan {

	@SerializedName("kode")
	private String kode;


	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@SerializedName("nama")
	private String nama;

	@SerializedName("message")
	private String message;

	public void setKode(String kode){
		this.kode = kode;
	}

	public String getKode(){
		return kode;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"kode = '" + kode + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}