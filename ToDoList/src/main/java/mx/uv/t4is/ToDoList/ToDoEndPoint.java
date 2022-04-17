package mx.uv.t4is.ToDoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import https.t4is_uv_mx.todo.AgregarRequest;
import https.t4is_uv_mx.todo.AgregarResponse;
import https.t4is_uv_mx.todo.EliminarRequest;
import https.t4is_uv_mx.todo.EliminarResponse;
import https.t4is_uv_mx.todo.EditarRequest;
import https.t4is_uv_mx.todo.EditarResponse;
import https.t4is_uv_mx.todo.ListarResponse;

@Endpoint
public class ToDoEndPoint {

    @Autowired
     lactividades iactividades;
    
     @PayloadRoot(localPart = "AgregarRequest", namespace = "https://t4is.uv.mx/todo")
     @ResponsePayload
     public AgregarResponse agregarActividad(@RequestPayload AgregarRequest agregar){
         AgregarResponse respuesta = new AgregarResponse();
         respuesta.setRespuesta("ACTIVIDAD ANOTADA EXITOSAMENTE");
         Actividad actividad = new Actividad();
         actividad.setNombre(agregar.getNombre());
         actividad.setDescripcion(agregar.getDescripcion());
         actividad.setEstado(agregar.getEstado());
         iactividades.save(actividad);
         return respuesta;
     }
    
      @PayloadRoot(localPart = "EliminarRequest", namespace = "https://t4is.uv.mx/todo")
      @ResponsePayload
      public EliminarResponse eliminarActividad(@RequestPayload EliminarRequest eliminar){
          EliminarResponse respuesta = new EliminarResponse();
          iactividades.deleteById(eliminar.getId());
          respuesta.setRespuesta("ACTIVIDAD ELIMINADA EXITOSAMENTE");
          return respuesta;
      }

     @PayloadRoot(localPart = "ListarRequest" ,namespace = "https://t4is.uv.mx/todo")
     @ResponsePayload
     public ListarResponse ListarTareas(){
        ListarResponse respuesta = new ListarResponse();
         Iterable<Actividad> lista = iactividades.findAll();
         
         for (Actividad actividad : lista) {
            ListarResponse.Actividad a = new ListarResponse.Actividad();
             a.setNombre(actividad.getNombre());
             a.setId(actividad.getId());
             a.setDescripcion(actividad.getDescripcion());
             a.setEstado(actividad.getEstado());
             respuesta.getActividad().add(a);
         }
         return respuesta;
     }
     
     /*Modificar Tarea*/
     @PayloadRoot(localPart = "EditarRequest" ,namespace = "https://t4is.uv.mx/todo")
     @ResponsePayload
     public EditarResponse editarActividad(@RequestPayload EditarRequest editar){
        EditarResponse respuesta = new EditarResponse(); 
         Actividad actividad = new Actividad();
         actividad.setId(editar.getId());
         actividad.setNombre(editar.getNombre());
         actividad.setDescripcion(editar.getDescripcion());
         actividad.setEstado(editar.getEstado());
         iactividades.save(actividad);
         respuesta.setRespuesta(true);
         return respuesta;
     }  
}
