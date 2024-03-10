package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {

	private static VehicleDao instance = null;

	private VehicleDao() {}

	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";

	public long create(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(CREATE_VEHICLE_QUERY,
					 PreparedStatement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, vehicle.getConstructeur());
			statement.setInt(2, vehicle.getNbPlaces());

			statement.executeUpdate();

			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getLong(1);
			} else {
				throw new DaoException("La création du véhicule a échoué, aucun ID généré.");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du véhicule : " + e.getMessage());
		}
	}

	public void delete(long vehicleId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(DELETE_VEHICLE_QUERY)) {
			statement.setLong(1, vehicleId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du véhicule : " + e.getMessage());
		}
	}

	public Optional<Vehicle> findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_VEHICLE_QUERY)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(mapResultSetToVehicle(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du véhicule par ID : " + e.getMessage());
		}
		return Optional.empty();
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(FIND_VEHICLES_QUERY)) {
			while (resultSet.next()) {
				vehicles.add(mapResultSetToVehicle(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération de tous les véhicules : " + e.getMessage());
		}
		return vehicles;
	}

	private Vehicle mapResultSetToVehicle(ResultSet resultSet) throws SQLException {
		Vehicle vehicle = new Vehicle();
		vehicle.setId(resultSet.getLong("id"));
		vehicle.setConstructeur(resultSet.getString("constructeur"));
		vehicle.setNbPlaces(resultSet.getInt("nb_places"));
		return vehicle;
	}
}