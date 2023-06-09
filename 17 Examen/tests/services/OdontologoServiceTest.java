package services;

import dao.implementations.OdontologoDaoArray;
import dao.implementations.OdontologoDaoH2;
import database.db;
import models.Odontologo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {

    // El @beforEach y el @AfterEach lo hacemos para la base de datos H2
    @BeforeEach
    void setUp() {
        db.tablaH2("CREATE TABLE IF NOT EXISTS ODONTOLOGOS (ID INT AUTO_INCREMENT PRIMARY KEY, MATRICULA INT, NOMBRE VARCHAR(25), APELLIDO VARCHAR(25));");
    }
    @AfterEach
    void tearDown() {
        db.tablaH2("DROP TABLE ODONTOLOGOS");
    }

    // TESTS

    @Test
    void registro_3_odontologos_enH2() throws SQLException {
        // Arrange
        Odontologo odo1 = new Odontologo(123, "Marcos", "Amado");
        Odontologo odo2 = new Odontologo(456, "Elias", "Gazza");
        Odontologo odo3 = new Odontologo(789, "Pablo", "Vidal");
        OdontologoService serviceH2 = new OdontologoService(new OdontologoDaoH2());
        // Act (registramos los odontologos)
        serviceH2.registrar(odo1);
        serviceH2.registrar(odo2);
        serviceH2.registrar(odo3);
        // Assert (traemos los nombres de todos los registrados)
        Connection conexion = db.conectarH2();
        Statement statement = conexion.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ODONTOLOGOS");
        List<String> res = new ArrayList<>();
        while (resultSet.next()) {
            res.add(resultSet.getString("nombre"));
        }
        Assertions.assertEquals(3, res.size());
    }

    @Test
    void listar_todos_enH2() {
        // Arrange (creamos odontologos y los registramos)
        Odontologo odo1 = new Odontologo(123, "MarCOs", "AmADo");
        Odontologo odo2 = new Odontologo(456, "elias", "gazza");
        Odontologo odo3 = new Odontologo(1000001, "Cosme", "Fulanito");
        Odontologo odo4 = new Odontologo(789, "Pablo", "VIDAL");
        OdontologoService serviceH2 = new OdontologoService(new OdontologoDaoH2());
        serviceH2.registrar(odo1);
        serviceH2.registrar(odo2);
        serviceH2.registrar(odo3);
        serviceH2.registrar(odo4);
        // Act (traemos todos los odontologos en una lista)
        List<Odontologo> registro = serviceH2.listarTodos();
        // Assert (ceheckeamos el tamaño de la lista)
        Assertions.assertEquals(3, registro.size());
    }

    @Test
    void registro_3_odontologos_en_ArrayDB() {
        // Arrange
        Odontologo odo1 = new Odontologo(123, "Cacho", "GarAy");
        Odontologo odo2 = new Odontologo(456, "LarrY", "Declay");
        Odontologo odo3 = new Odontologo(789, "pablo", "VIDAL");
        OdontologoDaoArray daoArray = new OdontologoDaoArray();
        OdontologoService serviceArray = new OdontologoService(daoArray);
        // Act (registramos los odontologos)
        serviceArray.registrar(odo1);
        serviceArray.registrar(odo2);
        serviceArray.registrar(odo3);
        // Assert (traemos los nombres de todos los registrados)
        Assertions.assertEquals(3, daoArray.getBaseDeDatosArray().size());
    }

    @Test
    void listar_todos_en_ArrayDB() {
        // Arrange
        Odontologo odo1 = new Odontologo(123, "Cacho", "GarAy");
        Odontologo odo2 = new Odontologo(456, "LarrY", "Declay");
        Odontologo odo3 = new Odontologo(789, "pablo", "VIDAL");
        OdontologoService serviceArray = new OdontologoService(new OdontologoDaoArray());
        serviceArray.registrar(odo1);
        serviceArray.registrar(odo2);
        serviceArray.registrar(odo3);
        // Act
        List<Odontologo> listaObtenida = serviceArray.listarTodos();
        // Assert (Probamos que traiga toda la lista y un nombre al azar)
        Assertions.assertEquals(3, listaObtenida.size());
        Assertions.assertEquals("Larry", listaObtenida.get(1).getNombre());

    }
}