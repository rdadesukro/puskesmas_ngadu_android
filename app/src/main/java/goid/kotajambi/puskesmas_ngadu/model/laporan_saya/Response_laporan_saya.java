package goid.kotajambi.puskesmas_ngadu.model.laporan_saya;

import com.google.gson.annotations.SerializedName;

public class Response_laporan_saya {

	@SerializedName("result")
	private Result_laporan_saya result;

	@SerializedName("kode")
	private boolean kode;

	@SerializedName("message")
	private String message;

	public void setResult(Result_laporan_saya result){
		this.result = result;
	}

	public Result_laporan_saya getResult(){
		return result;
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
			"result = '" + result + '\'' + 
			",kode = '" + kode + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}