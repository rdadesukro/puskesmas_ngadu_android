package goid.kotajambi.puskesmas_ngadu.model.model_berita;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CustomFields{

	@SerializedName("summary")
	private List<String> summary;

	@SerializedName("jnews_social_meta")
	private List<String> jnewsSocialMeta;

	@SerializedName("bad")
	private List<String> bad;

	@SerializedName("post_subtitle")
	private List<String> postSubtitle;

	@SerializedName("rating")
	private List<String> rating;

	@SerializedName("type")
	private List<String> type;

	@SerializedName("good")
	private List<String> good;

	@SerializedName("jnews_social_counter_all")
	private List<String> jnewsSocialCounterAll;

	@SerializedName("jnews_single_post")
	private List<String> jnewsSinglePost;

	@SerializedName("jnews_social_counter_last_update")
	private List<String> jnewsSocialCounterLastUpdate;

	@SerializedName("jnews_post_split")
	private List<String> jnewsPostSplit;

	@SerializedName("enable_review")
	private List<String> enableReview;

	@SerializedName("price")
	private List<String> price;

	@SerializedName("jnews_price_lowest")
	private List<String> jnewsPriceLowest;

	@SerializedName("jnews_social_counter_total")
	private List<String> jnewsSocialCounterTotal;

	@SerializedName("name")
	private List<String> name;

	@SerializedName("jnews_primary_category")
	private List<String> jnewsPrimaryCategory;

	@SerializedName("jnews_review_fields")
	private List<String> jnewsReviewFields;

	public void setSummary(List<String> summary){
		this.summary = summary;
	}

	public List<String> getSummary(){
		return summary;
	}

	public void setJnewsSocialMeta(List<String> jnewsSocialMeta){
		this.jnewsSocialMeta = jnewsSocialMeta;
	}

	public List<String> getJnewsSocialMeta(){
		return jnewsSocialMeta;
	}

	public void setBad(List<String> bad){
		this.bad = bad;
	}

	public List<String> getBad(){
		return bad;
	}

	public void setPostSubtitle(List<String> postSubtitle){
		this.postSubtitle = postSubtitle;
	}

	public List<String> getPostSubtitle(){
		return postSubtitle;
	}

	public void setRating(List<String> rating){
		this.rating = rating;
	}

	public List<String> getRating(){
		return rating;
	}

	public void setType(List<String> type){
		this.type = type;
	}

	public List<String> getType(){
		return type;
	}

	public void setGood(List<String> good){
		this.good = good;
	}

	public List<String> getGood(){
		return good;
	}

	public void setJnewsSocialCounterAll(List<String> jnewsSocialCounterAll){
		this.jnewsSocialCounterAll = jnewsSocialCounterAll;
	}

	public List<String> getJnewsSocialCounterAll(){
		return jnewsSocialCounterAll;
	}

	public void setJnewsSinglePost(List<String> jnewsSinglePost){
		this.jnewsSinglePost = jnewsSinglePost;
	}

	public List<String> getJnewsSinglePost(){
		return jnewsSinglePost;
	}

	public void setJnewsSocialCounterLastUpdate(List<String> jnewsSocialCounterLastUpdate){
		this.jnewsSocialCounterLastUpdate = jnewsSocialCounterLastUpdate;
	}

	public List<String> getJnewsSocialCounterLastUpdate(){
		return jnewsSocialCounterLastUpdate;
	}

	public void setJnewsPostSplit(List<String> jnewsPostSplit){
		this.jnewsPostSplit = jnewsPostSplit;
	}

	public List<String> getJnewsPostSplit(){
		return jnewsPostSplit;
	}

	public void setEnableReview(List<String> enableReview){
		this.enableReview = enableReview;
	}

	public List<String> getEnableReview(){
		return enableReview;
	}

	public void setPrice(List<String> price){
		this.price = price;
	}

	public List<String> getPrice(){
		return price;
	}

	public void setJnewsPriceLowest(List<String> jnewsPriceLowest){
		this.jnewsPriceLowest = jnewsPriceLowest;
	}

	public List<String> getJnewsPriceLowest(){
		return jnewsPriceLowest;
	}

	public void setJnewsSocialCounterTotal(List<String> jnewsSocialCounterTotal){
		this.jnewsSocialCounterTotal = jnewsSocialCounterTotal;
	}

	public List<String> getJnewsSocialCounterTotal(){
		return jnewsSocialCounterTotal;
	}

	public void setName(List<String> name){
		this.name = name;
	}

	public List<String> getName(){
		return name;
	}

	public void setJnewsPrimaryCategory(List<String> jnewsPrimaryCategory){
		this.jnewsPrimaryCategory = jnewsPrimaryCategory;
	}

	public List<String> getJnewsPrimaryCategory(){
		return jnewsPrimaryCategory;
	}

	public void setJnewsReviewFields(List<String> jnewsReviewFields){
		this.jnewsReviewFields = jnewsReviewFields;
	}

	public List<String> getJnewsReviewFields(){
		return jnewsReviewFields;
	}

	@Override
 	public String toString(){
		return 
			"CustomFields{" + 
			"summary = '" + summary + '\'' + 
			",jnews_social_meta = '" + jnewsSocialMeta + '\'' + 
			",bad = '" + bad + '\'' + 
			",post_subtitle = '" + postSubtitle + '\'' + 
			",rating = '" + rating + '\'' + 
			",type = '" + type + '\'' + 
			",good = '" + good + '\'' + 
			",jnews_social_counter_all = '" + jnewsSocialCounterAll + '\'' + 
			",jnews_single_post = '" + jnewsSinglePost + '\'' + 
			",jnews_social_counter_last_update = '" + jnewsSocialCounterLastUpdate + '\'' + 
			",jnews_post_split = '" + jnewsPostSplit + '\'' + 
			",enable_review = '" + enableReview + '\'' + 
			",price = '" + price + '\'' + 
			",jnews_price_lowest = '" + jnewsPriceLowest + '\'' + 
			",jnews_social_counter_total = '" + jnewsSocialCounterTotal + '\'' + 
			",name = '" + name + '\'' + 
			",jnews_primary_category = '" + jnewsPrimaryCategory + '\'' + 
			",jnews_review_fields = '" + jnewsReviewFields + '\'' + 
			"}";
		}
}