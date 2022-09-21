package recurrent.domain.mapper;

import automation.factory.Utils;
import recurrent.domain.business.CancelacionesTeleventa;

public class CanalTeleventaMapper {

    public CancelacionesTeleventa getCanalTeleventaFromString(String line){
        if(Utils.evaluateString(line)){
            return null;
        }
        CancelacionesTeleventa result = new CancelacionesTeleventa();
        String[] values = line.split(";");
        if(values.length == 11){
            if(Utils.evaluateString(values[0])){

            }
            if(Utils.evaluateString(values[0])){

            }
            if(Utils.evaluateString(values[0])){

            }
            if(Utils.evaluateString(values[0])){

            }
            if(Utils.evaluateString(values[0])){

            }
            if(Utils.evaluateString(values[0])){

            }
            if(Utils.evaluateString(values[0])){

            }
            if(Utils.evaluateString(values[0])){

            }
            if(Utils.evaluateString(values[0])){

            }
            if(Utils.evaluateString(values[0])){

            }
            if(Utils.evaluateString(values[0])){

            }
        }
        return result;
    }

}
