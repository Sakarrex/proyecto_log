package org.example.proyecto_log.commons;

import org.example.proyecto_log.model.dto.RequirementDto;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.Permutation;
import org.moeaframework.problem.AbstractProblem;

import java.util.*;

public class MoeaProblem extends AbstractProblem {

    HashMap<Integer, HashMap<Integer,Long>> framesDuration;
    HashMap<String,List<RequirementDto>> requirementDtos;
    HashMap<Integer, HashMap<Integer,Double>> framesPrice;
    HashMap<Integer, HashMap<Integer,Integer>> framesCapacities;


    public MoeaProblem(int numberOfVariables, int numberOfObjectives, HashMap<Integer, HashMap<Integer,Long>> frames,HashMap<String, List<RequirementDto>> requirementDtos,HashMap<Integer, HashMap<Integer,Double>> framesPrice, HashMap<Integer, HashMap<Integer,Integer>> framesCapacities) {
        super(numberOfVariables, numberOfObjectives);
        this.framesDuration = frames;
        this.requirementDtos = requirementDtos;
        this.framesCapacities = framesCapacities;
        this.framesPrice = framesPrice;

    }

    @Override
    public void evaluate(Solution solution) {
        long dist = 0;
        long maxDist= Integer.MIN_VALUE;
        long precio = 0;
        int cantSinCumplir = 0;

        //No se si hay una forma mas linda de acceder a los keys
        List<String> keys = new ArrayList<>(requirementDtos.keySet());


        for (int j = 0; j < solution.getNumberOfVariables(); j++) {

            //saco la permutacion
            Permutation per =  (Permutation) solution.getVariable(j);

            //saco los requirement asosciados
            List<RequirementDto> lista = requirementDtos.get(keys.get(j));


            for (int i = 0; i < (per.size()); i++) {

                Integer origen = lista.get(per.get(i)).getIdStopDeparture();
                Integer dest = lista.get(per.get(i)).getIdStopArrival();
                Double peso = lista.get(per.get(i)).getLoading();

                if(framesDuration.get(origen).containsKey(dest) && framesPrice.get(origen).containsKey(dest) && framesCapacities.get(origen).containsKey(dest)) {
                    dist += framesDuration.get(origen).get(dest);
                    precio += framesPrice.get(origen).get(dest);
                    if (framesCapacities.get(origen).get(dest) - peso > 0){
                        int count = (int) (framesCapacities.get(origen).get(dest) - peso);
                        framesCapacities.get(origen).put(dest,count);
                    }
                    else {
                        cantSinCumplir++;
                    }
                }
                else{
                    dist = Integer.MIN_VALUE;
                    precio = Integer.MAX_VALUE;
                    cantSinCumplir = Integer.MAX_VALUE;
                    break;
                }



            }
            

            if(dist > maxDist) {
                maxDist = dist;
            }
        }

        solution.setObjective(0,maxDist);
        solution.setObjective(1,precio);
        solution.setObjective(2,cantSinCumplir);
    }

    @Override
    public Solution newSolution() {

        Solution solution = new Solution(numberOfVariables,numberOfObjectives);

        //es feo hacerlo con este i
        int i = 0;
        for (Map.Entry<String,List<RequirementDto>> entry : requirementDtos.entrySet()) {

            Permutation per = EncodingUtils.newPermutation(entry.getValue().size());
            per.randomize();
            solution.setVariable(i, per);
            i+=1;
        }

        return solution;
    }
}
