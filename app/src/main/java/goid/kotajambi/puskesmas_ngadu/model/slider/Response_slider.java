package goid.kotajambi.puskesmas_ngadu.model.slider;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response_slider {

	@SerializedName("kode")
	private boolean kode;

	@SerializedName("message")
	private String message;

	@SerializedName("isi")
	private List<IsiItem_slider> isi;

	public boolean isKode(){
		return kode;
	}

	public String getMessage(){
		return message;
	}

	public List<IsiItem_slider> getIsi(){
		return isi;
	}
}