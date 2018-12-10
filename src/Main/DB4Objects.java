/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DAO.GestionDao;
import Models.Entrenador;
import Models.Pokemon;
import java.util.List;

/**
 *
 * @author dmorenoar
 */
public class DB4Objects {

    public static void main(String[] args) {

        GestionDao ligaDAO = new GestionDao();

        // TODO code application logic here
        Entrenador ash = new Entrenador("Ash", "+3412345", "Hombre", 2, 2, "Eléctrico");
        Entrenador brock = new Entrenador("Brock", "+340101", "Hombre", 5, 6, "Roca");

        Pokemon pikachu = new Pokemon("Pikachu", "Eléctrico", 22, ash);
        Pokemon onix = new Pokemon("Onix", "Roca", 22, brock);

        System.out.println("|||||||||Testeando insertar un entrenador|||||||||");
        //Testeando insertar un entrenador
        insertarEntrenador(ligaDAO, ash);
        insertarEntrenador(ligaDAO, brock);

        System.out.println("|||||||||Testeando seleccionar todos los entrenadores|||||||||");
        //Testeando seleccionar todos los entrenadores
        for (Entrenador e : selectAllEntrenadores(ligaDAO)) {
            System.out.println(e);
        }
        
        System.out.println("|||||||||Testeando ordenar los entreandor por nombre|||||||||");
        //Testeando ordenar los entrenadores por nombre
        for (Entrenador e : orderEntrenadorByNombre(ligaDAO)) {
            System.out.println(e);
        }

        System.out.println("|||||||||Testeando modificar datos de un entrenador|||||||||");
        //Testeando modificar los datos de un entrenador
        Entrenador ashNew = new Entrenador("Ash22", "+222", "Hombre", 2, 2, "Piedra");
        modificarDatosEntrenador(ligaDAO, ash, ashNew);

        System.out.println("|||||||||Testeando seleccionar un entrenador por nombre|||||||||");
        //Testeando seleccionar entrenador por nombre
        selectEntrenadorByNombre(ligaDAO, ash);

        System.out.println("|||||||||Testeando seleccionar entrenadores por rango de edad|||||||||");
        //Testeando seleccionar entrenadores por rando de edad
        for (Entrenador e : selectByRangeEdad(ligaDAO, 0, 10)) {
            System.out.println(e);
        }

        System.out.println("|||||||||Testeando borrar un entrenador|||||||||");
        //Testeando borrar un entrenador
        borrarEntrenador(ligaDAO, ash);

        System.out.println("|||||||||Testeando insertar un pokemon|||||||||");
        //Testeando insertar un pokemon
        insertarPokemon(ligaDAO, onix);

        System.out.println("|||||||||Testeando seleccionar todos los pokemons |||||||||");
        //Testeando seleccionar todos los pokemons
        for (Pokemon p : selectAllPokemons(ligaDAO)) {
            System.out.println(p);
        }

        System.out.println("|||||||||Testeando cerrar la conexion y borrando la base de datos|||||||||");
        //Testeando cerrar la conexion y borrando la base de datos
        cerrarDBOO(ligaDAO);
    }

    public static void insertarEntrenador(GestionDao ligaDAO, Entrenador e) {
        ligaDAO.insertarEntrenador(e);
        System.out.println("Entrenador dado de alta correctamente");
    }

    public static void insertarPokemon(GestionDao ligaDAO, Pokemon p) {
        ligaDAO.insertarPokemon(p);
        System.out.println("Pokemon dado de alta correctamente");
    }

    public static List<Entrenador> selectAllEntrenadores(GestionDao ligaDAO) {
        return ligaDAO.selectAllEntrenadores();
    }

    public static void borrarEntrenador(GestionDao ligaDAO, Entrenador e) {
        ligaDAO.borrarEntrenador(e);
        System.out.println("Entrenador borrado correctamente");
    }

    public static List<Entrenador> selectByRangeEdad(GestionDao ligaDAO, int edadMin, int edadMax) {
        return ligaDAO.selectEntrenadorByRangoEdad(edadMin, edadMax);
    }

    public static List<Pokemon> selectAllPokemons(GestionDao ligaDAO) {
        return ligaDAO.selectAllPokemons();
    }

    public static List<Entrenador> orderEntrenadorByNombre(GestionDao ligaDAO) {
        return ligaDAO.selectEntrenadoresByNombres();
    }

    public static void modificarDatosEntrenador(GestionDao ligaDAO, Entrenador antiguo, Entrenador modificado) {
        ligaDAO.modificarDatosEntrenador(antiguo, modificado);
        System.out.println("Datos del entrenador modificados correctamente");
    }

    public static void modificarEdadEntrenador(GestionDao ligaDAO, Entrenador e, int edad) {
        ligaDAO.modificarEdadEntrenador(e, edad);
    }

    public static void selectEntrenadorByNombre(GestionDao ligaDAO, Entrenador e) {
        System.out.println(ligaDAO.selectEntrenadorByNombre(e));
    }

    public static void cerrarDBOO(GestionDao ligaDAO) {
        ligaDAO.desconectar();
        System.out.println("Desconectado...");
        System.out.println("Borrando la base de datos");
    }
}
