package goid.kotajambi.puskesmas_ngadu.model.model_berita;


import com.google.gson.annotations.SerializedName;


public class Images{

	@SerializedName("thumbnail")
	private Thumbnail thumbnail;

	@SerializedName("jnews-350x250")
	private Jnews350x250 jnews350x250;

	@SerializedName("large")
	private Large large;

	@SerializedName("jnews-120x86")
	private Jnews120x86 jnews120x86;

	@SerializedName("jnews-featured-1140")
	private JnewsFeatured1140 jnewsFeatured1140;

	@SerializedName("medium")
	private Medium medium;

	@SerializedName("jnews-360x180")
	private Jnews360x180 jnews360x180;

	@SerializedName("jnews-750x536")
	private Jnews750x536 jnews750x536;

	@SerializedName("jnews-75x75")
	private Jnews75x75 jnews75x75;

	@SerializedName("jnews-1140x570")
	private Jnews1140x570 jnews1140x570;

	@SerializedName("jnews-featured-750")
	private JnewsFeatured750 jnewsFeatured750;

	@SerializedName("jnews-750x375")
	private Jnews750x375 jnews750x375;

	@SerializedName("jnews-360x504")
	private Jnews360x504 jnews360x504;

	@SerializedName("medium_large")
	private MediumLarge mediumLarge;

	@SerializedName("full")
	private Full full;

	@SerializedName("jnews-1140x815")
	private Jnews1140x815 jnews1140x815;

	public void setThumbnail(Thumbnail thumbnail){
		this.thumbnail = thumbnail;
	}

	public Thumbnail getThumbnail(){
		return thumbnail;
	}

	public void setJnews350x250(Jnews350x250 jnews350x250){
		this.jnews350x250 = jnews350x250;
	}

	public Jnews350x250 getJnews350x250(){
		return jnews350x250;
	}

	public void setLarge(Large large){
		this.large = large;
	}

	public Large getLarge(){
		return large;
	}

	public void setJnews120x86(Jnews120x86 jnews120x86){
		this.jnews120x86 = jnews120x86;
	}

	public Jnews120x86 getJnews120x86(){
		return jnews120x86;
	}

	public void setJnewsFeatured1140(JnewsFeatured1140 jnewsFeatured1140){
		this.jnewsFeatured1140 = jnewsFeatured1140;
	}

	public JnewsFeatured1140 getJnewsFeatured1140(){
		return jnewsFeatured1140;
	}

	public void setMedium(Medium medium){
		this.medium = medium;
	}

	public Medium getMedium(){
		return medium;
	}

	public void setJnews360x180(Jnews360x180 jnews360x180){
		this.jnews360x180 = jnews360x180;
	}

	public Jnews360x180 getJnews360x180(){
		return jnews360x180;
	}

	public void setJnews750x536(Jnews750x536 jnews750x536){
		this.jnews750x536 = jnews750x536;
	}

	public Jnews750x536 getJnews750x536(){
		return jnews750x536;
	}

	public void setJnews75x75(Jnews75x75 jnews75x75){
		this.jnews75x75 = jnews75x75;
	}

	public Jnews75x75 getJnews75x75(){
		return jnews75x75;
	}

	public void setJnews1140x570(Jnews1140x570 jnews1140x570){
		this.jnews1140x570 = jnews1140x570;
	}

	public Jnews1140x570 getJnews1140x570(){
		return jnews1140x570;
	}

	public void setJnewsFeatured750(JnewsFeatured750 jnewsFeatured750){
		this.jnewsFeatured750 = jnewsFeatured750;
	}

	public JnewsFeatured750 getJnewsFeatured750(){
		return jnewsFeatured750;
	}

	public void setJnews750x375(Jnews750x375 jnews750x375){
		this.jnews750x375 = jnews750x375;
	}

	public Jnews750x375 getJnews750x375(){
		return jnews750x375;
	}

	public void setJnews360x504(Jnews360x504 jnews360x504){
		this.jnews360x504 = jnews360x504;
	}

	public Jnews360x504 getJnews360x504(){
		return jnews360x504;
	}

	public void setMediumLarge(MediumLarge mediumLarge){
		this.mediumLarge = mediumLarge;
	}

	public MediumLarge getMediumLarge(){
		return mediumLarge;
	}

	public void setFull(Full full){
		this.full = full;
	}

	public Full getFull(){
		return full;
	}

	public void setJnews1140x815(Jnews1140x815 jnews1140x815){
		this.jnews1140x815 = jnews1140x815;
	}

	public Jnews1140x815 getJnews1140x815(){
		return jnews1140x815;
	}

	@Override
 	public String toString(){
		return 
			"Images{" + 
			"thumbnail = '" + thumbnail + '\'' + 
			",jnews-350x250 = '" + jnews350x250 + '\'' + 
			",large = '" + large + '\'' + 
			",jnews-120x86 = '" + jnews120x86 + '\'' + 
			",jnews-featured-1140 = '" + jnewsFeatured1140 + '\'' + 
			",medium = '" + medium + '\'' + 
			",jnews-360x180 = '" + jnews360x180 + '\'' + 
			",jnews-750x536 = '" + jnews750x536 + '\'' + 
			",jnews-75x75 = '" + jnews75x75 + '\'' + 
			",jnews-1140x570 = '" + jnews1140x570 + '\'' + 
			",jnews-featured-750 = '" + jnewsFeatured750 + '\'' + 
			",jnews-750x375 = '" + jnews750x375 + '\'' + 
			",jnews-360x504 = '" + jnews360x504 + '\'' + 
			",medium_large = '" + mediumLarge + '\'' + 
			",full = '" + full + '\'' + 
			",jnews-1140x815 = '" + jnews1140x815 + '\'' + 
			"}";
		}
}