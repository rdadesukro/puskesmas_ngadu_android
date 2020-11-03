package goid.kotajambi.puskesmas_ngadu.model.layanan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response_layanan {

	@SerializedName("kode")
	private boolean kode;

	@SerializedName("message")
	private String message;

	@SerializedName("isi")
	private List<IsiItem_layanan> isi;

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

	public void setIsi(List<IsiItem_layanan> isi){
		this.isi = isi;
	}

	public List<IsiItem_layanan> getIsi(){
		return isi;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"kode = '" + kode + '\'' + 
			",message = '" + message + '\'' + 
			",isi = '" + isi + '\'' + 
			"}";
		}
}