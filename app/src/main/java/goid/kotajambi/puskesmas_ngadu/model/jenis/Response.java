package goid.kotajambi.puskesmas_ngadu.model.jenis;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("kode")
	private boolean kode;

	@SerializedName("message")
	private String message;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
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