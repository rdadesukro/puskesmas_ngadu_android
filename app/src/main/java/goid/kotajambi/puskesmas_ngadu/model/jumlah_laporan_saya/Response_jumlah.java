package goid.kotajambi.puskesmas_ngadu.model.jumlah_laporan_saya;

import com.google.gson.annotations.SerializedName;

public class Response_jumlah {

	@SerializedName("jumlah")
	private int jumlah;

	@SerializedName("kode")
	private boolean kode;

	@SerializedName("message")
	private String message;

	public void setJumlah(int jumlah){
		this.jumlah = jumlah;
	}

	public int getJumlah(){
		return jumlah;
	}

	public void setKode(boolean kode){
		this.kode = kode;
	}

	public boolean isKode(){
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
			"jumlah = '" + jumlah + '\'' + 
			",kode = '" + kode + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}