package it.polito.centraletelefonica.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.maps.model.LatLng;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class PathsController extends Controller {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnOverview;

	@FXML
	private Button btnOperations;

	@FXML
	private Button btnOperationsCenter;

	@FXML
	private Button btnWorkers;

	@FXML
	private Button btnPaths;

	@FXML
	private DatePicker dateFrom;

	@FXML
	private DatePicker dateTo;

	@FXML
	private WebView webView;

	@FXML
	void openRelativeAnalitycs(MouseEvent event) {

		if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

			Node node = (Node) event.getSource();
			String nodeID = node.getId();
			changeScene(nodeID);

		}

	}

	@FXML
	void initialize() {
		assert btnOverview != null : "fx:id=\"btnOverview\" was not injected: check your FXML file 'PathsView.fxml'.";
		assert btnOperations != null : "fx:id=\"btnOperations\" was not injected: check your FXML file 'PathsView.fxml'.";
		assert btnOperationsCenter != null : "fx:id=\"btnOperationsCenter\" was not injected: check your FXML file 'PathsView.fxml'.";
		assert btnWorkers != null : "fx:id=\"btnWorkers\" was not injected: check your FXML file 'PathsView.fxml'.";
		assert btnPaths != null : "fx:id=\"btnPaths\" was not injected: check your FXML file 'PathsView.fxml'.";
		assert dateFrom != null : "fx:id=\"dateFrom\" was not injected: check your FXML file 'PathsView.fxml'.";
		assert dateTo != null : "fx:id=\"dateTo\" was not injected: check your FXML file 'PathsView.fxml'.";
		assert webView != null : "fx:id=\"webView\" was not injected: check your FXML file 'PathsView.fxml'.";
		System.setProperty("java.net.useSystemProxies", "true");
		WebEngine engine = webView.getEngine();
		engine.load(getClass().getResource("map.html").toString());
//		engine.executeScript(MapJS.addOperationMarker("titolo", new LatLng(45.0702376, 7.6308604686812025)));
		
	}
}
