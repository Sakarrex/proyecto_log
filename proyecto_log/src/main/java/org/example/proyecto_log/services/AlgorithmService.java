package org.example.proyecto_log.services;

import jdk.jfr.Percentage;
import org.example.proyecto_log.commons.MoeaProblem;
import org.example.proyecto_log.model.dto.RequirementDto;
import org.example.proyecto_log.model.mapper.RequirementMapper;
import org.example.proyecto_log.persistence.entity.FrameEntity;
import org.example.proyecto_log.persistence.entity.RequirementEntity;
import org.example.proyecto_log.persistence.entity.StopEntity;
import org.example.proyecto_log.persistence.entity.TrunckEntity;
import org.example.proyecto_log.persistence.repositories.FrameRepository;
import org.example.proyecto_log.persistence.repositories.RequirementRepository;
import org.example.proyecto_log.persistence.repositories.StopRepository;
import org.example.proyecto_log.persistence.repositories.TruncksRepository;
import org.jfree.chart.ChartHints.Key;
import org.json.JSONObject;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.variable.Permutation;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class AlgorithmService {

    FrameRepository frameRepository;
    RequirementRepository requirementRepository;
    TruncksRepository truncksRepository;
    RequirementMapper requirementMapper;
    StopRepository stopRepository;
    HashMap<Integer, HashMap<Integer,Long>> framesDuration;
    HashMap<Integer, HashMap<Integer,Double>> framesPrice;
    HashMap<Integer, HashMap<Integer,Integer>> framesCapacities;
    HashMap<String,List<RequirementDto>> requirements;
    HashMap<Integer,String> stopNames;




    public AlgorithmService (FrameRepository frameRepository, RequirementRepository requrimenteRepository, RequirementMapper requirementMapper, TruncksRepository truncksRepository, StopRepository stopRepository) {
        this.frameRepository = frameRepository;
        this.requirementRepository = requrimenteRepository;
        this.requirementMapper = requirementMapper;
        this.truncksRepository = truncksRepository;
        this.stopRepository = stopRepository;
        this.framesDuration = new HashMap<>();
        this.framesPrice = new HashMap<>();
        this.framesCapacities = new HashMap<>();
        this.stopNames = new HashMap<>();
        this.requirements = new HashMap<>();
    }

    public Integer buscarCamino(){
        List<Integer> ciudades = new ArrayList<>(framesDuration.keySet());

        for (int i=0; i<framesDuration.size();i++) {
            //obtengo una ciudad de origen y todas las ciudades a las que llega
            int keyOrigen = ciudades.get(i);
            HashMap<Integer,Long> origen = framesDuration.get(keyOrigen);
            for (int j=0; j<ciudades.size();j++) {
                // si desde la ciudad i no puedo llegar a la j, me fijo por una cidad k que llegue a j, y haga un camino de i a j con duracion y precio i+k+j
                int keyDestino = ciudades.get(j);
                if(i==j || origen.containsKey(keyDestino)){
                    continue;
                }
                for(int k=0; k<framesDuration.size();k++){
                    int keyIntermedio = ciudades.get(k);
                    HashMap<Integer,Long> intermedio = framesDuration.get(keyIntermedio);
                    if(intermedio.containsKey(keyDestino) && origen.containsKey(keyIntermedio)){
                        long duracion = origen.get(keyIntermedio)+intermedio.get(keyDestino);
                        double precio = framesPrice.get(keyOrigen).get(keyIntermedio)+framesPrice.get(keyIntermedio).get(keyDestino);
                        framesDuration.get(keyOrigen).put(keyDestino,duracion);
                        framesPrice.get(keyOrigen).put(keyDestino, precio);
                    }
                   
                }
            
            }
            
        }


        return null;
    }

    public String init(){
        //reseteo variables para poder hacer varias veces la consulta sin tener que reiniciar todo el programa
        this.framesDuration = new HashMap<>();
        this.framesPrice = new HashMap<>();
        this.framesCapacities = new HashMap<>();
        this.stopNames = new HashMap<>();
        this.requirements = new HashMap<>();
        // traigo los frames
        Iterable<FrameEntity> frames = frameRepository.findAll();

        for (FrameEntity frame : frames) {

            //calculo duracion entre stops
            Integer origen = frame.getIdStopDeparture();
            Integer destino = frame.getIdStopArrival();
            Duration dur = Duration.between(frame.getDepartureDatetime(), frame.getArrivalDatetime());

            //si la duracion de inicio es mayor a destino calculo el tiempo entre 2 dias
            if ( dur.isNegative()){
                Duration dur1 = Duration.between(frame.getDepartureDatetime(), LocalTime.MAX);
                Duration dur2 = Duration.between(LocalTime.MIDNIGHT, frame.getArrivalDatetime());
                dur = dur1.plus(dur2);
            }

            //Precio y duración de cada frame
            if (framesDuration.containsKey(origen)) {

                framesDuration.get(origen).put(destino, dur.toMinutes());
                framesPrice.get(origen).put(destino, frame.getPrice());
            }
            else{

                HashMap<Integer, Long> framesDestino = new HashMap<>();
                framesDestino.put(destino, dur.toMinutes());
                framesDuration.put(origen, framesDestino);

                HashMap<Integer, Double> frameprecio = new HashMap<>();
                frameprecio.put(destino, frame.getPrice());
                framesPrice.put(origen, frameprecio);
            }

        }

        buscarCamino();

        int cont = 0;
        List<Integer> keys = new ArrayList<>(framesDuration.keySet());
        for (int i = 0; i<framesDuration.size();i++){
            for (int j = 0; j<framesDuration.size();j++)
            if(!framesDuration.get(keys.get(i)).containsKey(keys.get(j))){
                cont++;
            }
        }
        
        List<StopEntity> stopEntities = stopRepository.findAll();

        for (StopEntity stopEntity : stopEntities) {
            stopNames.put(stopEntity.getId(), stopEntity.getName());
        }


        //busco los requirements
        List<RequirementEntity> requirementEntities = requirementRepository.findAll();


        for (int i=0; i<requirementEntities.size();i++) {
            //Si el requirement tiene un stop que no existe lo salto
            if(stopNames.get(requirementEntities.get(i).getIdStopDeparture())==null || stopNames.get(requirementEntities.get(i).getIdStopArrival())==null){
                continue;
            }
            
            String categoria = requirementEntities.get(i).getCategory();
            //los guardo dentro de su categoria
            if (requirements.containsKey(categoria)) {
                requirements.get(categoria).add(requirementMapper.toDto(requirementEntities.get(i)));
            }
            else{
                ArrayList<RequirementDto> lista = new ArrayList<RequirementDto>();
                lista.add(requirementMapper.toDto(requirementEntities.get(i)));
                requirements.put(categoria,lista);
            }


        }

        List<TrunckEntity> trunckEntities = truncksRepository.findAll();

        for (int i = 0; i<trunckEntities.size();i++){
            int origen = trunckEntities.get(i).getIdStopDeparture();
            int destino = trunckEntities.get(i).getIdStopArrival();
            int capacidad =trunckEntities.get(i).getCapacity();

            if (framesCapacities.containsKey(origen)) {
                framesCapacities.get(origen).put(destino, capacidad);
            }else
            {
                HashMap<Integer, Integer> framesize = new HashMap<>();
                framesize.put(destino,capacidad);
                framesCapacities.put(origen,framesize);
            }

        }

        



        return "Inicializado";

    }

    public HashMap run(){
        init();
        NSGAII algortihm = new NSGAII(new MoeaProblem(requirements.size(), 3,framesDuration,requirements,framesPrice,framesCapacities));

        algortihm.run(1000);

        HashMap<String,String[]> resultado = new HashMap<>();
        
        List<String> categorias = new ArrayList<>(requirements.keySet());
        for (int i=0; i<algortihm.getResult().get(0).getNumberOfVariables();i++){
            Permutation var = (Permutation) algortihm.getResult().get(0).getVariable(i);
            List<RequirementDto> listaRequirements = requirements.get(categorias.get(i));
            int[] ids = var.toArray();


            String[] names = new String[ids.length];

            for (int j = 0; j < ids.length; j++) {

                names[j] = stopNames.get(listaRequirements.get(ids[j]).getIdStopDeparture());
                
            }
            resultado.put(categorias.get(i),names);

        }

        algortihm.getResult().display();
       



        return resultado;
    };
}
