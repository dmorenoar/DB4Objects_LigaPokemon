/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Entrenador;
import Models.Pokemon;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Constraint;
import com.db4o.query.Query;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author dmorenoar
 */
public class GestionDao {
    
    /*Creamos el contenedor que manejará la persistencia*/
    public ObjectContainer db;
    
    public GestionDao(){
        //Creamos la conexión con el fichero
        db = Db4oEmbedded.openFile("ligapokemon"); 
    }
    
    /*Insertar un entrenador*/
    public void insertarEntrenador(Entrenador e){
        db.store(e);
    }
    
    public void insertarPokemon(Pokemon p){
        db.store(p);
    }
      
    
    public Entrenador selectEntrenadorByNombre(Entrenador e){
        Entrenador aux;
        Query q = db.query();
        q.constrain(Entrenador.class);
        q.descend("nombre").constrain(e.getNombre());
        ObjectSet resultado = q.execute();
        
        aux = (Entrenador) resultado.next();
        
        return aux;
    }
    
    
    /*Modificar un entrenador por un campo determinado
        Recibimos el entrenador y la edad que queremos modificar.
        Tenemos que traer el antiguo y luego modificarlo
    */
    public void modificarEdadEntrenador(Entrenador e, int edad){
        ObjectSet resultado = db.queryByExample(e);
        Entrenador aux = (Entrenador) resultado.next();
        aux.setEdad(edad);
        db.store(e);
    }
    
    /*Modificar datos de un entrenador al completo*/
    public void modificarDatosEntrenador(Entrenador antiguo, Entrenador modificado){
        ObjectSet resultado = db.queryByExample(antiguo);
        Entrenador aux = (Entrenador) resultado.next();
        aux.setNombre(modificado.getNombre());
        aux.setSexo(modificado.getSexo());
        aux.setEdad(modificado.getEdad());
        aux.setEspecialidad(modificado.getEspecialidad());
        aux.setExperiencia(modificado.getExperiencia());
        aux.setTelefono(modificado.getTelefono());
        db.store(aux);
    }
    
    /*Ordenar los entrenadores por nombre*/
    public List<Entrenador> ordernarEntrenadoresByNombre(){
        List<Entrenador> entrenadores = selectAllEntrenadores();
        Collections.sort(entrenadores, Collections.reverseOrder());
        return entrenadores;
    }
    
    /*Seleccionamos todos los entrenadores*/
    public List<Entrenador> selectAllEntrenadores(){
        List<Entrenador> entrenadores = new ArrayList();
        Query q = db.query(); //Creamos la sentencia query
        q.constrain(Entrenador.class); //Asignamos la condición
        ObjectSet resultado = q.execute(); //Ejecutamos la sentencia y recuperamos el resultado
        
        while(resultado.hasNext()){
            Entrenador e = (Entrenador) resultado.next();
            entrenadores.add(e);
        }
        
        return entrenadores;
    }
    
    public List<Pokemon> selectAllPokemons(){
        List<Pokemon> pokemons = new ArrayList();
        
        Query q = db.query();
        q.constrain(Pokemon.class);
        ObjectSet resultado = q.execute();
        
        while(resultado.hasNext()){
            Pokemon p = (Pokemon) resultado.next();
            pokemons.add(p);
        }
        
        return pokemons;
    }
    
    
    
    public List<Entrenador> selectEntrenadoresByNombres(){
        List<Entrenador> entrenadores = selectAllEntrenadores();
        Collections.sort(entrenadores, Collections.reverseOrder());
        return entrenadores;
    }
    
    /*Obtenemos los entrenadores por un rango de edad*/
    public List<Entrenador> selectEntrenadorByRangoEdad(int edadMinima, int edadMaxima){
        List<Entrenador> entrenadores = new ArrayList();
        
        Query q = db.query();
        q.constrain(Entrenador.class);
        Constraint edadMin = q.descend("edad").constrain(edadMinima).greater();
        q.descend("edad").constrain(edadMaxima).smaller().and(edadMin);
        
        ObjectSet resultado = q.execute();
        
        while(resultado.hasNext()){
            Entrenador e = (Entrenador) resultado.next();
            entrenadores.add(e);
        }
        
        return entrenadores;
    }
    
    
    /*Para borrar un entrenador en primer lugar hemos de recuperar
    el objeto y luego proceder a borrarlo*/
    public void borrarEntrenador(Entrenador e){
        /*Hemos de recuperar el entrenador para poder borrarlo*/
        Query q = db.query();
        q.constrain(Entrenador.class);
        q.descend("nombre").constrain(e.getNombre());
        ObjectSet resultado = q.execute();
        Entrenador aux = (Entrenador) resultado.next();
        
        //Recuperado el entrenador lo eliminamos.
        db.delete(aux);
        
    }
    
    
    
    public void desconectar(){
        db.close();
        File f = new File("ligapokemon");
        f.delete();
    }
    
}
