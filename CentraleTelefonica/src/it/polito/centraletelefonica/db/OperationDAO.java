package it.polito.centraletelefonica.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.google.maps.model.LatLng;

import it.polito.centraletelefonica.model.ChiusureRow;
import it.polito.centraletelefonica.model.NuoveRow;
import it.polito.centraletelefonica.model.Operation;
import it.polito.centraletelefonica.model.OperationCenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;

public class OperationDAO extends DAO {

	public void getAreaPercentage(LocalDate from, LocalDate to, ObservableList<Data> dati) {

		try {
			PreparedStatement preparedStatement = persistentConnection.prepareStatement(Queries.N_OP_CENTRALE_PERIODO);
			preparedStatement.setDate(1, Date.valueOf(from));
			preparedStatement.setDate(2, Date.valueOf(to));
			ResultSet resultSet = preparedStatement.executeQuery();
			int tot = 0;

			while (resultSet.next()) {
				tot += resultSet.getInt("n_operazioni");
			}

			resultSet.beforeFirst();
			int count = 0;

			while (resultSet.next()) {

				double percentage = (double) (resultSet.getInt("n_operazioni")) / tot;
				int parte = (int) (percentage * 100);
				dati.get(count).setPieValue(parte);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void setTypePercentage(LocalDate from, LocalDate to, ObservableList<Data> dati) {

		try {
			PreparedStatement preparedStatement = persistentConnection.prepareStatement(Queries.N_OP_TIPO_PERIODO);
			preparedStatement.setDate(1, Date.valueOf(from));
			preparedStatement.setDate(2, Date.valueOf(to));
			ResultSet resultSet = preparedStatement.executeQuery();
			int tot = 0;

			while (resultSet.next()) {
				tot += resultSet.getInt("n_operazioni");
			}

			resultSet.beforeFirst();
			int count = 0;

			while (resultSet.next()) {

				double percentage = (double) (resultSet.getInt("n_operazioni")) / tot;
				int parte = (int) (percentage * 100);
				dati.get(count).setPieValue(parte);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ObservableList<NuoveRow> getNuoveMese() {
		ObservableList<NuoveRow> result = FXCollections.observableArrayList();
		try {
			PreparedStatement preparedStatement = persistentConnection.prepareStatement(Queries.NUOVE_PER_MESE);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				NuoveRow nuoveRow = new NuoveRow();
				nuoveRow.setPeriodo(resultSet.getString("Mese"));
				nuoveRow.setNuoveSegnalazioni(resultSet.getInt("n_operazioni"));
				result.add(nuoveRow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ObservableList<NuoveRow> getNuoveTrimestre() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObservableList<NuoveRow> getNuoveQuadrimestre() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObservableList<NuoveRow> getNuoveSemestre() {
		// TODO Auto-generated method stub
		return null;
	}

	public double mediaSuMese() {
		double result = 0.0;
		try {
			PreparedStatement preparedStatement = persistentConnection.prepareStatement(Queries.MEDIA_NUOVE_MENSILE);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			result = resultSet.getDouble("avg_mensile");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public ObservableList<ChiusureRow> getChiuseMese() {
		ObservableList<ChiusureRow> result = FXCollections.observableArrayList();
		try {
			PreparedStatement preparedStatement = persistentConnection.prepareStatement(Queries.CHIUSURE_PER_MESE);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ChiusureRow cRow = new ChiusureRow();
				cRow.setPeriodo(resultSet.getString("Mese"));
				cRow.setOpConcluse(resultSet.getInt("n_chiusure"));
				result.add(cRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public double getMediaChiuseMese() {

		double result = 0.0;
		try {
			PreparedStatement preparedStatement = persistentConnection.prepareStatement(Queries.MEDIA_CHIUSE_MESE);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			result = resultSet.getDouble("avg_mensile");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public List<Operation> getAll() {

		List<Operation> operazioni = new LinkedList<>();

		try {
			PreparedStatement preparedStatement = persistentConnection.prepareStatement(Queries.GET_ALL_OPERATIONS);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String id = resultSet.getString("SegnalazioneID");
				String tipo = resultSet.getString("SegnalazioneTipo");
				double media = resultSet.getDouble("durata_media");
				double varianza = resultSet.getDouble("varianza");
				int operatoriRichiesti = resultSet.getInt("operatori_richiesti");
				LatLng coordinate = new LatLng(resultSet.getDouble("op.Latitude"), resultSet.getDouble("op.Longitude"));
				String idCentrale = resultSet.getString("op.CentraleID");
				String name = resultSet.getString("nome");
				String street = resultSet.getString("indirizzo");
				LatLng latLng = new LatLng(resultSet.getDouble("c.Latitude"), resultSet.getDouble("c.Longitude"));
				int numOperatori = resultSet.getInt("NumeroOperatori");
				LocalDate reportingDate = resultSet.getDate("DataSegnalazione").toLocalDate();
				LocalDate goalDate = resultSet.getDate("DataObiettivo").toLocalDate();
				Date d = resultSet.getDate("DataChiusura");
				OperationCenter operationCenter = new OperationCenter(idCentrale, name, street, latLng, numOperatori);
				Operation operation = new Operation(id, coordinate, operationCenter);
				LocalDate closingDate = null;
				String stato = resultSet.getString("Stato");
				String city = resultSet.getString("Comune");
				if (d != null) {
					closingDate = d.toLocalDate();
					operation.setDataChiusura(closingDate);
				}

				operation.setDataSegnalazione(reportingDate);
				operation.setDataObiettivo(goalDate);
				operation.setStato(stato);
				operation.setTipo(tipo);
				operation.setMedia(media);
				operation.setComune(city);
				operation.setVarianza(varianza);
				operation.setOperatoriRichiesti(operatoriRichiesti);
				operazioni.add(operation);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return operazioni;
	}

}
