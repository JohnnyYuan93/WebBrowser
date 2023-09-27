package application;

import javafx.scene.control.Tab;
import javafx.scene.web.WebView;

public class MyTab extends Tab {
	private WebView webview;
	
	public WebView getWebview() {
		return webview;
	}
	public void setWebview(WebView webview) {
		this.webview = webview;
	}
	public MyTab(WebView webview) {
		super();
		this.webview = webview;
	}
	
	public MyTab() {
		this.webview=new WebView();
		this.setContent(webview);
	}
	

}
