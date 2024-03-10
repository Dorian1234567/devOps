package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";

	public long create(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(CREATE_RESERVATION_QUERY,
					 PreparedStatement.RETURN_GENERATED_KEYS)) {
			statement.setLong(1, reservation.getClientId());
			statement.setLong(2, reservation.getVehicleId());
			statement.setDate(3, java.sql.Date.valueOf(reservation.getDebut()));
			statement.setDate(4, java.sql.Date.valueOf(reservation.getFin()));

			statement.executeUpdate();

			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getLong(1);
			} else {
				throw new DaoException("La création de la réservation a échoué, aucun ID généré.");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création de la réservation : " + e.getMessage());
		}
	}

	public void delete(long reservationId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {
			statement.setLong(1, reservationId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression de la réservation : " + e.getMessage());
		}
	}

	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {
			statement.setLong(1, clientId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					reservations.add(mapResultSetToReservation(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations par client : " + e.getMessage());
		}
		return reservations;
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)) {
			statement.setLong(1, vehicleId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					reservations.add(mapResultSetToReservation(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations par véhicule : " + e.getMessage());
		}
		return reservations;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(FIND_RESERVATIONS_QUERY)) {
			while (resultSet.next()) {
				reservations.add(mapResultSetToReservation(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération de toutes les réservations : " + e.getMessage());
		}
		return reservations;
	}

	private Reservation mapResultSetToReservation(ResultSet resultSet) throws SQLException {
		Reservation reservation = new Reservation();
		reservation.setId(resultSet.getLong("id"));
		reservation.setClientId(resultSet.getLong("client_id"));
		reservation.setVehicleId(resultSet.getLong("vehicle_id"));
		reservation.setDebut(resultSet.getDate("debut").toLocalDate());
		reservation.setFin(resultSet.getDate("fin").toLocalDate());
		return reservation;
	}
}
