package goid.kotajambi.puskesmas_ngadu.model.login;

import com.google.gson.annotations.SerializedName;

public class Response_login {

	@SerializedName("access_token")
	private String accessToken;

	@SerializedName("expires_at")
	private String expiresAt;

	@SerializedName("foto")
	private String foto;

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@SerializedName("nama")
	private String nama;



	@SerializedName("no_hp")
	private String noHp;

	@SerializedName("kode")
	private String kode;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("token_type")
	private String tokenType;

	@SerializedName("message")
	private String message;

	@SerializedName("alamat")
	private String alamat;

	public String getAccessToken(){
		return accessToken;
	}

	public String getExpiresAt(){
		return expiresAt;
	}

	public String getFoto(){
		return foto;
	}

	public String getNoHp(){
		return noHp;
	}

	public String getKode(){
		return kode;
	}

	public int getIdUser(){
		return idUser;
	}

	public String getTokenType(){
		return tokenType;
	}

	public String getMessage(){
		return message;
	}

	public String getAlamat(){
		return alamat;
	}
}