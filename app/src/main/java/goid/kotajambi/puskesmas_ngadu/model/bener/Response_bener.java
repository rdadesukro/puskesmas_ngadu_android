package goid.kotajambi.puskesmas_ngadu.model.bener;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response_bener {

	@SerializedName("result")
	private List<ResultItem_bener> result;

	@SerializedName("kode")
	private int kode;

	@SerializedName("search_count")
	private int searchCount;

	public void setResult(List<ResultItem_bener> result){
		this.result = result;
	}

	public List<ResultItem_bener> getResult(){
		return result;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}

	public void setSearchCount(int searchCount){
		this.searchCount = searchCount;
	}

	public int getSearchCount(){
		return searchCount;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"result = '" + result + '\'' + 
			",kode = '" + kode + '\'' + 
			",search_count = '" + searchCount + '\'' + 
			"}";
		}
}