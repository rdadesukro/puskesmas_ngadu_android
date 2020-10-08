package goid.kotajambi.puskesmas_ngadu.model.model_berita;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Response_berita {

	@SerializedName("pages")
	private int pages;

	@SerializedName("count")
	private int count;

	@SerializedName("category")
	private Category category;

	@SerializedName("posts")
	private List<PostsItem> posts;

	@SerializedName("status")
	private String status;

	public void setPages(int pages){
		this.pages = pages;
	}

	public int getPages(){
		return pages;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setCategory(Category category){
		this.category = category;
	}

	public Category getCategory(){
		return category;
	}

	public void setPosts(List<PostsItem> posts){
		this.posts = posts;
	}

	public List<PostsItem> getPosts(){
		return posts;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Response_berita{" +
			"pages = '" + pages + '\'' + 
			",count = '" + count + '\'' + 
			",category = '" + category + '\'' + 
			",posts = '" + posts + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}