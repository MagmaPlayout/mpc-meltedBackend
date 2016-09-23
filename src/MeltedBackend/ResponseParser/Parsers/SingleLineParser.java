package MeltedBackend.ResponseParser.Parsers;

import MeltedBackend.ResponseParser.Responses.GenericResponse;

/**
 * Parser for melted commands with one line response (aside from status line)
 * (USTA, UGET ... )
 * 
 * @author rombus
 */
public class SingleLineParser extends AbstractMeltedParser{

    public SingleLineParser(GenericResponse response) {
        super(response);
    }

    @Override
    public GenericResponse parse(String rawResponse) {
        String[] lines = rawResponse.split("\n");
        String status = lines[0];
        String[] singleData = null;

        try{
            singleData = lines[1].split(" ");
        } catch(ArrayIndexOutOfBoundsException e){
            // Seguro el comando falló, por lo que no hay linea de datos
        }

        response.setData(status, singleData, null);
        return response;
    }
}
